package com.ipnetinstitute.csc394.mobile.services

import com.ipnetinstitute.csc394.mobile.data.model.Question
import com.ipnetinstitute.csc394.mobile.data.model.Survey
import com.ipnetinstitute.csc394.mobile.data.model.User_api_login
import com.ipnetinstitute.csc394.mobile.data.model.User_app_login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestAPI {

    //sign in
    @POST("api/auth/signin")
    fun login(@Body body: User_app_login): Call<User_api_login>

    //List of survey
    @GET("getPendingSurvey/{student_id}")
    fun getSurvey(@Path("student_id") student_id: Int): Call<List<Survey>>


    //List of questions
    @GET("getPendingSurveyQuestions/{question_id}/{student_id}")
    fun getQuestions(
        @Path("question_id") question_id: Long,
        @Path("student_id") student_id: Long
    ): Call<List<Question>>

}