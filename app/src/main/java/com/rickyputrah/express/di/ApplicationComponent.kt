package com.rickyputrah.express.di

import com.rickyputrah.express.di.module.ApiModule
import com.rickyputrah.express.di.module.RepositoryModule
import com.rickyputrah.express.di.module.ServiceModule
import com.rickyputrah.express.di.module.ViewModelModule
import com.rickyputrah.express.ui.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        RepositoryModule::class,
        ApiModule::class,
        ServiceModule::class]
)
interface ApplicationComponent {

    fun inject(activity: HomeActivity)

}