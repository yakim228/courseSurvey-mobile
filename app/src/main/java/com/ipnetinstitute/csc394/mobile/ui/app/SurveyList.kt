package com.ipnetinstitute.csc394.mobile.ui.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.Survey
import com.ipnetinstitute.csc394.mobile.services.Constants.Companion.BASE_URL
import com.ipnetinstitute.csc394.mobile.services.RestAppAPI
import com.ipnetinstitute.csc394.mobile.services.RestAppBuilder
import kotlinx.android.synthetic.main.activity_survey_list.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class SurveyList : AppCompatActivity() {
//    @BindView(R.id.surveyList) lateinit  var recyclerView: RecyclerView
    var userId= 0
    lateinit var userToken: String
    var surveyList: ArrayList<Survey> = ArrayList<Survey>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_list)

        userToken = intent.extras!!.get("user_token").toString()
        userId = intent.extras!!.get("user_id") as Int

//        Toast.makeText(this, userToken, Toast.LENGTH_LONG).show()
//        Toast.makeText(this, "$userId", Toast.LENGTH_LONG).show()

//        val survey1 = Survey("Test de survey","Un survey pour tester le recyclerview","R","M",
//            Date(2020,5,16), Date(2020,5,17),false,0, Date(2020,5,16), Date(2020,5,16),51
//        )
//        val survey2 = Survey("Autre survey","Un autre survey pour tester le recyclerview","R","M",
//            Date(2020,5,16), Date(2020,5,17),false,0, Date(2020,5,16),Date(2020,5,16),51
//        )
//
//        surveyList.add(survey1)
//        surveyList.add(survey2)
//        surveyList.add(survey1)
//        surveyList.add(survey2)
//        surveyList.add(survey1)
//        surveyList.add(survey2)

//        surveyListView.layoutManager = LinearLayoutManager(this)
//        val adapter = SurveyListAdapter(surveyList)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        surveyListView.adapter = adapter

        getSurvey()
    }


    fun getSurvey(){
        val restAppBuilder = RestAppBuilder(true, userToken)
        val restAppAPI = restAppBuilder.BuildService()

        //Rest callings
        val callSurvey = restAppAPI.getSurvey(userId)
        Toast.makeText(this,"Inside the rest calling method",Toast.LENGTH_LONG).show()

        callSurvey.enqueue(object : Callback<List<Survey>> {
            override fun onFailure(call: Call<List<Survey>>, t: Throwable) {
                Log.d("general f", "${t.message}")
                Log.d("general failure", "$t.stackTrace")
                Toast.makeText(this@SurveyList,"$t.stackTrace",Toast.LENGTH_LONG).show()
                Toast.makeText(this@SurveyList, "Erreur de connexion", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Survey>>,
                response: retrofit2.Response<List<Survey>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SurveyList, "Inside surveys", Toast.LENGTH_LONG).show()
                    Log.d("general success", "$response")

                    surveyList = response.body() as ArrayList<Survey>
                    Toast.makeText(this@SurveyList, surveyList[0].title, Toast.LENGTH_LONG).show()
                    surveyListView.layoutManager = LinearLayoutManager(this@SurveyList)
                    val adapter = SurveyListAdapter(surveyList)
                    surveyListView.adapter = adapter
                }else{
                    Toast.makeText(this@SurveyList, "On response: errorcode : $response.code()",Toast.LENGTH_LONG).show()
                }
            }
        })
        Toast.makeText(this,"Taille de la liste :${surveyList.size}",Toast.LENGTH_LONG).show()
        Log.d("Taille de la liste:","${surveyList.size}")
    }
}
