package com.example.planetcleanarchitecture.data_layer.data_sources.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PlanetsDao {

    /**
     * Observes list of planets.
     *
     * @return all planets.
     */
    @Query("SELECT * FROM Planets")
    fun observePlanets(): LiveData<List<PlanetEntity>>

    /**
     * Observes a single planet.
     *
     * @param planetId the planet id.
     * @return the planet with planetId.
     */
    @Query("SELECT * FROM Planets WHERE planetId = :planetId")
    fun observePlanetById(planetId: String): LiveData<PlanetEntity?>

    /**
     * Select all planets from the planets table.
     *
     * @return all planets.
     */
    @Query("SELECT * FROM Planets")
    suspend fun getPlanets(): List<PlanetEntity>

    /**
     * Select a planet by id.
     *
     * @param planetId the planet id.
     * @return the planet with planetId.
     */
    @Query("SELECT * FROM Planets WHERE planetId = :planetId")
    suspend fun getPlanetById(planetId: String): PlanetEntity?

    /**
     * Insert a planet in the database. If the planet already exists, replace it.
     *
     * @param planet the planet to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet: PlanetEntity)

    /**
     * Update a planet.
     *
     * @param planet planet to be updated
     * @return the number of planets updated. This should always be 1.
     */
    @Update
    suspend fun updatePlanet(planet: PlanetEntity): Int

    /**
     * Delete a planet by id.
     *
     * @return the number of planets deleted. This should always be 1.
     */
    @Query("DELETE FROM Planets WHERE planetId = :planetId")
    suspend fun deletePlanetById(planetId: String): Int

    /**
     * Delete all planets.
     */
    @Query("DELETE FROM Planets")
    suspend fun deletePlanets()

    @Transaction
    suspend fun setPlanets(planets: List<PlanetEntity>) {
        deletePlanets()
        planets.forEach { insertPlanet(it) }
    }
}