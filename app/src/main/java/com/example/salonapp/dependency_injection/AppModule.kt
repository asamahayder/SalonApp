package com.example.salonapp.dependency_injection

import android.content.Context
import com.example.salonapp.common.AuthInterceptor
import com.example.salonapp.common.Constants
import com.example.salonapp.common.SessionManager
import com.example.salonapp.data.remote.AuthAPI
import com.example.salonapp.data.remote.SalonAPI
import com.example.salonapp.data.remote.UserAPI
import com.example.salonapp.data.repositories.AuthRepositoryImplementation
import com.example.salonapp.data.repositories.SalonRepositoryImplementation
import com.example.salonapp.data.repositories.UserRepositoryImplementation
import com.example.salonapp.domain.repositories.AuthRepository
import com.example.salonapp.domain.repositories.SalonRepository
import com.example.salonapp.domain.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSalonApi(okHttpClient: OkHttpClient): SalonAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SalonAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager{
        return SessionManager(context)
    }


    @Provides
    @Singleton
    fun provideUserApi(okHttpClient: OkHttpClient): UserAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor{
        return  AuthInterceptor(sessionManager)
    }

    @Provides
    @Singleton
    fun provideOkHTTPClient(interceptor: AuthInterceptor): OkHttpClient{
        return  OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient): AuthAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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