package com.example.planetcleanarchitecture.ui_layer.planet_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planetcleanarchitecture.data_layer.repositories.PlanetsRepository
import com.example.planetcleanarchitecture.domain_layer.AddPlanetUseCase
import kotlinx.coroutines.Dispatchers

class PlanetListScreenViewModelProvider(
    private val addPlanetUseCase: AddPlanetUseCase,
    private val mRepository: PlanetsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlanetListScreenViewModel(addPlanetUseCase, mRepository, Dispatchers.IO) as T
    }
}