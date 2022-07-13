package com.example.salonapp.presentation.components.profile.profile_edit

import com.example.salonapp.domain.models.User


data class ProfileEditState (
    val isLoading: Boolean = false,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val message: String = "",
    val error: String? = null,
    val user:User? = null,
    val showAlert: Boolean =  false,

    )