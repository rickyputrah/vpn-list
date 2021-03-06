package com.rickyputrah.express.di

import com.rickyputrah.express.di.module.*
import com.rickyputrah.express.ui.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ApiModule::class,
        ServiceModule::class]
)
interface ApplicationComponent {

    fun inject(activity: HomeActivity)

}