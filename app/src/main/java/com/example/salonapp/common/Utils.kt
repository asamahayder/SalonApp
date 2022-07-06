package com.example.salonapp.common

class Utils {

    companion object {
        fun formatName(firstName: String?, lastname:String?): String{
            if (firstName.isNullOrEmpty() || lastname.isNullOrEmpty()){
                return "NameError"
            }

            var firstNameFirstChar = firstName[0].uppercaseChar()

            var firstNameWithoutFirstChar = firstName.substring(1)

            var newFirstName = firstNameFirstChar + firstNameWithoutFirstChar

            var lastnameFirstChar = lastname[0].uppercaseChar()

            return "$newFirstName $lastnameFirstChar."

        }
    }

}