package com.example.salonapp.presentation.salon

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.models.Salon
import com.example.salonapp.domain.use_cases.get_salon.GetSalonUseCase
import com.example.salonapp.domain.use_cases.get_salons.GetSalonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class  SalonViewModel @Inject constructor(
    private val getSalonUseCase: GetSalonUseCase, //We can reuse use cases by injecting multiple when needed.
    savesStateHandle: SavedStateHandle
) : ViewModel(){

    private val _state = mutableStateOf(SalonState())
    val state: State<SalonState> = _state

    init {
        savesStateHandle.get<Int>(Constants.PARAM_SALON_ID)?.let { id ->
            getSalon(id)
        }
    }

    private fun getSalon(id: Int){
        getSalonUseCase(id).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = SalonState(salon = result.data ?: Salon())
                }
                is Resource.Error -> {
                    _state.value = SalonState(error = result.message ?: "Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = SalonState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}