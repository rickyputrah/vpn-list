package com.rickyputrah.express

import android.app.Application
import android.content.Context
import com.rickyputrah.express.di.DIApplicationManager

class ExpressApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DIApplicationManager.setupApplicationComponent()
    }


}