package com.rickyputrah.express.di

import com.rickyputrah.express.BaseApplication
import com.rickyputrah.express.di.module.ApplicationModule

fun getApplicationComponent() = DIApplicationManager.applicationComponent

object DIApplicationManager {
    lateinit var applicationComponent: ApplicationComponent

    fun setupApplicationComponent(application: BaseApplication) {
        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(application)
        ).build()
    }
}
