package com.example.blockvoluntariado.core.Di

import com.example.blockvoluntariado.feature.authOnboarding.domain.repository.AuthRepository
import com.example.blockvoluntariado.feature.authOnboarding.infrastructure.repository.AuthRepositoryImpl
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
    fun provideApplicationRepository(impl: com.example.blockvoluntariado.feature.application.infrastructure.repository.ApplicationRepositoryImpl): com.example.blockvoluntariado.feature.application.domain.repository.ApplicationRepository

    @Binds
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideConvocatoriaRepository(impl: DiscoveryInMemoryRepository): ConvocatoriasRepository

    @Binds
    fun provideParticipationRepository(impl: com.example.blockvoluntariado.feature.participationTracking.infrastructure.repository.ParticipationRepositoryImpl): com.example.blockvoluntariado.feature.participationTracking.domain.repository.ParticipationRepository

    @Binds
    fun provideVolunteerRepository(impl: VolunteerInMemoryRepository): VolunteerRepository
}