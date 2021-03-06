package com.rickyputrah.express.data.repository

import com.rickyputrah.express.data.model.LocationVpnResponse
import javax.inject.Inject

class LocationVpnRepository @Inject constructor(private val locationVpnApi: LocationVpnApi) :
    ILocationVpnRepository {

    override suspend fun getLocationVpnList(): ResultRepository<LocationVpnResponse> =
        handleRequest {
            locationVpnApi.getLocations()
        }
}