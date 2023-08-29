package com.example.planetcleanarchitecture.ui_layer.planet_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planetcleanarchitecture.R
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.data_layer.repositories.WorkResult
import com.example.planetcleanarchitecture.databinding.FragmentPlanetListScreenBinding
import com.example.planetcleanarchitecture.ui_layer.utils.CallbackInterface
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class PlanetListScreen : Fragment(), CallbackInterface, OnClickListener {

    private val viewModel: PlanetListScreenViewModel by viewModels()

    private lateinit var binding: FragmentPlanetListScreenBinding

    @Inject
    lateinit var planetAdapter: PlanetListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanetListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPlanets.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        //adapter = PlanetListAdapter()
        planetAdapter.setCallbackListener(this)

        binding.viewModel = viewModel
        binding.dataAdapter = planetAdapter

        viewModel.planets.observe(viewLifecycleOwner, Observer { out ->

            binding.circularProcessBar.visibility = View.GONE
            binding.txtError.visibility = View.GONE

            when (out) {
                is WorkResult.Error -> {
                    binding.txtError.visibility = View.VISIBLE
                    binding.txtError.text = out.exception.message
                }

                is WorkResult.Loading -> binding.circularProcessBar.visibility = View.VISIBLE
                is WorkResult.Success -> {
                    //PlanetsListUiState(planets = out.data)
                    planetAdapter.submitList(out.data)
                }
            }
        })
    }

    override fun onClickDeletePlanet(id: String) {
        viewModel?.deletePlanet(id)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRefresh -> viewModel.refreshPlanetsList()
            R.id.btnSamples -> viewModel.addSamplePlanets()
        }
    }

}