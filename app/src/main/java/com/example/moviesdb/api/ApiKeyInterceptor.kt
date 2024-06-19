package com.example.moviesdb.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        // Add the API key as a query parameter
        val urlWithApiKey = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        // Build the new request with the updated URL
        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}
