package com.rickyputrah.express

import android.app.Application
import com.rickyputrah.express.util.BASE_URL

open class BaseApplication : Application() {

    open fun getBaseUrl() = BASE_URL

}