package com.ipnetinstitute.csc394.mobile.controller

import com.ipnetinstitute.csc394.mobile.data.model.User_api_login
import com.ipnetinstitute.csc394.mobile.data.model.User_app_login
import com.ipnetinstitute.csc394.mobile.services.RestAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiController {

    val BASE_URL = "http://ec2-18-224-141-43.us-east-2.compute.amazonaws.com/services/"
    lateinit var restAPI: RestAPI
    var status = "test"
    var user_api_login: User_api_login? = null

    fun start() {
//        var gson = GsonBuilder().setLenient().create()
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        restAPI = retrofit.create(RestAPI::class.java)

//        var user = User("Aleta","Givens","mvincentbb@nowhere.com","Manuel634","6KCA6E93G4C213XC0U"," 149-1442")
        val userApp = User_app_login("mvincentbb", "vincent1234")
        val call = restAPI.login(userApp)
        status = "inside start"

        call.enqueue(object : Callback<User_api_login> {
            override fun onFailure(call: Call<User_api_login>, t: Throwable) {
                status = "Non atteint"
            }

            override fun onResponse(
                call: Call<User_api_login>,
                response: Response<User_api_login>
            ) {
                if (response.isSuccessful) {
                    user_api_login = response.body() as User_api_login
                    status = "Atteint avec réussite"
                } else {
                    status = "Atteint avec échec"
                }
            }

        })
    }

}