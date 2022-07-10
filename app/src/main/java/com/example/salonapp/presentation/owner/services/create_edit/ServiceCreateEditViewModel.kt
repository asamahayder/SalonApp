package com.example.salonapp.presentation.owner.services.create_edit

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.ValidationResult
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.services.create_service.CreateServiceUseCase
import com.example.salonapp.domain.use_cases.services.get_service.GetServiceByIdUseCase
import com.example.salonapp.domain.use_cases.services.update_service.UpdateServiceUseCase
import com.example.salonapp.domain.use_cases.validations.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ServiceCreateEditViewModel @Inject constructor(
    private val getSalonUseCase: GetSalonUseCase,
    private val getServiceByIdUseCase: GetServiceByIdUseCase,
    private val createServiceUseCase: CreateServiceUseCase,
    private val updateServiceUseCase: UpdateServiceUseCase,
    private val validateServiceNameUseCase: ValidateServiceNameUseCase,
    private val validateServiceDescriptionUseCase: ValidateServiceDescriptionUseCase,
    private val validateServiceDurationUseCase: ValidateServiceDurationUseCase,
    private val validateServicePriceUseCase: ValidateServicePriceUseCase,
    private val validateServicePauseUseCase: ValidateServicePauseUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(ServiceCreateEditState())
    val state: State<ServiceCreateEditState> = _state

    private val eventChannel = Channel<ServiceCreateEditEvent>()
    val events = eventChannel.receiveAsFlow()


    private fun initializeEditService(serviceId: Int){
        getServiceByIdUseCase(serviceId).onEach { result ->
                when(result){
                    is Resource.Success -> {

                        val service = result.data!!

                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = null,
                            name = service.name,
                            description = service.description ?: "",
                            price = service.price.toString(),
                            durationInMinutes = service.durationInMinutes.toString(),
                            pauseStartInMinutes = service.pauseStartInMinutes.toString(),
                            pauseEndInMinutes = service.pauseEndInMinutes.toString(),
                            serviceId = service.id,

                        )

                        val serviceEmployeeIds = service.employees.map { it.id }

                        _state.value.selectableEmployees.forEachIndexed{ index, employee ->
                            if (serviceEmployeeIds.contains(employee.id)){
                                onEvent(ServiceCreateEditEvent.AddEmployee(index))
                            }
                        }

                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    }
                }
        }.launchIn(viewModelScope)
    }

    private fun initialize(serviceId: Int?){
        val salonId = sessionManager.fetchSalonId()

        if (salonId != null){
            getSalonUseCase(salonId).onEach {result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            salon = result.data,
                            selectableEmployees = result.data?.employees ?: listOf()
                        )

                        if (serviceId != null){
                            initializeEditService(serviceId)
                        }else{
                            _state.value = _state.value.copy(
                                isLoading = false
                            )
                        }

                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)

                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected error")
                    }
                }

            }.launchIn(viewModelScope)


        }else{
            _state.value = _state.value.copy(error = "Error, Salon ID not found")
        }
    }

    fun onEvent(event: ServiceCreateEditEvent) {
        when(event) {
            is ServiceCreateEditEvent.NameChanged ->{
                _state.value = _state.value.copy(name = event.name)

            }
            is ServiceCreateEditEvent.DescriptionChanged ->{
                _state.value = _state.value.copy(description = event.description)

            }
            is ServiceCreateEditEvent.PriceChanged ->{
                _state.value = _state.value.copy(price = event.price)

            }
            is ServiceCreateEditEvent.DurationChanged ->{
                _state.value = _state.value.copy(durationInMinutes = event.duration)
            }
            is ServiceCreateEditEvent.PauseStartChanged ->{
                _state.value = _state.value.copy(pauseStartInMinutes = event.pauseStart)

            }
            is ServiceCreateEditEvent.PauseEndChanged ->{
                _state.value = _state.value.copy(pauseEndInMinutes = event.pauseEnd)
            }
            is ServiceCreateEditEvent.AddEmployee ->{
                //Index based on selectable list

                if (_state.value.salon == null) return
                val employee = _state.value.salon!!.employees[event.index]

                val selectedEmployees = _state.value.selectedEmployees.toMutableList()
                selectedEmployees.add(employee)

                val selectableEmployees = _state.value.salon!!.employees.filter { !selectedEmployees.contains(it) }

                _state.value = _state.value.copy(
                    selectedEmployees = selectedEmployees,
                    selectableEmployees = selectableEmployees
                )

            }
            is ServiceCreateEditEvent.RemoveEmployee ->{
                if (_state.value.salon == null) return

                //Index based on selected list
                val selectedEmployees = _state.value.selectedEmployees.toMutableList()
                selectedEmployees.removeAt(event.index)

                val selectableEmployees = _state.value.salon!!.employees.filter { !selectedEmployees.contains(it)}

                _state.value = _state.value.copy(
                    selectedEmployees = selectedEmployees,
                    selectableEmployees = selectableEmployees
                )

            }
            is ServiceCreateEditEvent.OnToggleEmployeeMenu -> {
                _state.value = _state.value.copy(employeeSelectionExpanded = !state.value.employeeSelectionExpanded)
            }
            is ServiceCreateEditEvent.OnEmployeeSelectDismiss -> {
                _state.value = _state.value.copy(employeeSelectionExpanded = false)
            }
            is ServiceCreateEditEvent.Submit ->{
                runValidation()
            }
            is ServiceCreateEditEvent.Initialize -> {
                initialize(event.serviceID)
            }
        }
    }

    fun runValidation(){

        val validationResultList = mutableListOf<ValidationResult>()

        val nameResult = validateServiceNameUseCase.execute(_state.value.name)
        validationResultList.add(nameResult)

        val priceResult = validateServicePriceUseCase.execute(_state.value.price)
        validationResultList.add(priceResult)

        val descriptionResult = validateServiceDescriptionUseCase.execute(_state.value.description)
        validationResultList.add(descriptionResult)


        val durationResult = validateServiceDurationUseCase.execute(
            _state.value.durationInMinutes
        )

        validationResultList.add(durationResult)

        var pauseResult:ValidationResult? = null

        if (durationResult.successful){
            pauseResult = validateServicePauseUseCase.execute(
                _state.value.pauseStartInMinutes,
                _state.value.pauseEndInMinutes,
                _state.value.durationInMinutes.toInt()
            )

            validationResultList.add(pauseResult)
        }

        val hasError = validationResultList.any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                nameError = nameResult.errorMessage,
                descriptionError = descriptionResult.errorMessage,
                priceError = priceResult.errorMessage,
                durationError = durationResult.errorMessage,
                pauseError = pauseResult?.errorMessage
            )
            return
        }

        if (_state.value.serviceId != null){
            editService()
        }else{
            createService()
        }

    }

    private fun editService(){
        val pauseStart = if (_state.value.pauseStartInMinutes.isBlank()) null else _state.value.pauseStartInMinutes.toInt()

        val pauseEnd = if (_state.value.pauseEndInMinutes.isBlank()) null else _state.value.pauseEndInMinutes.toInt()

        val updatedService = Service(
            id = _state.value.serviceId!!,
            salon = _state.value.salon!!,
            name = _state.value.name,
            description = _state.value.description,
            price = _state.value.price.toInt(),
            durationInMinutes = _state.value.durationInMinutes.toInt(),
            pauseStartInMinutes = pauseStart,
            pauseEndInMinutes = pauseEnd,
            employees = _state.value.selectedEmployees
        )

        updateServiceUseCase(updatedService).onEach { result ->
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        error = null,
                        isLoading = false
                    )
                    eventChannel.send(ServiceCreateEditEvent.ServiceCreatedOrEditedSuccessfully)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun createService(){

        val pauseStart = if (_state.value.pauseStartInMinutes.isBlank()) null else _state.value.pauseStartInMinutes.toInt()

        val pauseEnd = if (_state.value.pauseEndInMinutes.isBlank()) null else _state.value.pauseEndInMinutes.toInt()

        val newService = Service(
            id = 0,
            salon = _state.value.salon!!,
            name = _state.value.name,
            description = _state.value.description,
            price = _state.value.price.toInt(),
            durationInMinutes = _state.value.durationInMinutes.toInt(),
            pauseStartInMinutes = pauseStart,
            pauseEndInMinutes = pauseEnd,
            employees = _state.value.selectedEmployees
        )

        createServiceUseCase(newService).onEach { result ->
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        error = null,
                        isLoading = false
                    )
                    eventChannel.send(ServiceCreateEditEvent.ServiceCreatedOrEditedSuccessfully)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}