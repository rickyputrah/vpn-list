package com.rickyputrah.express.di.module

import com.rickyputrah.express.data.repository.ILocationVpnRepository
import com.rickyputrah.express.data.repository.LocationVpnRepository
import dagger.Binds
import dagger.Module


@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindLocationRepository(apiService: LocationVpnRepository): ILocationVpnRepository
}