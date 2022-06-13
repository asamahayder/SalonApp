package com.example.salonapp.presentation.login_and_register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.use_cases.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class  RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase, //We can reuse use cases by injecting multiple when needed.
    private val savesStateHandle: SavedStateHandle
) : ViewModel(){

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    init {

    }

    private fun register(){
        registerUseCase(_state.value.userRegisterDTO).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = RegisterState(message = result.data.toString())
                }
                is Resource.Error -> {
                    _state.value = RegisterState(
                        error = result.message ?: "Unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}