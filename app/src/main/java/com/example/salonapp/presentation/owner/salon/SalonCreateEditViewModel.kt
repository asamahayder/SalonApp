package com.example.salonapp.presentation.owner.salon

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.ValidationResult
import com.example.salonapp.domain.use_cases.salons.CreateSalonUseCase
import com.example.salonapp.domain.use_cases.salons.DeleteSalonUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.salons.UpdateSalonUseCase
import com.example.salonapp.domain.use_cases.validations.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  SalonCreateEditViewModel @Inject constructor(
    private val createSalonUseCase: CreateSalonUseCase,
    private val getSalonUseCase: GetSalonUseCase,
    private val updateSalonUseCase: UpdateSalonUseCase,
    private val deleteSalonUseCase: DeleteSalonUseCase,
    private val validateSalonNameUseCase: ValidateSalonNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateCityUseCase: ValidateCityUseCase,
    private val validatePostCodeUseCase: ValidatePostCodeUseCase,
    private val validateStreetNameUseCase: ValidateStreetNameUseCase,
    private val validateStreetNumberUseCase: ValidateStreetNumberUseCase,
    private val validateSuitUseCase: ValidateSuitUseCase,
    private val validateDoorUseCase: ValidateDoorUseCase
) : ViewModel(){

    private val _state = mutableStateOf(SalonCreateEditState())
    val state: State<SalonCreateEditState> = _state

    private val eventChannel = Channel<SalonCreateEditEvent>()
    val events = eventChannel.receiveAsFlow()


    fun initialize(salonId:Int?){
        _state.value = SalonCreateEditState(salonId = salonId)

        if (salonId != null){

            getSalonUseCase(salonId).onEach {result ->
                when(result){
                    is Resource.Success -> {
                        val salon = result.data!!

                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = null,
                            name = salon.name,
                            email = salon.email,
                            phone = salon.phone,
                            city = salon.city,
                            postCode = salon.postCode,
                            streetName = salon.streetName,
                            streetNumber = salon.streetNumber,
                            suit = salon.suit,
                            door = salon.door,
                            employees = salon.employees
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

        }

    }


    fun onEvent(event: SalonCreateEditEvent) {
        when(event) {
            is SalonCreateEditEvent.NameChanged -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is SalonCreateEditEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is SalonCreateEditEvent.PhoneChanged -> {
                _state.value = _state.value.copy(phone = event.phone)
            }
            is SalonCreateEditEvent.CityChanged -> {
                _state.value = _state.value.copy(city = event.city)
            }
            is SalonCreateEditEvent.PostCodeChanged -> {
                _state.value = _state.value.copy(postCode = event.postCode)
            }
            is SalonCreateEditEvent.StreetNameChanged -> {
                _state.value = _state.value.copy(streetName = event.streetName)
            }
            is SalonCreateEditEvent.StreetNumberChanged -> {
                _state.value = _state.value.copy(streetNumber = event.streetNumber)
            }
            is SalonCreateEditEvent.SuitChanged -> {
                _state.value = _state.value.copy(suit = event.suit)
            }
            is SalonCreateEditEvent.DoorChanged -> {
                _state.value = _state.value.copy(door = event.door)
            }
            is SalonCreateEditEvent.Submit -> {
                runValidation()
            }
            is SalonCreateEditEvent.OnDeleteSalon -> {
                deleteSalon()

            }
            is SalonCreateEditEvent.OnShowDeleteAlert -> {
                _state.value = _state.value.copy(showAlert = true)
            }
            is SalonCreateEditEvent.OnDismissDeleteAlert -> {
                _state.value = _state.value.copy(showAlert = false)
            }
            is SalonCreateEditEvent.OnInitialize -> {
                initialize(event.salonId)
            }
        }
    }

    fun runValidation(){
        val nameResult = validateSalonNameUseCase.execute(_state.value.name)

        val emailResult: ValidationResult

        if (!_state.value.email.isNullOrEmpty()){
            emailResult = validateEmailUseCase.execute(_state.value.email!!)
        }else{
            emailResult = ValidationResult(successful = true)
        }

        val phoneResult = validatePhoneUseCase.execute(_state.value.phone)

        val cityResult = validateCityUseCase.execute(_state.value.city)
        val postCodeResult = validatePostCodeUseCase.execute(_state.value.postCode)
        val streetNameResult = validateStreetNameUseCase.execute(_state.value.streetName)
        val streetNumberResult = validateStreetNumberUseCase.execute(_state.value.streetNumber)

        val suitResult: ValidationResult

        if (!_state.value.suit.isNullOrEmpty()){
            suitResult = validateSuitUseCase.execute(_state.value.suit!!)
        }else{
            suitResult = ValidationResult(successful = true)
        }

        val doorResult: ValidationResult

        if (!_state.value.door.isNullOrEmpty()){
            doorResult = validateDoorUseCase.execute(_state.value.door!!)
        }else{
            doorResult = ValidationResult(successful = true)
        }


        val hasError = listOf(
            nameResult,
            emailResult,
            phoneResult,
            cityResult,
            postCodeResult,
            streetNameResult,
            streetNumberResult,
            suitResult,
            doorResult
        ).any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                cityError = cityResult.errorMessage,
                postCodeError = postCodeResult.errorMessage,
                streetNameError = streetNameResult.errorMessage,
                streetNumberError = streetNumberResult.errorMessage,
                suitError = suitResult.errorMessage,
                doorError = doorResult.errorMessage
            )
            return
        }


        if (_state.value.salonId != null){
            updateSalon()
        }else{
            createSalon()
        }

    }

    private fun deleteSalon(){
        if (_state.value.salonId == null) return

        deleteSalonUseCase(_state.value.salonId!!).onEach {result ->
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

            when(result){
                is Resource.Success -> {
                    eventChannel.send(SalonCreateEditEvent.OnFinishedAction)
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

    private fun updateSalon(){

        if (_state.value.salonId == null) return

        val updatedSalon = Salon(
            id = _state.value.salonId!!,
            name = _state.value.name,
            email = _state.value.email ?: "",
            phone = _state.value.phone,
            city = _state.value.city,
            postCode = _state.value.postCode,
            streetName = _state.value.streetName,
            streetNumber = _state.value.streetNumber,
            suit = _state.value.suit ?: "",
            door = _state.value.door ?: "",
            employees = _state.value.employees,
        )

        updateSalonUseCase(updatedSalon).onEach { result ->

            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

            when(result){
                is Resource.Success -> {
                    eventChannel.send(SalonCreateEditEvent.OnFinishedAction)
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

    private fun createSalon(){
        val newSalon = Salon(
            name = _state.value.name,
            email = _state.value.email ?: "",
            phone = _state.value.phone,
            city = _state.value.city,
            postCode = _state.value.postCode,
            streetName = _state.value.streetName,
            streetNumber = _state.value.streetNumber,
            suit = _state.value.suit ?: "",
            door = _state.value.door ?: ""
        )

        createSalonUseCase(newSalon).onEach { result ->

            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data.toString())

            when(result){
                is Resource.Success -> {
                    eventChannel.send(SalonCreateEditEvent.OnFinishedAction)
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