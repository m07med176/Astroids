package com.udacity.asteroidradar.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.models.Asteroid
import com.udacity.asteroidradar.databinding.DataListItemBinding


class AsteroidAdapter(private val clickListener: AsteroidListener)
    : ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(clickListener , getItem(position)!!)
    }

    class AsteroidViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = DataListItemBinding.bind(itemView)

        fun bind(clickListener: AsteroidListener , asteroidsItem: Asteroid){
            binding.asteroid = asteroidsItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.data_list_item, parent, false)
                return AsteroidViewHolder(view)
            }
        }
    }

    class AsteroidDiffCallback :
        DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.isPotentiallyHazardous == newItem.isPotentiallyHazardous &&
                    oldItem.codename == newItem.codename &&
                    oldItem.closeApproachDate == newItem.closeApproachDate &&
                    oldItem.absoluteMagnitude == newItem.absoluteMagnitude &&
                    oldItem.distanceFromEarth == newItem.distanceFromEarth &&
                    oldItem.estimatedDiameter == newItem.estimatedDiameter &&
                    oldItem.relativeVelocity == newItem.relativeVelocity
        }
    }

    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}