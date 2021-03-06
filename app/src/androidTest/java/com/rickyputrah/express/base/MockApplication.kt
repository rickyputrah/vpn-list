package com.rickyputrah.express.base

import android.content.Context
import com.rickyputrah.express.BaseApplication
import com.rickyputrah.express.di.DIApplicationManager

class MockApplication : BaseApplication() {

    override fun getBaseUrl(): String {
        return "http://127.0.0.1:8080"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DIApplicationManager.setupApplicationComponent(this)
    }
}