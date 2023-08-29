package com.example.planetcleanarchitecture.ui_layer.add_planet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.planetcleanarchitecture.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditPlanet : Fragment() {

    companion object {
        fun newInstance() = AddEditPlanet()
    }

    private val viewModel: AddEditPlanetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit_planet, container, false)
    }

}