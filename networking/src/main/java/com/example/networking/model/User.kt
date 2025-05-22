package com.example.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)