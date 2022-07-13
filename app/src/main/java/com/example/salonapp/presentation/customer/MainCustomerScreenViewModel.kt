package com.example.salonapp.presentation.customer

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.booking.GetBookingByEmployeeIdUseCase
import com.example.salonapp.domain.use_cases.booking.GetMyBookingsUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.user.GetEmployeeUseCase
import com.example.salonapp.presentation.employee.schedule.EmployeeScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  MainCustomerScreenViewModel @Inject constructor(
    private val getMyBookingsUseCase: GetMyBookingsUseCase
) : ViewModel(){

    private val _state = mutableStateOf(MainCustomerScreenState())
    val state: State<MainCustomerScreenState> = _state

    init {
        initialize()
    }

    private fun initialize(){
        _state.value = MainCustomerScreenState()

        getMyBookingsUseCase().onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(myBookings = result.data!!, isLoading = false, error = null)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unknown error")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun onEvent(event: MainCustomerScreenEvent) {
        when(event) {
            is MainCustomerScreenEvent.OnInitialize -> {
                initialize()
            }
        }
    }

}