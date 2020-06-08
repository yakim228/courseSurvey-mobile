package com.ipnetinstitute.csc394.mobile.services

import com.ipnetinstitute.csc394.mobile.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestAPI {

    //sign in
    @POST("api/auth/signin")
    fun login(@Body body: UserAppLogin): Call<User_api_login>

    //List of survey
    @GET("getPendingSurvey/{student_id}")
    fun getSurvey(@Path("student_id") student_id: Int): Call<List<Survey>>


    //List of questions
    @GET("getPendingSurveyQuestions/{survey_id}/{student_id}")
    fun getQuestions(
        @Path("survey_id") survey_id: Int,
        @Path("student_id") student_id: Int
    ): Call<List<Question>>

    @POST("save/StudentSurvey")
    fun rateSurvey(@Body body: StudentSurvey): Call<Response<Any>>
    

}