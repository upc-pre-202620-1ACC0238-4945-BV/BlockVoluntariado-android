package com.example.blockvoluntariado.Main.NavigationDiscoveryVolunteering


import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class DetailRoute(val id: Int)