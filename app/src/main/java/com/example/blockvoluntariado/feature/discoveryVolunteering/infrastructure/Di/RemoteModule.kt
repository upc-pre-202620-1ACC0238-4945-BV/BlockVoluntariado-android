package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Di

import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Remote.ConvocatoriaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .baseUrl("https://api.blockvoluntariado.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideConvocatoriaService(retrofit: Retrofit): ConvocatoriaService{
        return retrofit.create(ConvocatoriaService::class.java)
    }


}