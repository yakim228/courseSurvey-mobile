package com.ipnetinstitute.csc394.mobile.data.datasource

import com.ipnetinstitute.csc394.mobile.data.model.User
import okhttp3.ResponseBody
import retrofit2.Call;
import retrofit2.http.*


interface GetDataService {
    @Headers("Content-Type:application/json")
    @GET("/users")
    fun getUsers() : Call<List<User>>

    @Headers("Content-Type:application/json")
    @GET("/users/{id}")
    fun getOneUser(@Path("id") user_id: Int) : Call<User>

    @Headers("Content-Type:application/json")
    @POST("auth_tokens")
    fun signin(@Body info: User): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("users")
    fun registerUser(
        @Body info: User
    ): retrofit2.Call<ResponseBody>
}