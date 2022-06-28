package com.example.salonapp.presentation.login_and_register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.models.ValidationEvent
import com.example.salonapp.domain.use_cases.get_salons.GetSalonsUseCase
import com.example.salonapp.domain.use_cases.register.RegisterUseCase
import com.example.salonapp.domain.use_cases.validations.*
import com.example.salonapp.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  LoginAndRegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val getSalonsUseCase: GetSalonsUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validatePasswordConfirmUseCase: ValidatePasswordConfirmUseCase,
    private val savesStateHandle: SavedStateHandle
) : ViewModel(){

    private val _state = mutableStateOf(LoginAndRegisterState())
    val state: State<LoginAndRegisterState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {

    }

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is RegistrationFormEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(passwordConfirm = event.passwordConfirm)
            }
            is RegistrationFormEvent.FirstnameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstname)
            }
            is RegistrationFormEvent.LastnameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastname)
            }
            is RegistrationFormEvent.PhoneChanged -> {
                _state.value = _state.value.copy(phone = event.phone)
            }
            is RegistrationFormEvent.SetRole -> {
                _state.value = _state.value.copy(role = event.role)
                _state.value = _state.value.copy(currentScreen = LoginAndRegisterScreen.RegisterDetails)
            }
            is RegistrationFormEvent.Submit -> {
//                getSalonsUseCase().launchIn(viewModelScope)
                runValidation()
            }
        }
    }

    private fun runValidation(){
        val emailResult = validateEmailUseCase.execute(_state.value.email)
        val firstnameResult = validateFirstNameUseCase.execute(_state.value.firstName)
        val lastnameResult = validateLastNameUseCase.execute(_state.value.lastName)
        val phoneResult = validatePhoneUseCase.execute(_state.value.phone)
        val passwordResult = validatePasswordUseCase.execute(_state.value.password)
        val passwordConfirmResult = validatePasswordConfirmUseCase.execute(
            passwordConfirm = _state.value.passwordConfirm,
            password = _state.value.password
        )

        val hasError = listOf(
            emailResult,
            firstnameResult,
            lastnameResult,
            phoneResult,
            passwordResult,
            passwordConfirmResult
        ).any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                emailError = emailResult.errorMessage,
                firstNameError = firstnameResult.errorMessage,
                lastNameError = lastnameResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                passwordConfirmError = passwordConfirmResult.errorMessage
            )
            return
        }

        register()

    }

    private fun register(){
        val newUser = UserRegister(
            email = _state.value.email,
            password = _state.value.password,
            firstName = _state.value.firstName,
            lastName = _state.value.lastName,
            phone = _state.value.phone,
            role = _state.value.role
        )

        registerUseCase(newUser).onEach { result ->
            when(result){
                is Resource.Success -> {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvent.Success)
                    }
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