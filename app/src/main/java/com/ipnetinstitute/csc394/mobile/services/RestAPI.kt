package com.ipnetinstitute.csc394.mobile.services

import com.ipnetinstitute.csc394.mobile.data.model.User_api_login
import com.ipnetinstitute.csc394.mobile.data.model.User_app_login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RestAPI {

    //sign in
    @POST("api/auth/signin")
    fun login(@Body body: User_app_login): Call<User_api_login>
}