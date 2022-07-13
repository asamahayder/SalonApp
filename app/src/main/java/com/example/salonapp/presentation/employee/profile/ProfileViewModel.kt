package com.example.salonapp.presentation.employee.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.salons.GetSalonUseCase
import com.example.salonapp.domain.use_cases.salons.GetSalonsByOwnerIdUseCase
import com.example.salonapp.domain.use_cases.user.GetEmployeeUseCase
import com.example.salonapp.domain.use_cases.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ProfileViewModel @Inject constructor(
    private val getSalonUseCase: GetSalonUseCase,
    private val getSalonsByOwnerIdUseCase: GetSalonsByOwnerIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getEmployeeUseCase: GetEmployeeUseCase,
    private val sessionManager: SessionManager

) : ViewModel(){

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun getEmployee(userId:Int){
        getEmployeeUseCase(userId).onEach { result ->

            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(user = result.data!!, error = null)
                    getSalon(_state.value.user!!.salonId!!)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = "Error, try again.")
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getSalon(salonId:Int){


        getSalonUseCase(salonId).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        salon = result.data!!,
                        isLoading = false,
                        error = null,
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

    private fun initialize(){
        _state.value = ProfileState()

        val userID: Int? = sessionManager.fetchUserId()

        if (userID != null){
            getEmployee(userID)
        }else{
            _state.value = _state.value.copy(error = "Can't fetch employee id.")
        }

    }


    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnInitialize ->{
                initialize()
            }

        }
    }



}