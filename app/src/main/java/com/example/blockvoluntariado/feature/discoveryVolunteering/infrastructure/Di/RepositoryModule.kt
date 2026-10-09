package com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Di

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.ConvocatoriasRepository
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Repositories.ConvocatoriaRepositoryImpl
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Repositories.InMemoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

interface RepositoryModule {

    @Binds
    fun provideConvocatoriaRepository(impl: InMemoryRepository): ConvocatoriasRepository

}