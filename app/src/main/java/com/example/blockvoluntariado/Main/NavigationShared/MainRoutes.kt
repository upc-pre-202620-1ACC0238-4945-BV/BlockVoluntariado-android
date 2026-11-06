package com.example.blockvoluntariado.Main.NavigationShared


import kotlinx.serialization.Serializable

@Serializable
data object VolunteerProfileRoute

@Serializable
data class EditVolunteerProfileRoute(val volunteerId: Int)

@Serializable
data class EditVolunteerPreferencesRoute(val volunteerId: Int)

