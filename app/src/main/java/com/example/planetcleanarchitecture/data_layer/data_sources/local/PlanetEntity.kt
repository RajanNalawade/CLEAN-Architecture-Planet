package com.example.planetcleanarchitecture.data_layer.data_sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import java.util.Date
import java.util.UUID

@Entity(tableName = "planets")
data class PlanetEntity(
    @PrimaryKey var planetId: String,

    var name: String = "",

    var distanceLy: Float = 1.0F,

    var discovered: Date = Date(),
) {
    fun toPlanet(): Planet {
        return Planet(
            id = planetId,
            name = name,
            distanceY = distanceLy,
            discoveredDate = discovered
        )
    }
}

fun Planet.toPlanetEntity(): PlanetEntity {
    return PlanetEntity(
        planetId = id ?: UUID.randomUUID().toString(),
        name = name,
        distanceLy = distanceY,
        discovered = discoveredDate
    )
}