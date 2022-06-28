package com.example.salonapp.dependency_injection

import com.example.salonapp.common.Constants
import com.example.salonapp.data.remote.AuthAPI
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.data.remote.UserAPI
import com.example.salonapp.data.repositories.AuthRepositoryImplementation
import com.example.salonapp.data.repositories.SalonRepositoryImplementation
import com.example.salonapp.data.repositories.UserRepositoryImplementation
import com.example.salonapp.domain.repositories.AuthRepository
import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.UserRepository
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
    fun provideUserApi(): UserAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(): AuthAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideSalonRepository(api: SalonAPI): SalonRepository {
        return SalonRepositoryImplementation(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserAPI): UserRepository {
        return UserRepositoryImplementation(api)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthAPI): AuthRepository {
        return AuthRepositoryImplementation(api)
    }




}