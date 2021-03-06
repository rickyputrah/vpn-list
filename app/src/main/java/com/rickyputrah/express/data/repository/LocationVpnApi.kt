package com.rickyputrah.express.data.repository

import com.rickyputrah.express.data.model.LocationVpnResponse
import retrofit2.http.GET

interface LocationVpnApi {
    @GET("locations")
    suspend fun getLocations(): LocationVpnResponse
}
