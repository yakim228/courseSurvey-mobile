package com.ipnetinstitute.csc394.mobile.data

import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    lateinit var retrofit: Retrofit
    val BASE_URL = "localhost"
    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null){
            retrofit = Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}