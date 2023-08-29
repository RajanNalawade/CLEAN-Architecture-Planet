package com.example.planetcleanarchitecture.ui_layer.planet_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.planetcleanarchitecture.data_layer.repositories.Planet
import com.example.planetcleanarchitecture.databinding.RowPlanetBinding
import com.example.planetcleanarchitecture.ui_layer.utils.CallbackInterface
import javax.inject.Inject

class PlanetListAdapter @Inject constructor() :
    ListAdapter<Planet, PlanetListAdapter.PlanetViewHolder>(PlanetListDifferentiator()) {

    private lateinit var binding: RowPlanetBinding

    private lateinit var listener: CallbackInterface

    fun setCallbackListener(listener: CallbackInterface) {
        this.listener = listener
    }

    class PlanetViewHolder(private val viewBinding: RowPlanetBinding) :
        ViewHolder(viewBinding.root) {
        fun bind(data: Planet, listener: CallbackInterface) {
            viewBinding.data = data
            viewBinding.listner = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        this.binding = RowPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetViewHolder(this.binding)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.bind(getItem(position), this.listener)
    }

    override fun onBindViewHolder(
        holder: PlanetViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)

        } else {
            if (payloads.last() == true) {
                holder.bind(getItem(position), this.listener)
            }
        }
    }

    class PlanetListDifferentiator : DiffUtil.ItemCallback<Planet>() {
        override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Planet, newItem: Planet): Any? {
            return oldItem.id != newItem.id
        }

    }

}

@BindingAdapter("android:text")
fun TextView.setPlanetDistance(distance: Float) {
    this.text = "$distance Years"
}