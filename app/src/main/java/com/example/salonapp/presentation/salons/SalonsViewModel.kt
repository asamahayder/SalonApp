package com.example.salonapp.presentation.salons

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.use_cases.get_salons.GetSalonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class  SalonsViewModel @Inject constructor(
    private val getSalonsUseCase: GetSalonsUseCase //We can reuse use cases by injecting multiple when needed.
) : ViewModel(){

    private val _state = mutableStateOf(SalonsState())
    val state: State<SalonsState> = _state

    init {
        getSalons()
    }

    private fun getSalons(){
        getSalonsUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = SalonsState(salons = result.data  ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = SalonsState(error = result.message ?: "Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = SalonsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}