package com.example.blockvoluntariado.Main.NavigationShared


import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class DetailRoute(val id: Int)