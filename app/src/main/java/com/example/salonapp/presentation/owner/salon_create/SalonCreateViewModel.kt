package com.example.salonapp.presentation.owner.salon_create

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.models.ValidationResult
import com.example.salonapp.domain.use_cases.create_salon.CreateSalonUseCase
import com.example.salonapp.domain.use_cases.validations.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  SalonCreateViewModel @Inject constructor(
    private val createSalonUseCase: CreateSalonUseCase,
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

    private val _state = mutableStateOf(SalonCreateState())
    val state: State<SalonCreateState> = _state

    private val eventChannel = Channel<SalonCreateEvent>()
    val events = eventChannel.receiveAsFlow()

    init {

    }

    fun onEvent(event: SalonCreateEvent) {
        when(event) {
            is SalonCreateEvent.NameChanged -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is SalonCreateEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is SalonCreateEvent.PhoneChanged -> {
                _state.value = _state.value.copy(phone = event.phone)
            }
            is SalonCreateEvent.CityChanged -> {
                _state.value = _state.value.copy(city = event.city)
            }
            is SalonCreateEvent.PostCodeChanged -> {
                _state.value = _state.value.copy(postCode = event.postCode)
            }
            is SalonCreateEvent.StreetNameChanged -> {
                _state.value = _state.value.copy(streetName = event.streetName)
            }
            is SalonCreateEvent.StreetNumberChanged -> {
                _state.value = _state.value.copy(streetNumber = event.streetNumber)
            }
            is SalonCreateEvent.SuitChanged -> {
                _state.value = _state.value.copy(suit = event.suit)
            }
            is SalonCreateEvent.DoorChanged -> {
                _state.value = _state.value.copy(door = event.door)
            }
            is SalonCreateEvent.Submit -> {
                runValidation()
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

        createSalon()
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
                    eventChannel.send(SalonCreateEvent.SalonCreatedSuccessfully)
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