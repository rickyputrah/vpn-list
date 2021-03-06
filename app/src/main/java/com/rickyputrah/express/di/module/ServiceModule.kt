package com.rickyputrah.express.di.module

import com.rickyputrah.express.network.ApiService
import com.rickyputrah.express.network.ApiServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {
    @Binds
    abstract fun bindApiService(apiService: ApiServiceImpl): ApiService
}