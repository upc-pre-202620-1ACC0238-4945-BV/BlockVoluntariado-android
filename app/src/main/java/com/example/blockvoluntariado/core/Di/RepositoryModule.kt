package com.example.blockvoluntariado.core.Di

import com.example.blockvoluntariado.feature.discoveryVolunteering.domain.ConvocatoriasRepository
import com.example.blockvoluntariado.feature.discoveryVolunteering.infrastructure.Repositories.InMemoryRepository as DiscoveryInMemoryRepository
import com.example.blockvoluntariado.feature.volunteerProfile.domain.VolunteerRepository
import com.example.blockvoluntariado.feature.volunteerProfile.infrastructure.Repositories.InMemoryRepository as VolunteerInMemoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideConvocatoriaRepository(impl: DiscoveryInMemoryRepository): ConvocatoriasRepository

    @Binds
    fun provideVolunteerRepository(impl: VolunteerInMemoryRepository): VolunteerRepository
}