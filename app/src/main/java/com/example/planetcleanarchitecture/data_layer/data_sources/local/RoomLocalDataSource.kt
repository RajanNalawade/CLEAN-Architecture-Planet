package com.example.planetcleanarchitecture.data_layer.data_sources.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.data_layer.repositories.WorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class RoomLocalDataSource internal constructor(
    private val planetsDao: PlanetsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {
    override fun getPlanetsLiveData(): LiveData<WorkResult<List<Planet>>> {
        return planetsDao.observePlanets().map {
            WorkResult.Success(it.map { planetEntity -> planetEntity.toPlanet() })
        }
    }

    override fun getPlanetLiveData(planetId: String): LiveData<WorkResult<Planet?>> {
        return planetsDao.observePlanetById(planetId).map {
            WorkResult.Success(it?.toPlanet())
        }
    }

    override suspend fun setPlanets(planets: List<Planet>) {
        planetsDao.setPlanets(planets.map { it.toPlanetEntity() })
    }

    override suspend fun addPlanet(planet: Planet) {
        planetsDao.insertPlanet(planet.toPlanetEntity())
    }

    override suspend fun deletePlanet(planetId: String) = withContext<Unit>(ioDispatcher) {
        planetsDao.deletePlanetById(planetId)
    }
}