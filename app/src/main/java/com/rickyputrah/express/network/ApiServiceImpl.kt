package com.rickyputrah.express.network

import com.rickyputrah.express.util.BASE_URL
import com.rickyputrah.express.util.CONNECT_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class ApiServiceImpl @Inject constructor() : ApiService {

    override fun <T> create(type: Class<T>): T {
        val httpClientBuilder = OkHttpClient.Builder()
            .apply {
                applyLoggingInterceptor(this)
                applyDebugCertificate(this)
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            }

        val httpClient = httpClientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        return retrofit.create(type)
    }

    private fun applyLoggingInterceptor(builder: OkHttpClient.Builder) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        builder.addInterceptor(loggingInterceptor)
    }

    private fun applyDebugCertificate(builder: OkHttpClient.Builder) {
        try {
            // Create a trust manager that does not validate certificate chains
            val sslManager = object : X509TrustManager {

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }
            }

            val trustAllCerts = arrayOf<TrustManager>(sslManager)

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            builder.sslSocketFactory(sslSocketFactory, sslManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
