package com.rickyputrah.express.di

fun getApplicationComponent() = DIApplicationManager.applicationComponent

object DIApplicationManager {
    lateinit var applicationComponent: ApplicationComponent

    fun setupApplicationComponent() {
        this.applicationComponent = DaggerApplicationComponent.create()
    }
}
