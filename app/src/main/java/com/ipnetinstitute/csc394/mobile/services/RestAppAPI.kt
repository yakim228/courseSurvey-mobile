package com.ipnetinstitute.csc394.mobile.services

import com.ipnetinstitute.csc394.mobile.data.model.Question
import com.ipnetinstitute.csc394.mobile.data.model.Survey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestAppAPI {

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