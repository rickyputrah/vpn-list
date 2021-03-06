package com.rickyputrah.express.data.repository

import com.rickyputrah.express.data.model.LocationVpnResponse
import com.rickyputrah.express.model.BestLocationItemModel
import com.rickyputrah.express.model.LocationItemModel

interface ILocationVpnRepository {
    suspend fun getLocationVpnList(): ResultRepository<LocationVpnResponse>
    suspend fun getBestLocation(list: List<LocationItemModel>): ResultRepository<BestLocationItemModel>
}