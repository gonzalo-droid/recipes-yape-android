package com.example.recipes_yape_android.core.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupportInterceptor @Inject constructor() : Interceptor {
    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Content-Type", SettingRemote.CONTENT_TYPE_JSON)
            .addHeader("Accept", SettingRemote.ACCEPT_JSON)
            .build()
        return chain.proceed(request)
    }
}
