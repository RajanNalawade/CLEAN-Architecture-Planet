package com.example.planetcleanarchitecture.data_layer.repositories

import androidx.lifecycle.LiveData

interface PlanetsRepository {

    fun getPlanetLiveData(planetID: String): LiveData<WorkResult<Planet?>>

    fun getPlanetsLiveData(): LiveData<WorkResult<List<Planet>>>

    suspend fun addPlanet(planet: Planet)

    suspend fun deletePlanet(planetID: String)

    //This will update the local data source from the remote data source:
    suspend fun refreshPlanets()
}