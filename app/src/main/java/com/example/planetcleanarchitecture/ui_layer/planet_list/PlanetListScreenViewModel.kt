package com.example.planetcleanarchitecture.ui_layer.planet_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.data_layer.repositories.PlanetsRepository
import com.example.planetcleanarchitecture.data_layer.repositories.WorkResult
import com.example.planetcleanarchitecture.di.IoDispatcher
import com.example.planetcleanarchitecture.domain_layer.AddPlanetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

/*data class PlanetsListUiState(
    val planets: List<Planet> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)*/

@HiltViewModel
class PlanetListScreenViewModel @Inject constructor(
    private val addPlanetUseCase: AddPlanetUseCase,
    private val mRepository: PlanetsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val planets: MutableLiveData<WorkResult<List<Planet>>> =
        mRepository.getPlanetsLiveData() as MutableLiveData<WorkResult<List<Planet>>>

    init {
        addSamplePlanets()
    }

    fun addSamplePlanets() {
        viewModelScope.launch {
            val planets = arrayOf(
                Planet("0", name = "Skaro", distanceY = 0.5F, discoveredDate = Date()),
                Planet("1", name = "Trenzalore", distanceY = 5F, discoveredDate = Date()),
                Planet("2", name = "Galifrey", distanceY = 80F, discoveredDate = Date()),
            )
            planets.forEach {
                withContext(dispatcher) {
                    addPlanetUseCase(it)
                }
            }
        }
    }

    fun deletePlanet(planetID: String) {
        viewModelScope.launch {
            withContext(dispatcher) {
                mRepository.deletePlanet(planetID)
            }
        }
    }

    fun refreshPlanetsList() {
        viewModelScope.launch {
            withContext(dispatcher) {
                mRepository.refreshPlanets()
            }
        }
    }
}