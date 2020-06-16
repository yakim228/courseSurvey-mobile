package com.ipnetinstitute.csc394.mobile.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAppBuilder(var userLogged: Boolean,var token: String) {

    fun buildService(): RestAPI{

        val httpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val ongoing = chain.request().newBuilder()
                ongoing.addHeader("Accept", "application/json")
                ongoing.addHeader("Content-Type","application/json")
                if (userLogged) {
                    ongoing.addHeader("Authorization", "Bearer $token")
                }
                return chain.proceed(ongoing.build())
            }
        }).build()
        val builder = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).client(httpClient)
        val retrofit = builder.build()
        val restAPI = retrofit.create(RestAPI::class.java)
        return restAPI
    }
}
//    companion object{
//    fun manageHeaderContents(): OkHttpClient {
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(object : Interceptor {
//            override fun intercept(chain: Interceptor.Chain): Response {
//                val original = chain.request()
//                val request = original.newBuilder().header("Authorization", token)
//                    .method(original.method, original.body).build()
//                return chain.proceed(request)
//            }
//        })
//        return httpClient.build()
//
////        }
//    }
