package com.example.socialnetwork.data.network

import android.util.Log
import com.example.socialnetwork.data.model.Session
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import javax.inject.Inject

class RequestInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var requestBuilder = request.newBuilder()
        if (Session.accessToken != null) {
            requestBuilder.header(
                "Authorization",
                "Bearer " + Session.accessToken
            )
        }

        var requestUrlBuilder = request.url().newBuilder()
        requestBuilder.url(requestUrlBuilder?.build())
        request = requestBuilder.build()
        var response = chain.proceed(request)

        val responseBodyCopy = response.peekBody(java.lang.Long.MAX_VALUE)
        Log.e("SERVICE", "response: " + responseBodyCopy.string())
        return response
    }

    private fun bodyToString(request: Request): String {

        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }

    }
}