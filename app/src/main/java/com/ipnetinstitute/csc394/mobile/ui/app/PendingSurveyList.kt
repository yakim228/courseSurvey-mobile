package com.ipnetinstitute.csc394.mobile.ui.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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

class PendingSurveyList : Fragment(), SurveyListAdapter.OnItemClickListener {

    private lateinit var surveyListView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var surveyId: Number

    private lateinit var userToken: String
    private lateinit var userId: Number

    private var surveyList : ArrayList<Survey> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pending_survey, container, false)

        surveyListView = view.findViewById(R.id.surveyListView)
        getSurvey()
//        surveyListView.layoutManager = LinearLayoutManager(activity)
//
//        val surveyOne = Survey("test","A Sample survey example", "Clear it", "Thank u", Date(2020,6,5), Date(2020,6,6), 5, 51, Date(2020,6,4), Date(2020, 6, 4), 51)
//        val surveyTwo = Survey("Another test","A Sample second survey example", "Clear it", "Thank u", Date(2020,6,5), Date(2020,6,6), 5, 51, Date(2020,6,4), Date(2020, 6, 4), 51)
//        val surveyThird = Survey("test","A Sample survey example", "Clear it", "Thank u", Date(2020,6,5), Date(2020,6,6), 5, 51, Date(2020,6,4), Date(2020, 6, 4), 51)
//        val surveyFourth = Survey("Another test","A Sample second survey example", "Clear it", "Thank u", Date(2020,6,5), Date(2020,6,6), 5, 51, Date(2020,6,4), Date(2020, 6, 4), 51)
//
//        surveyList.add(surveyOne)
//        surveyList.add(surveyTwo)
//        surveyList.add(surveyThird)
//        surveyList.add(surveyFourth)
//
//        val adapter = SurveyListAdapter(surveyList,this)
//        surveyListView.adapter = adapter

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity!!.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("surveyUserToken","")!!
        userId = sharedPreferences.getInt("surveyUserId",0)
    }

    override fun onItemClicked(survey: Survey) {
        val surveyId = survey.id
        Toast.makeText(activity, "Index du survey: $surveyId", Toast.LENGTH_LONG).show()
        val questionsDisplay = QuestionsDisplay()
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putInt(getString(R.string.pending) , surveyId )
        questionsDisplay.arguments = bundle
        fragmentTransaction.replace(R.id.fragment_container, questionsDisplay)
        fragmentTransaction.addToBackStack("questions")
        fragmentTransaction.commit()
    }

    fun getSurvey(){
        val restAppBuilder = RestAppBuilder(true, userToken)
        val restAppAPI = restAppBuilder.buildService()

        //Rest callings
        val callSurvey = restAppAPI.getSurvey(userId as Int)

        callSurvey.enqueue(object : Callback<List<Survey>> {
            override fun onFailure(call: Call<List<Survey>>, t: Throwable) {
//                Log.d("general f", "${t.message}")
//                Log.d("general failure", "$t.stackTrace")
//                Toast.makeText(activity,"$t.stackTrace",Toast.LENGTH_LONG).show()
//                Toast.makeText(activity, "Erreur de connexion", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Survey>>,
                response: retrofit2.Response<List<Survey>>
            ) {
                if (response.isSuccessful) {
//                    Toast.makeText(activity, "Inside surveys", Toast.LENGTH_LONG).show()
//                    Log.d("general success", "$response")

                    surveyList = response.body() as ArrayList<Survey>

//                    Toast.makeText(activity, "Liste de surveys $surveyList", Toast.LENGTH_LONG).show()
//                    Toast.makeText(activity, surveyList[0].title, Toast.LENGTH_LONG).show()
                    surveyListView.layoutManager = LinearLayoutManager(activity)
                    val adapter = SurveyListAdapter(surveyList,this@PendingSurveyList)
                    surveyListView.adapter = adapter
                }else{
//                    Toast.makeText(activity,"On response: errorcode : $response.code()",Toast.LENGTH_LONG).show()
                }
            }
        })
//        Toast.makeText(activity,"Taille de la liste :${surveyList.size}",Toast.LENGTH_LONG).show()
//        Log.d("Taille de la liste:","${surveyList.size}")
    }
}