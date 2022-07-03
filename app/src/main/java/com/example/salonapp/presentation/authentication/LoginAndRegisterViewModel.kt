package com.example.salonapp.presentation.authentication

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.models.UserRegister
import com.example.salonapp.domain.use_cases.get_salons.GetSalonsUseCase
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
class  LoginAndRegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
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

    private val eventChannel = Channel<LoginAndRegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {

    }

    fun onEvent(event: LoginAndRegisterEvent) {
        when(event) {
            is LoginAndRegisterEvent.EmailRegisterChanged -> {
                _state.value = _state.value.copy(emailRegister = event.email)
            }
            is LoginAndRegisterEvent.EmailLoginChanged -> {
                _state.value = _state.value.copy(emailLogin = event.email)
            }
            is LoginAndRegisterEvent.PasswordRegisterChanged -> {
                _state.value = _state.value.copy(passwordRegister = event.password)
            }
            is LoginAndRegisterEvent.PasswordLoginChanged -> {
                _state.value = _state.value.copy(passwordLogin = event.password)
            }
            is LoginAndRegisterEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(passwordConfirm = event.passwordConfirm)
            }
            is LoginAndRegisterEvent.FirstnameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstname)
            }
            is LoginAndRegisterEvent.LastnameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastname)
            }
            is LoginAndRegisterEvent.PhoneChanged -> {
                _state.value = _state.value.copy(phone = event.phone)
            }
            is LoginAndRegisterEvent.SetRole -> {
                _state.value = _state.value.copy(role = event.role)
                _state.value = _state.value.copy(currentScreen = LoginAndRegisterScreen.RegisterDetails)
            }
            is LoginAndRegisterEvent.SubmitRegister -> {
                runRegisterValidation()
            }
            is LoginAndRegisterEvent.SubmitLogin -> {
                runLoginValidation()
            }
            is LoginAndRegisterEvent.GoToRegister -> {
                _state.value = _state.value.copy(currentScreen = LoginAndRegisterScreen.RegisterRoleSelection)
            }
            is LoginAndRegisterEvent.BackCalledFromRegisterDetails -> {
                _state.value = _state.value.copy(currentScreen = LoginAndRegisterScreen.RegisterRoleSelection)
            }
            is LoginAndRegisterEvent.BackCalledFromRegisterRoles -> {
                _state.value = _state.value.copy(currentScreen = LoginAndRegisterScreen.Login)
            }

        }
    }

    private fun runLoginValidation(){
        val emailResult = validateEmailUseCase.execute(_state.value.emailLogin)
        val passwordResult = validatePasswordUseCase.execute(_state.value.passwordLogin)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                emailLoginError = emailResult.errorMessage,
                passwordLoginError = passwordResult.errorMessage
            )
            return
        }

        login(_state.value.emailLogin, _state.value.passwordLogin)
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
                        eventChannel.send(LoginAndRegisterEvent.LoginOrRegisterSuccess)
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