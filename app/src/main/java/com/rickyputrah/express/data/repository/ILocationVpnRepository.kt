package com.rickyputrah.express.data.repository

import com.rickyputrah.express.data.model.LocationVpnResponse

interface ILocationVpnRepository {
    suspend fun getLocationVpnList(): ResultRepository<LocationVpnResponse>
}