package com.rickyputrah.express

import android.content.Context
import com.rickyputrah.express.di.DIApplicationManager

class ExpressApplication : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        DIApplicationManager.setupApplicationComponent(this)
    }


}