package com.example.planetcleanarchitecture.data_layer.repositories

import androidx.lifecycle.LiveData
import com.example.planetcleanarchitecture.data_layer.data_sources.local.LocalDataSource
import com.example.planetcleanarchitecture.data_layer.data_sources.remote.RemoteDataSource
import javax.inject.Inject

class PlanetsRepositoryImplementation @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : PlanetsRepository {
    override fun getPlanetLiveData(planetID: String): LiveData<WorkResult<Planet?>> {
        return localDataSource.getPlanetLiveData(planetID)
    }

    override fun getPlanetsLiveData(): LiveData<WorkResult<List<Planet>>> {
        return localDataSource.getPlanetsLiveData()
    }

    override suspend fun addPlanet(planet: Planet) {
        val planetWithID = remoteDataSource.addPlanet(planet)
        localDataSource.addPlanet(planetWithID)
    }

    override suspend fun deletePlanet(planetID: String) {
        localDataSource.deletePlanet(planetID)
    }

    override suspend fun refreshPlanets() {
        val planets = remoteDataSource.getPlanets()
        localDataSource.setPlanets(planets)
    }
}