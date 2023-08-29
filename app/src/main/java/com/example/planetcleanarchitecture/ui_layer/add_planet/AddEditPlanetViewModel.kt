package com.example.planetcleanarchitecture.ui_layer.add_planet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.planetcleanarchitecture.R
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.data_layer.repositories.PlanetsRepository
import com.example.planetcleanarchitecture.data_layer.repositories.WorkResult
import com.example.planetcleanarchitecture.domain_layer.AddPlanetUseCase
import com.example.planetcleanarchitecture.ui_layer.navigation.PlanetsDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class AddEditPlanetUiState(
    val planetName: String = "",
    val planetDistanceLy: Float = 1.0F,
    val planetDiscovered: Date = Date(),
    val isLoading: Boolean = false,
    val isPlanetSaved: Boolean = false,
    val isPlanetSaving: Boolean = false,
    val planetSavingError: Int? = null
)

@HiltViewModel
class AddEditPlanetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addPlanetUseCase: AddPlanetUseCase,
    private val mRepository: PlanetsRepository
) : ViewModel() {
    private val planet_id: String? = savedStateHandle[PlanetsDestinationsArgs.PLANET_ID_ARG]

    private val _uiState = MutableStateFlow(AddEditPlanetUiState())

    init {
        if (planet_id != null) {
            loadPlanet(planet_id)
        }
    }

    fun setPlanetName(name: String) {
        _uiState.update { it.copy(planetName = name) }
    }

    fun setPlanetDistanceY(distance: Float) {
        _uiState.update { it.copy(planetDistanceLy = distance) }
    }

    fun savePlanet() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isPlanetSaving = true) }

                addPlanetUseCase(
                    Planet(
                        planet_id,
                        _uiState.value.planetName,
                        _uiState.value.planetDistanceLy,
                        _uiState.value.planetDiscovered
                    )
                )

                _uiState.update { it.copy(isPlanetSaved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(planetSavingError = R.string.error_saving_planet) }
            } finally {
                _uiState.update { it.copy(isPlanetSaving = false) }
            }
        }
    }

    fun loadPlanet(planetID: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = mRepository.getPlanetLiveData(planetID).asFlow().first()
            if (result !is WorkResult.Success || result.data == null) {
                _uiState.update { it.copy(isLoading = false) }
            } else {
                val planet = result.data
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        planetName = planet.name,
                        planetDistanceLy = planet.distanceY,
                        planetDiscovered = planet.discoveredDate
                    )
                }
            }
        }
    }
}