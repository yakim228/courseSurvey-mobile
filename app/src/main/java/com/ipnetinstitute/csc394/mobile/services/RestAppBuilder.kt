package com.ipnetinstitute.csc394.mobile.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class RestAppBuilder(var token: String) {
    //    companion object{
    fun manageHeaderContents(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val request = original.newBuilder().header("Authorization", token)
                    .method(original.method, original.body).build()
                return chain.proceed(request)
            }
        })
        return httpClient.build()

//        }
    }
}