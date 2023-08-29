package com.example.planetcleanarchitecture.domain_layer

import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.data_layer.repositories.PlanetsRepository
import java.util.Date
import javax.inject.Inject

class AddPlanetUseCase @Inject constructor(private val mRepository: PlanetsRepository) {

    suspend operator fun invoke(planet: Planet) {

        if (planet.name.isEmpty()) {
            throw Exception("Please specify planet name")
        }

        if (planet.distanceY < 0) {
            throw Exception("Please enter positive distance")
        }
        if (planet.discoveredDate.after(Date())) {
            throw Exception("Please enter a discovery date in the past")
        }

        mRepository.addPlanet(planet)
    }

}