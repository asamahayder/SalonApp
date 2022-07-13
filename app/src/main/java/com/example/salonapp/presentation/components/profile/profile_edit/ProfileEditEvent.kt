package com.example.salonapp.presentation.components.profile.profile_edit

sealed class ProfileEditEvent {
    data class FirstnameChanged(val firstname: String) : ProfileEditEvent()
    data class LastnameChanged(val lastname: String) : ProfileEditEvent()
    data class PhoneChanged(val phone: String) : ProfileEditEvent()

    object OnInitialize : ProfileEditEvent()

    object OnSubmit: ProfileEditEvent()
    object OnUpdateSuccess: ProfileEditEvent()
    object OnDeleteSuccess: ProfileEditEvent()

    object OnShowAlert: ProfileEditEvent()
    object OnDismissAlert: ProfileEditEvent()

    object OnDeleteUser: ProfileEditEvent()
}