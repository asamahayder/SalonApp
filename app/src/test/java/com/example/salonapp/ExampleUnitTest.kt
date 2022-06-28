package com.example.salonapp

import com.example.salonapp.common.Constants
import com.example.salonapp.data.remote.SalonAPI
import org.junit.Test

import org.junit.Assert.*

import com.example.salonapp.dependency_injection.AppModule
import kotlinx.coroutines.test.runTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSalonAPI() = runTest{
        getSalons()
    }

    private suspend fun getSalons(): String{
        var api = createRetrofitInstance()
        return api.getSalons().toString()
    }

    private fun createRetrofitInstance(): SalonAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SalonAPI::class.java)
    }
}