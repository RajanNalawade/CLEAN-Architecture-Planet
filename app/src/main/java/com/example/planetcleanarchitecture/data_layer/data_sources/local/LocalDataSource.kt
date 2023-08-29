package com.example.planetcleanarchitecture.data_layer.data_sources.local

import androidx.lifecycle.LiveData
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.data_layer.repositories.WorkResult

interface LocalDataSource {
    fun getPlanetLiveData(planetID: String): LiveData<WorkResult<Planet?>>
    fun getPlanetsLiveData(): LiveData<WorkResult<List<Planet>>>
    suspend fun setPlanets(planets: List<Planet>)
    suspend fun addPlanet(planet: Planet)
    suspend fun deletePlanet(planetId: String)
}