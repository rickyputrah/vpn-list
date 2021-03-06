package com.rickyputrah.express.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val appContext: Context) {

    @Provides
    internal fun provideAppContext(): Context = appContext
}