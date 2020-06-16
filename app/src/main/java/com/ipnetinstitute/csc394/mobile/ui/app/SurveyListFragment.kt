package com.ipnetinstitute.csc394.mobile.ui.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.Survey
import com.ipnetinstitute.csc394.mobile.services.Constants
import com.ipnetinstitute.csc394.mobile.services.RestAppBuilder
import retrofit2.Call
import retrofit2.Callback
import kotlin.collections.ArrayList

class SurveyListFragment: Fragment() , SurveyListAdapter.OnItemClickListener{

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var surveyList: ArrayList<Survey>
    private lateinit var userToken: String
    private lateinit var userId: Number
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_survey_list, container, false)
//        Toast.makeText(activity, "Token: $userToken")
//        val surveyOne = Survey("test","Un exemple de survey","Remplissez le","Merci pour votre patience", Date(2020,5,23), Date(2020,5,23),3,5, Date(2020,5,23), Date(2020,5,23),52)
//        val surveyTwo = Survey("Second test","Un autre exemple de survey","Remplissez le","Merci pour votre patience", Date(2020,5,23), Date(2020,5,23),3,5, Date(2020,5,23), Date(2020,5,23),52)
//        val surveyThree = Survey("Troisième test","Un dernier exemple de survey","Remplissez le","Merci pour votre patience", Date(2020,5,23), Date(2020,5,23),3,5, Date(2020,5,23), Date(2020,5,23),52)
//
//        val surveyFour = Survey("test","Un exemple de survey","Remplissez le","Merci pour votre patience", Date(2020,5,23), Date(2020,5,23),3,5, Date(2020,5,23), Date(2020,5,23),52)
//        val surveyFive = Survey("Second test","Un autre exemple de survey","Remplissez le","Merci pour votre patience", Date(2020,5,23), Date(2020,5,23),3,5, Date(2020,5,23), Date(2020,5,23),52)
//        val surveySix = Survey("Troisième test","Un dernier exemple de survey","Remplissez le","Merci pour votre patience", Date(2020,5,23), Date(2020,5,23),3,5, Date(2020,5,23), Date(2020,5,23),52)

        surveyList = ArrayList()
//        surveyList.add(surveyOne)
//        surveyList.add(surveyTwo)
//        surveyList.add(surveyThree)
//        surveyList.add(surveyFour)
//        surveyList.add(surveyFive)
//        surveyList.add(surveySix)

        recyclerView = rootView.findViewById<RecyclerView>(R.id.surveyFragmentListView)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        val adapter = SurveyListAdapter(surveyList,this)
//        recyclerView.adapter = adapter
        getSurvey()

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity!!.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("surveyUserToken","")!!
        userId = sharedPreferences.getInt("surveyUserId",0)
    }

    companion object{
        fun newInstance(): SurveyListFragment = SurveyListFragment()
    }

    fun getSurvey(){
        val restAppBuilder = RestAppBuilder(true, userToken)
        val restAppAPI = restAppBuilder.buildService()

        //Rest callings
        val callSurvey = restAppAPI.getSurvey(userId as Int)
        Toast.makeText(activity,"Inside the rest calling method",Toast.LENGTH_LONG).show()

        callSurvey.enqueue(object : Callback<List<Survey>> {
            override fun onFailure(call: Call<List<Survey>>, t: Throwable) {
                Log.d("general f", "${t.message}")
                Log.d("general failure", "$t.stackTrace")
                Toast.makeText(activity,"$t.stackTrace",Toast.LENGTH_LONG).show()
                Toast.makeText(activity, "Erreur de connexion", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Survey>>,
                response: retrofit2.Response<List<Survey>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "Inside surveys", Toast.LENGTH_LONG).show()
                    Log.d("general success", "$response")

                    surveyList = response.body() as ArrayList<Survey>
                    Toast.makeText(activity, surveyList[0].title, Toast.LENGTH_LONG).show()
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    val adapter = SurveyListAdapter(surveyList,this@SurveyListFragment)
                    recyclerView.adapter = adapter
                }else{
                    Toast.makeText(activity,"On response: errorcode : $response.code()",Toast.LENGTH_LONG).show()
                }
            }
        })
        Toast.makeText(activity,"Taille de la liste :${surveyList.size}",Toast.LENGTH_LONG).show()
        Log.d("Taille de la liste:","${surveyList.size}")
    }

    override fun onItemClicked(survey: Survey) {
        Toast.makeText(activity,"Clicked",Toast.LENGTH_LONG).show()
        Log.d("Click on fragment", "click")
    }


}