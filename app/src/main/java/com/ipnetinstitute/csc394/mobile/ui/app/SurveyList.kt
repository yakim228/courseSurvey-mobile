package com.ipnetinstitute.csc394.mobile.ui.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.Survey
import com.ipnetinstitute.csc394.mobile.services.Constants.Companion.BASE_URL
import com.ipnetinstitute.csc394.mobile.services.RestAppAPI
import kotlinx.android.synthetic.main.activity_survey_list.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SurveyList : AppCompatActivity() {
//    @BindView(R.id.surveyList) lateinit  var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_list)

        val userToken = intent.extras!!.get("user_token").toString()
        val userId = intent.extras!!.get("user_id") as Int

        Toast.makeText(this, userToken, Toast.LENGTH_LONG).show()
        Toast.makeText(this, userId.toString(), Toast.LENGTH_LONG).show()
        var surveyList = ArrayList<Survey>()

        //Header managements for all the requests apart from the login
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val request = original.newBuilder().header("Authorization", userToken)
                    .method(original.method, original.body).build()
                return chain.proceed(request)
            }
        })

        val client = httpClient.build()
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        val restAppAPI = retrofit.create(RestAppAPI::class.java)

        //Rest callings
        val callSurvey = restAppAPI.getSurvey(userId)

        callSurvey.enqueue(object : Callback<List<Survey>> {
            override fun onFailure(call: Call<List<Survey>>, t: Throwable) {
                Toast.makeText(this@SurveyList, "Erreur de connexion", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Survey>>,
                response: retrofit2.Response<List<Survey>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SurveyList, "Inside surveys", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@SurveyList, surveyList[0].title, Toast.LENGTH_LONG).show()
                    surveyList = response.body() as ArrayList<Survey>
                    val adapter = SurveyListAdapter(surveyList)
                    val layoutManager = LinearLayoutManager(this@SurveyList)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    surveyListView.layoutManager = layoutManager
                    surveyListView.adapter = adapter
                }
            }
        })

    }
}
