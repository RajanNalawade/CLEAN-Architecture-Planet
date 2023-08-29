package com.example.planetcleanarchitecture.data_layer.data_sources.remote

import com.example.planetcleanarchitecture.data_layer.repositories.Planet

interface RemoteDataSource {

    suspend fun getPlanets(): List<Planet>

    suspend fun addPlanet(planet: Planet): Planet

    suspend fun deletePlanet(planetId: String)

}