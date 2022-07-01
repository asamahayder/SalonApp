package com.example.salonapp.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add auth token to requests
 * from https://medium.com/android-news/token-authorization-with-retrofit-android-oauth-2-0-747995c79720
 */

class AuthInterceptor(
    private val sessionManager: SessionManager
    ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}