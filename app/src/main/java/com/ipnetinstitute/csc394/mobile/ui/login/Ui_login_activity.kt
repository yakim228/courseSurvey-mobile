package com.ipnetinstitute.csc394.mobile.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ipnetinstitute.csc394.mobile.MainActivity
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.User_api_login
import com.ipnetinstitute.csc394.mobile.data.model.User_app_login
import com.ipnetinstitute.csc394.mobile.services.Constants
import com.ipnetinstitute.csc394.mobile.services.RestAppBuilder
import com.ipnetinstitute.csc394.mobile.ui.app.SurveyList
import kotlinx.android.synthetic.main.activity_ui_login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ui_login_activity : AppCompatActivity() {

    lateinit var userAppLogin: User_app_login
    lateinit var userApiLogin: User_api_login
    lateinit var editor: SharedPreferences.Editor

//    lateinit var username: String
//    lateinit var password: String
//    @BindView(R.id.username) lateinit var username: EditText
//    @BindView(R.id.password) lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_login_activity)
        editor = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE).edit()


        login_btn.setOnClickListener {
            val usernameValue = username.editText!!.text.toString()
            val passwordValue = password.editText!!.text.toString()
//            Toast.makeText(this@Ui_login_activity, usernameValue, Toast.LENGTH_LONG).show()
//            Toast.makeText(this@Ui_login_activity, passwordValue, Toast.LENGTH_LONG).show()
            userAppLogin = User_app_login(usernameValue, passwordValue, false)
            login(userAppLogin)
        }
    }

    fun login(userAppLogin: User_app_login) {

//        val BASEURL = "http://ec2-18-224-141-43.us-east-2.compute.amazonaws.com/services/"

        val restAppBuilder = RestAppBuilder(false,"")
        val restAPI = restAppBuilder.BuildService()
        val call = restAPI.login(userAppLogin)

//        var userApiLogin: User_api_login?

        call.enqueue(object : Callback<User_api_login> {
            override fun onFailure(call: Call<User_api_login>, t: Throwable) {
                Log.d("login_error", "${t.message}")
                Log.d("throwable","${t.stackTrace}")
                Toast.makeText(this@Ui_login_activity, "Api Non atteint", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<User_api_login>,
                response: Response<User_api_login>
            ) {
                if (response.isSuccessful) {
                    userApiLogin = response.body() as User_api_login

                    editor.putString("surveyUser",userApiLogin.username)
                    editor.putString("surveyUserPassword",userAppLogin.password)
                    editor.putString("surveyUserToken",userApiLogin.accessToken)
                    editor.putInt("surveyUserId",userApiLogin.id)


                    editor.apply()

                    Toast.makeText(this@Ui_login_activity, "Api Atteint", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Ui_login_activity, MainActivity::class.java)
//                    intent.putExtra("user_token", userApiLogin!!.accessToken)
//                    intent.putExtra("user_id", userApiLogin!!.id)
                    startActivity(intent)
                }
            }
        })
    }
}
