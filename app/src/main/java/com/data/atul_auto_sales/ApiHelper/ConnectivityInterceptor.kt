package com.stpl.antimatter.Network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(var context: Context):Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil().isOnline(context)) {
            throw NoConnectivityException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}