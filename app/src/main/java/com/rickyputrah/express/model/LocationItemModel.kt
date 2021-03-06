package com.rickyputrah.express.model

import com.rickyputrah.express.data.model.LocationVpnResponse

data class LocationItemModel(
    val name: String,
    val ipList: List<String>,
    val image: String
)

data class LocationListModel(
    val buttonText: String,
    val listOfLocation: List<LocationItemModel>
)

fun LocationVpnResponse.toLocationListModel(): LocationListModel {
    return LocationListModel(
        this.buttonText,
        this.locations.locationList.map { location ->
            LocationItemModel(
                location.name,
                location.server.map { it.ipAddress },
                this.icons.icon[location.iconId] ?: ""
            )
        }
    )
}