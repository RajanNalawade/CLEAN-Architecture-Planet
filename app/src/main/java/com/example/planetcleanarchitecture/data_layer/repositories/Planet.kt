package com.example.planetcleanarchitecture.data_layer.repositories

import java.util.Date

data class Planet(
    val id: String?,
    val name: String,
    val distanceY: Float,
    val discoveredDate: Date
)
