package com.example.blockvoluntariado.core.Di

import android.content.Context
import com.example.blockvoluntariado.core.network.AuthInterceptor
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.remote.AuthService
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.ConvocatoriaService
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Remote.VolunteerService
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
object RemoteModule {

    private const val BASE_URL = "https://api.blockvoluntariado.com/api/"

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenManager: TokenManager
    ): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApplicationService(retrofit: Retrofit): com.example.blockvoluntariado.feature.application.infrastructure.remote.ApplicationService {
        return retrofit.create(com.example.blockvoluntariado.feature.application.infrastructure.remote.ApplicationService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideConvocatoriaService(retrofit: Retrofit): ConvocatoriaService {
        return retrofit.create(ConvocatoriaService::class.java)
    }

    @Provides
    @Singleton
    fun provideParticipationService(retrofit: Retrofit): com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.ParticipationService {
        return retrofit.create(com.example.blockvoluntariado.feature.participationTracking.infrastructure.remote.ParticipationService::class.java)
    }

    @Provides
    @Singleton
    fun provideVolunteerService(retrofit: Retrofit): VolunteerService {
        return retrofit.create(VolunteerService::class.java)
    }
}