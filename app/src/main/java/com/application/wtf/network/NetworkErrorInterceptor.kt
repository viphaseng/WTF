package com.application.wtf.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException

class NetworkConnectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val response = chain.proceed(requestBuilder.build())
        if (!response.isSuccessful) {
            val body: ResponseBody = response.peekBody(Long.MAX_VALUE)
            val content = body.string()
            val jsonObject = JSONObject(content)
            val error = jsonObject.getString("error")
            throw ErrorConnectivityException(error)
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    class ErrorConnectivityException(serverMessage: String?) : IOException(serverMessage)
}