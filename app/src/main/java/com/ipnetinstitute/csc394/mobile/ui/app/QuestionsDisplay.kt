package com.ipnetinstitute.csc394.mobile.ui.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.Question
import com.ipnetinstitute.csc394.mobile.data.model.StudentSurvey
import com.ipnetinstitute.csc394.mobile.services.Constants
import com.ipnetinstitute.csc394.mobile.services.RestAppBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class QuestionsDisplay : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userToken: String
    private lateinit var userId: Number
    private var questionsList: ArrayList<Question> = ArrayList()
    private lateinit var question: Question
    private lateinit var rating: Number
    private lateinit var studentSurvey: StudentSurvey

    private lateinit var tBtn: Button
    private lateinit var sBtn: Button
    private lateinit var aBtn: Button
    private lateinit var pBtn: Button
    private lateinit var mBtn: Button

    private lateinit var questionContent: TextView
    private lateinit var surveyId: Number
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.questions_fragment, container, false)
        questionContent = view.findViewById(R.id.question_content)
        tBtn = view.findViewById(R.id.t_btn)
        sBtn = view.findViewById(R.id.s_btn)
        aBtn = view.findViewById(R.id.a_btn)
        pBtn = view.findViewById(R.id.p_btn)
        mBtn = view.findViewById(R.id.m_btn)

        tBtn.setOnClickListener {
            rateQuestion(tBtn)
        }
        sBtn.setOnClickListener {
            rateQuestion(sBtn)
        }
        aBtn.setOnClickListener {
            rateQuestion(aBtn)
        }
        pBtn.setOnClickListener {
            rateQuestion(pBtn)
        }
        mBtn.setOnClickListener {
            rateQuestion(mBtn)
        }

        getQuestion()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity!!.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("surveyUserToken","")!!
        userId = sharedPreferences.getInt("surveyUserId",0)

        val bundle = this.arguments
        if(bundle!= null){
            surveyId = bundle.getInt(getString(R.string.pending))
        }
    }

    private fun getQuestion(){
        val restAppBuilder = RestAppBuilder(true,userToken)
        val restAppAPI = restAppBuilder.BuildService()

        val callQuestions = restAppAPI.getQuestions(surveyId.toInt(), userId.toInt())
        callQuestions.enqueue(object : Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Toast.makeText(activity, "Not reached", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                questionsList = response.body() as ArrayList<Question>
//                Toast.makeText(activity, "Liste des questions $questionsList",Toast.LENGTH_LONG).show()
                question = questionsList[0]
                questionContent.text = question.title
            }
        })
    }

    private fun rateQuestion(view: View){
        when(view.id){
            R.id.t_btn ->{
                Toast.makeText(activity, "T btn clicked", Toast.LENGTH_LONG).show()
                val rating = 5
                val studentSurvey = StudentSurvey("student_survey", surveyId.toInt(), userId.toInt(), question.id,"", 0, rating, 0,Date(2020,6,6), Date(2020,6,6),52)
                rate(studentSurvey)
            }
            R.id.s_btn ->{
                Toast.makeText(activity, "S btn clicked", Toast.LENGTH_LONG).show()
                val rating = 4
                val studentSurvey = StudentSurvey("student_survey", surveyId.toInt(), userId.toInt(), question.id,"", 0, rating, 0,Date(2020,6,6), Date(2020,6,6),52)
                rate(studentSurvey)
            }
            R.id.a_btn ->{
                Toast.makeText(activity, "A btn clicked", Toast.LENGTH_LONG).show()
                val rating = 3
                val studentSurvey = StudentSurvey("student_survey", surveyId.toInt(), userId.toInt(), question.id,"", 0, rating, 0,Date(2020,6,6), Date(2020,6,6),52)
                rate(studentSurvey)
            }
            R.id.p_btn ->{
                Toast.makeText(activity, "P btn clicked", Toast.LENGTH_LONG).show()
                val rating = 2
                val studentSurvey = StudentSurvey("student_survey", surveyId.toInt(), userId.toInt(), question.id,"", 0, rating, 0,Date(2020,6,6), Date(2020,6,6),52)
                rate(studentSurvey)
            }
            R.id.m_btn ->{
                Toast.makeText(activity, "M btn clicked", Toast.LENGTH_LONG).show()
                val rating = 1
                val studentSurvey = StudentSurvey("student_survey",surveyId.toInt(), userId.toInt(), question.id,"", 0, rating, 0,Date(2020,6,6), Date(2020,6,6),52)
                rate(studentSurvey)

            }

        }
    }

    fun returnToSurveyList(){
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        val pendingSurveyList = PendingSurveyList()
        fragmentTransaction.replace(R.id.fragment_container, pendingSurveyList)
//        fragmentTransaction.addToBackStack("questions")
        fragmentTransaction.commit()
    }

    private fun rate (studentSurvey: StudentSurvey){
        val restAppBuilder = RestAppBuilder(true,userToken)
        val restAppAPI = restAppBuilder.BuildService()
        val callRate = restAppAPI.rateSurvey(studentSurvey)

        callRate.enqueue(object : Callback<Response<Any>> {
            override fun onFailure(call: Call<Response<Any>>, t: Throwable) {
                Toast.makeText(activity, "not saved", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Response<Any>>, response: Response<Response<Any>>) {
                Toast.makeText(activity, "Saved", Toast.LENGTH_LONG).show()
                returnToSurveyList()
            }

        })
    }

//    fun getSurvey(){
//        val restAppBuilder = RestAppBuilder(true, userToken)
//        val restAppAPI = restAppBuilder.BuildService()
//
//        //Rest callings
//        val callSurvey = restAppAPI.getSurvey(userId as Int)
//        Toast.makeText(activity,"Inside the rest calling method", Toast.LENGTH_LONG).show()
//
//        callSurvey.enqueue(object : Callback<List<Survey>> {
//            override fun onFailure(call: Call<List<Survey>>, t: Throwable) {
//                Log.d("general f", "${t.message}")
//                Log.d("general failure", "$t.stackTrace")
//                Toast.makeText(activity,"$t.stackTrace", Toast.LENGTH_LONG).show()
//                Toast.makeText(activity, "Erreur de connexion", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(
//                call: Call<List<Survey>>,
//                response: retrofit2.Response<List<Survey>>
//            ) {
//                if (response.isSuccessful) {
//                    Toast.makeText(activity, "Inside surveys", Toast.LENGTH_LONG).show()
//                    Log.d("general success", "$response")
//
////                    surveyList = response.body() as ArrayList<Survey>
////                    Toast.makeText(activity, surveyList[0].title, Toast.LENGTH_LONG).show()
////                    recyclerView.layoutManager = LinearLayoutManager(activity)
////                    val adapter = SurveyListAdapter(surveyList,this@SurveyListFragment)
////                    recyclerView.adapter = adapter
//                }else{
//                    Toast.makeText(activity,"On response: errorcode : $response.code()", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//        Toast.makeText(activity,"Taille de la liste :${surveyList.size}", Toast.LENGTH_LONG).show()
//        Log.d("Taille de la liste:","${surveyList.size}")
//    }
}