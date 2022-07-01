package com.example.salonapp.common

import android.content.Context
import android.content.SharedPreferences
import com.example.salonapp.R

/**
 * Session manager to save and fetch data from SharedPreferences
 * from https://medium.com/android-news/token-authorization-with-retrofit-android-oauth-2-0-747995c79720
 */
class SessionManager(
     context: Context
) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }

    fun saveUserId(id: Int){
        val editor = prefs.edit()
        editor.putString(USER_ID, id.toString())
        editor.apply()
    }

    fun fetchUserId(): Int? {
        val idString: String = prefs.getString(USER_ID, Constants.USER_ID_NOT_FOUND)!!

        return if (idString != Constants.USER_ID_NOT_FOUND) idString.toInt() else null
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}