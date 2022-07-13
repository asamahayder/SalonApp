package com.example.salonapp.presentation.components.profile.profile_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.domain.use_cases.user.DeleteUserUseCase
import com.example.salonapp.domain.use_cases.user.GetUserUseCase
import com.example.salonapp.domain.use_cases.user.UpdateUserUseCase
import com.example.salonapp.domain.use_cases.validations.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class  ProfileEditViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase
) : ViewModel(){

    private val _state = mutableStateOf(ProfileEditState())
    val state: State<ProfileEditState> = _state

    private val eventChannel = Channel<ProfileEditEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun initialize(){

        _state.value = ProfileEditState()


        getUserUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    val user = result.data!!

                    _state.value = _state.value.copy(
                        isLoading = false,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        phone = user.phone,
                        error = null,
                        user = user
                    )
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

    fun onEvent(event: ProfileEditEvent) {
        when(event) {
            is ProfileEditEvent.FirstnameChanged -> {
                _state.value = _state.value.copy(firstName = event.firstname)
            }
            is ProfileEditEvent.LastnameChanged -> {
                _state.value = _state.value.copy(lastName = event.lastname)
            }
            is ProfileEditEvent.PhoneChanged -> {
                _state.value = _state.value.copy(phone = event.phone)
            }
            is ProfileEditEvent.OnSubmit -> {
                runValidation()
            }
            is ProfileEditEvent.OnInitialize -> {
                initialize()
            }
            is ProfileEditEvent.OnShowAlert -> {
                _state.value = _state.value.copy(showAlert = true)
            }
            is ProfileEditEvent.OnDismissAlert -> {
                _state.value = _state.value.copy(showAlert = false)
            }
            is ProfileEditEvent.OnDeleteUser -> {
                deleteUser()
            }
        }
    }


    private fun runValidation(){
        val firstnameResult = validateFirstNameUseCase.execute(_state.value.firstName)
        val lastnameResult = validateLastNameUseCase.execute(_state.value.lastName)
        val phoneResult = validatePhoneUseCase.execute(_state.value.phone)

        val hasError = listOf(
            firstnameResult,
            lastnameResult,
            phoneResult
        ).any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                firstNameError = firstnameResult.errorMessage,
                lastNameError = lastnameResult.errorMessage,
                phoneError = phoneResult.errorMessage
            )
            return
        }

        updateUser()

    }

    private fun deleteUser(){

        val userId = _state.value.user!!.id

        deleteUserUseCase(userId).onEach {result ->
            when(result){
                is Resource.Success -> {
                    eventChannel.send(ProfileEditEvent.OnDeleteSuccess)
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

    private fun updateUser(){

        val updatedUser = _state.value.user!!.copy(
            firstName = _state.value.firstName,
            lastName = _state.value.lastName,
            phone = _state.value.phone
        )

        updateUserUseCase(updatedUser).onEach {result ->
            when(result){
                is Resource.Success -> {
                    eventChannel.send(ProfileEditEvent.OnUpdateSuccess)
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


}