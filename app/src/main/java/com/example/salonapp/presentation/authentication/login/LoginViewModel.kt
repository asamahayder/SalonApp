package com.example.salonapp.presentation.authentication.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.UserLogin
import com.example.salonapp.domain.use_cases.authentication.LoginUseCase
import com.example.salonapp.domain.use_cases.user.GetUserUseCase
import com.example.salonapp.domain.use_cases.validations.ValidateEmailUseCase
import com.example.salonapp.domain.use_cases.validations.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,

    ) : ViewModel(){

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {

            is LoginEvent.EmailLoginChanged -> {
                _state.value = _state.value.copy(emailLogin = event.email)
            }

            is LoginEvent.PasswordLoginChanged -> {
                _state.value = _state.value.copy(passwordLogin = event.password)
            }
            is LoginEvent.SubmitLogin -> {
                runLoginValidation()
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
                    getUser()
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

    private fun getUser() {
        getUserUseCase().onEach { result ->
            when(result){
                is Resource.Success ->{
                    if (result.data != null){
                        _state.value = _state.value.copy(
                            error = "",
                            isLoading = false
                        )
                        viewModelScope.launch {
                            eventChannel.send(LoginEvent.LoginSuccess(result.data.role))
                        }
                    }else{
                        _state.value = _state.value.copy(
                            error = result.message ?: "Unexpected error occurred. Could not log in",
                            isLoading = false
                        )
                    }

                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Unexpected error occurred. Could not log in",
                        isLoading = false
                    )
                }
            }

        }.launchIn(viewModelScope)

    }



}