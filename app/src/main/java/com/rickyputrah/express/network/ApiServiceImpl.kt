package com.rickyputrah.express.network

import android.content.Context
import com.rickyputrah.express.BaseApplication
import com.rickyputrah.express.util.CONNECT_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ApiServiceImpl @Inject constructor(private val context: Context) : ApiService {

    override fun <T> create(type: Class<T>): T {
        val httpClientBuilder = OkHttpClient.Builder()
            .apply {
                applyLoggingInterceptor(this)
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            }

        val httpClient = httpClientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(httpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        return retrofit.create(type)
    }

    private fun getBaseUrl(): String {
        return (context.applicationContext as BaseApplication).getBaseUrl()
    }

    private fun applyLoggingInterceptor(builder: OkHttpClient.Builder) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(loggingInterceptor)
    }
}
