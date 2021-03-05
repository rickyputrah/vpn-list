package com.rickyputrah.express.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickyputrah.express.R
import com.rickyputrah.express.model.LocationItemModel

class LocationListAdapter(private val context: Context) :
    RecyclerView.Adapter<LocationViewHolder>() {

    var dataset: List<LocationItemModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_list_item, parent, false)
        return LocationViewHolder(view, context)
    }

    override fun getItemCount() = dataset.count()

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        dataset.getOrNull(position)?.let {
            (holder as? LocationViewHolder)?.bind(it)
        }
    }
}