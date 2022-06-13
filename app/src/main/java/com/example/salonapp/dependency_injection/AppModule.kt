package com.example.salonapp.dependency_injection

import com.example.salonapp.common.Constants
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.data.repositories.SalonRepositoryImplementation
import com.example.salonapp.domain.repositories.SalonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSalonApi(): SalonAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SalonAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideSalonRepository(api: SalonAPI): SalonRepository{
        return SalonRepositoryImplementation(api)
    }

}