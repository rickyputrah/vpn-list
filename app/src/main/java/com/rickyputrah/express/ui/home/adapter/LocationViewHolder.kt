package com.rickyputrah.express.ui.home.adapter

import android.content.Context
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rickyputrah.express.R
import com.rickyputrah.express.databinding.LocationListItemBinding
import com.rickyputrah.express.model.LocationItemModel


class LocationViewHolder(
    itemView: View,
    private val context: Context
) : RecyclerView.ViewHolder(itemView) {

    private val binding = LocationListItemBinding.bind(itemView)

    private val glideRequest by lazy { Glide.with(context) }

    fun bind(item: LocationItemModel) {
        binding.textLocationName.text = item.name
        binding.textInformation.text =
            context.resources.getQuantityString(
                R.plurals.text_number_connection,
                item.ipList.size,
                item.ipList.size
            )
        glideRequest.load(decode(item.image, DEFAULT))
            .error(R.color.red_primary)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageCountryFlag)
    }
}
