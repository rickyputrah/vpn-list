package com.rickyputrah.express.di.module

import com.rickyputrah.express.data.repository.LocationVpnApi
import com.rickyputrah.express.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideLocationVpnApi(apiService: ApiService): LocationVpnApi {
        return apiService.create(LocationVpnApi::class.java)
    }
}