package com.data.atul_auto_sales.ApiHelper

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.data.atul_auto_sales.Utills.SalesApp
import com.data.atul_auto_sales.Utills.PrefManager
import com.stpl.antimatter.Network.ConnectivityInterceptor

import com.stpl.antimatter.Utils.ApiContants

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null
    public fun getClient(context: Context): Retrofit {

        if (okHttpClient == null)
            initOkHttp(context)

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(ApiContants.BaseUrl)
                    .client(okHttpClient!!)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    private fun initOkHttp(context: Context) {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(ChuckerInterceptor(context))
        httpClient.addInterceptor(ConnectivityInterceptor(context))
        httpClient.addInterceptor { chain ->
            val original = chain.request()


            if (SalesApp.isAddAccessToken) {
                var requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("token", PrefManager.getString(ApiContants.AccessToken,""))
                val request = requestBuilder.build()
                chain.proceed(request)
            } else {
                var requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")

                val request = requestBuilder.build()
                chain.proceed(request)
            }

        }

        okHttpClient = httpClient.build()
    }

/*    val chuckerCollector = ChuckerCollector(
        context = this,
        // Toggles visibility of the notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )*/

    // Create the Interceptor
    /*val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        // The previously created Collector
        .collector(chuckerCollector)
        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)
        // List of headers to replace with ** in the Chucker UI
        .redactHeaders("Auth-Token", "Bearer")
        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)
        // Use decoder when processing request and response bodies. When multiple decoders are installed they
        // are applied in an order they were added.
        .addBodyDecoder(decoder)
        // Controls Android shortcut creation.
        .createShortcut(true)
        .build()*/
}