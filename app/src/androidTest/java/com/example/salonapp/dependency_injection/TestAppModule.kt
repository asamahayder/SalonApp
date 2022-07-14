package com.example.salonapp.dependency_injection

import android.content.Context
import com.example.salonapp.common.AuthInterceptor
import com.example.salonapp.common.Constants
import com.example.salonapp.common.SessionManager
import com.example.salonapp.data.remote.*
import com.example.salonapp.data.repositories.*
import com.example.salonapp.domain.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager{
        return SessionManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryFake()
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
            .readTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
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
    fun provideBookingApi(okHttpClient: OkHttpClient): BookingAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BookingAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideOpeningHoursApi(okHttpClient: OkHttpClient): OpeningHoursAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(OpeningHoursAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideRequestApi(okHttpClient: OkHttpClient): RequestAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RequestAPI::class.java)
    }

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
    fun provideServiceApi(okHttpClient: OkHttpClient): ServiceAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ServiceAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTextApi(okHttpClient: OkHttpClient): TextAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TextAPI::class.java)
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
    fun provideBookingRepository(
        api: BookingAPI,
        userRepository: UserRepository,
        servicesRepository: ServicesRepository
    ): BookingRepository {
        return BookingRepositoryImplementation(api, userRepository, servicesRepository)
    }

    @Provides
    @Singleton
    fun provideOpeningHoursRepository(
        api: OpeningHoursAPI,
        userRepository: UserRepository
    ): OpeningHoursRepository {
        return OpeningHoursRepositoryImplementation(api, userRepository)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(
        api: RequestAPI,
        userRepository: UserRepository,
        salonRepository: SalonRepository
    ): RequestRepository {
        return RequestRepositoryImplementation(api, userRepository, salonRepository)
    }

    @Provides
    @Singleton
    fun provideSalonRepository(api: SalonAPI, userRepository: UserRepository): SalonRepository {
        return SalonRepositoryImplementation(api, userRepository)
    }

    @Provides
    @Singleton
    fun provideServiceRepository(
        api: ServiceAPI,
        salonRepository: SalonRepository,
        userRepository: UserRepository
    ): ServicesRepository {
        return ServiceRepositoryImplementation(api, salonRepository, userRepository)
    }

    @Provides
    @Singleton
    fun provideTextRepository(api: TextAPI): TextRepository {
        return TextRepositoryImplementation(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserAPI): UserRepository {
        return UserRepositoryImplementation(api)
    }

}