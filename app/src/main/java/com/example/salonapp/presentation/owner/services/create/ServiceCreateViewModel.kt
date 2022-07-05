package com.example.salonapp.presentation.owner.services.create

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.R
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.models.Service
import com.example.salonapp.domain.models.ValidationResult
import com.example.salonapp.domain.use_cases.get_salon.GetSalonUseCase
import com.example.salonapp.domain.use_cases.services.create_service.CreateServiceUseCase
import com.example.salonapp.domain.use_cases.validations.*
import com.example.salonapp.presentation.owner.salon_create.SalonCreateEvent
import com.example.salonapp.presentation.owner.services.ServicesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ServiceCreateViewModel @Inject constructor(
    private val getSalonUseCase: GetSalonUseCase,
    private val createServiceUseCase: CreateServiceUseCase,
    private val validateServiceNameUseCase: ValidateServiceNameUseCase,
    private val validateServiceDescriptionUseCase: ValidateServiceDescriptionUseCase,
    private val validateServiceDurationUseCase: ValidateServiceDurationUseCase,
    private val validateServicePriceUseCase: ValidateServicePriceUseCase,
    private val validateServicePauseUseCase: ValidateServicePauseUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(ServiceCreateState())
    val state: State<ServiceCreateState> = _state

    private val eventChannel = Channel<ServiceCreateEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        val salonId = sessionManager.fetchSalonId()

        if (salonId != null){
            getSalonUseCase(salonId).onEach {result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            salon = result.data,
                            selectableEmployees = result.data?.employees ?: listOf()
                        )
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


    fun onEvent(event: ServiceCreateEvent) {
        when(event) {
            is ServiceCreateEvent.NameChanged ->{
                _state.value = _state.value.copy(name = event.name)

            }
            is ServiceCreateEvent.DescriptionChanged ->{
                _state.value = _state.value.copy(description = event.description)

            }
            is ServiceCreateEvent.PriceChanged ->{
                _state.value = _state.value.copy(price = event.price)

            }
            is ServiceCreateEvent.DurationChanged ->{
                _state.value = _state.value.copy(durationInMinutes = event.duration)
            }
            is ServiceCreateEvent.PauseStartChanged ->{
                _state.value = _state.value.copy(pauseStartInMinutes = event.pauseStart)

            }
            is ServiceCreateEvent.PauseEndChanged ->{
                _state.value = _state.value.copy(pauseEndInMinutes = event.pauseEnd)
            }
            is ServiceCreateEvent.AddEmployee ->{
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
            is ServiceCreateEvent.RemoveEmployee ->{
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
            is ServiceCreateEvent.OnToggleEmployeeMenu -> {
                _state.value = _state.value.copy(employeeSelectionExpanded = !state.value.employeeSelectionExpanded)
            }
            is ServiceCreateEvent.OnEmployeeSelectDismiss -> {
                _state.value = _state.value.copy(employeeSelectionExpanded = false)
            }
            is ServiceCreateEvent.Submit ->{
                runValidation()
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

        createService()
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
                    eventChannel.send(ServiceCreateEvent.ServiceCreatedSuccessfully)
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