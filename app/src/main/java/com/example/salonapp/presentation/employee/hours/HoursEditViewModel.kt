package com.example.salonapp.presentation.employee.hours

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salonapp.common.Constants
import com.example.salonapp.common.Resource
import com.example.salonapp.common.SessionManager
import com.example.salonapp.domain.use_cases.opening_hours.GetDefaultOpeningHoursUseCase
import com.example.salonapp.domain.use_cases.opening_hours.UpdateOpeningHoursUseCase
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
class  HoursEditViewModel @Inject constructor(
    private val getDefaultOpeningHoursUseCase: GetDefaultOpeningHoursUseCase,
    private val validateOpeningHourUseCase: ValidateOpeningHourUseCase,
    private val updateOpeningHoursUseCase: UpdateOpeningHoursUseCase,
    private val sessionManager: SessionManager
) : ViewModel(){

    private val _state = mutableStateOf(HoursEditState())
    val state: State<HoursEditState> = _state

    private val eventChannel = Channel<HoursEditEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        initialize()
    }

    private fun initialize(){

        _state.value = HoursEditState()

        val userId = sessionManager.fetchUserId()

        if (userId != null){
            _state.value = _state.value.copy(userId = userId)
            getDefaultOpeningHoursUseCase(userId).onEach {result ->
                when(result){
                    is Resource.Success -> {
                        val openingHours = result.data!!
                        _state.value = _state.value.copy(isLoading = false, error = null, openingHours = openingHours)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = "Error, try again.")
                    }
                }

            }.launchIn(viewModelScope)

        }else{
            _state.value = _state.value.copy(isLoading = false, error = "Error, try again.")
        }

    }

    fun onEvent(event: HoursEditEvent) {

        when(event) {
            is HoursEditEvent.OnInitialize -> {
                initialize()
            }
            is HoursEditEvent.OnSubmit -> {
                runValidation()
            }
            is HoursEditEvent.OnSetMondayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(mondayStart = event.mondayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetMondayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(mondayEnd = event.mondayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetMondayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(mondayOpen = event.mondayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetTuesdayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(tuesdayStart = event.tuesdayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetTuesdayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(tuesdayEnd = event.tuesdayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetTuesdayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(tuesdayOpen = event.tuesdayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetWednesdayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(wednessdayStart = event.wednesdayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetWednesdayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(wednessdayEnd = event.wednesdayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetWednesdayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(wednessdayOpen = event.wednesdayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetThursdayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(thursdayStart = event.thursdayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetThursdayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(thursdayEnd = event.thursdayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetThursdayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(thursdayOpen = event.thursdayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetFridayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(fridayStart = event.fridayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetFridayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(fridayEnd = event.fridayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetFridayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(fridayOpen = event.fridayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetSaturdayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(saturdayStart = event.saturdayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetSaturdayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(saturdayEnd = event.saturdayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetSaturdayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(saturdayOpen = event.saturdayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetSundayStart -> {
                val newOpeningHours = _state.value.openingHours!!.copy(sundayStart = event.sundayStart)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetSundayEnd -> {
                val newOpeningHours = _state.value.openingHours!!.copy(sundayEnd = event.sundayEnd)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }
            is HoursEditEvent.OnSetSundayOpen -> {
                val newOpeningHours = _state.value.openingHours!!.copy(sundayOpen = event.sundayOpen)
                _state.value = _state.value.copy(openingHours = newOpeningHours)
            }

        }
    }


    private fun runValidation(){

        if (_state.value.openingHours == null) return

        val mondayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.mondayStart, _state.value.openingHours!!.mondayEnd)
        val tuesdayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.tuesdayStart, _state.value.openingHours!!.tuesdayEnd)
        val wednessdayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.wednessdayStart, _state.value.openingHours!!.wednessdayEnd)
        val thursdayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.thursdayStart, _state.value.openingHours!!.thursdayEnd)
        val fridayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.fridayStart, _state.value.openingHours!!.fridayEnd)
        val saturdayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.saturdayStart, _state.value.openingHours!!.saturdayEnd)
        val sundayResult = validateOpeningHourUseCase.execute(_state.value.openingHours!!.sundayStart, _state.value.openingHours!!.sundayEnd)


        val hasError = listOf(
            mondayResult,
            tuesdayResult,
            wednessdayResult,
            thursdayResult,
            fridayResult,
            saturdayResult,
            sundayResult
        ).any { !it.successful }

        if(hasError) {
            _state.value = _state.value.copy(
                validationError = true
            )
            return
        }

        updateHours()

    }

    private fun updateHours(){
        updateOpeningHoursUseCase(_state.value.openingHours!!).onEach {result ->

            when(result){
                is Resource.Success -> {
                    eventChannel.send(HoursEditEvent.OnUpdateSuccess)
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