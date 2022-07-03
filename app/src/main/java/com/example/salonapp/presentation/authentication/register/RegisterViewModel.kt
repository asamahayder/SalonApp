package com.example.salonapp.presentation.authentication.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.use_cases.login.LoginUseCase
import com.example.salonapp.domain.use_cases.register.RegisterUseCase
import com.example.salonapp.domain.use_cases.validations.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validatePasswordConfirmUseCase: ValidatePasswordConfirmUseCase,
) : ViewModel(){

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when(event) {
            is RegisterEvent.EmailRegisterChanged -> {
                _state.value = _state.value.copy(emailRegister = event.email)
            }
            is RegisterEvent.PasswordRegisterChanged -> {
                _state.value = _state.value.copy(passwordRegister = event.password)
            }
            is RegisterEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(passwordConfirm = event.passwordConfirm)
            }
            is RegisterEvent.FirstnameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstname)
            }
            is RegisterEvent.LastnameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastname)
            }
            is RegisterEvent.PhoneChanged -> {
                _state.value = _state.value.copy(phone = event.phone)
            }
            is RegisterEvent.SetRole -> {
                _state.value = _state.value.copy(role = event.role)
            }
            is RegisterEvent.Submit -> {
                runRegisterValidation()
            }

        }
    }


    private fun runRegisterValidation(){
        val emailResult = validateEmailUseCase.execute(_state.value.emailRegister)
        val firstnameResult = validateFirstNameUseCase.execute(_state.value.firstName)
        val lastnameResult = validateLastNameUseCase.execute(_state.value.lastName)
        val phoneResult = validatePhoneUseCase.execute(_state.value.phone)
        val passwordResult = validatePasswordUseCase.execute(_state.value.passwordRegister)
        val passwordConfirmResult = validatePasswordConfirmUseCase.execute(
            passwordConfirm = _state.value.passwordConfirm,
            password = _state.value.passwordRegister
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
                emailRegisterError = emailResult.errorMessage,
                firstNameError = firstnameResult.errorMessage,
                lastNameError = lastnameResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                passwordRegisterError = passwordResult.errorMessage,
                passwordConfirmError = passwordConfirmResult.errorMessage
            )
            return
        }

        register()

    }

    private fun login(email: String, password: String){
        val userLogin = UserLogin(
            email = email,
            password = password
        )

        loginUseCase(userLogin).onEach { result ->
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data?: "")

            when(result) {
                is Resource.Success -> {
                    viewModelScope.launch {
                        eventChannel.send(RegisterEvent.RegisterSuccess)
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

    private fun register(){
        val newUser = UserRegister(
            email = _state.value.emailRegister,
            password = _state.value.passwordRegister,
            firstName = _state.value.firstName,
            lastName = _state.value.lastName,
            phone = _state.value.phone,
            role = _state.value.role
        )

        registerUseCase(newUser).onEach { result ->

            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.message?: "")
            Log.i(Constants.LOGTAG_USECASE_RESULTS, result.data?: "")

            when(result){
                is Resource.Success -> {
                    login(_state.value.emailRegister, _state.value.passwordRegister)
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