package com.ipnetinstitute.csc394.mobile.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ipnetinstitute.csc394.mobile.AdminActivity
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.TeacherActivity
import com.ipnetinstitute.csc394.mobile.data.model.User_api_login
import com.ipnetinstitute.csc394.mobile.data.model.UserAppLogin
import com.ipnetinstitute.csc394.mobile.services.Constants
import com.ipnetinstitute.csc394.mobile.services.RestAppBuilder
import com.ipnetinstitute.csc394.mobile.ui.app.AppActivity
import kotlinx.android.synthetic.main.activity_ui_login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UiLoginActivity : AppCompatActivity() {

    lateinit var userAppLogin: UserAppLogin
    lateinit var userApiLogin: User_api_login
    lateinit var editor: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
//    val sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_login_activity)
        sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        login_btn.setOnClickListener {
            val usernameValue = username.editText!!.text.toString()
            val passwordValue = password.editText!!.text.toString()
            //Api login request
            userAppLogin = UserAppLogin(usernameValue, passwordValue, false)
            login(userAppLogin)
        }
    }

    fun login(userAppLogin: UserAppLogin) {
//        val BASEURL = "http://ec2-18-224-141-43.us-east-2.compute.amazonaws.com/services/"
        val restAppBuilder = RestAppBuilder(false,"")
        val restAPI = restAppBuilder.BuildService()
        val call = restAPI.login(userAppLogin)
//        var userApiLogin: User_api_login?

        call.enqueue(object : Callback<User_api_login> {
            override fun onFailure(call: Call<User_api_login>, t: Throwable) {
                Log.d("login_error", "${t.message}")
                Log.d("throwable","${t.stackTrace}")
                Toast.makeText(this@UiLoginActivity, "Api Non atteint", Toast.LENGTH_LONG).show()
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
                    if (userApiLogin.roles.isNotEmpty()) {
                        editor.putString("userRole",userApiLogin.roles[0].name)
                    }

                    editor.apply()

                    Toast.makeText(this@UiLoginActivity, "Api Atteint", Toast.LENGTH_LONG).show()
                    val intent_teacher = Intent(this@UiLoginActivity, TeacherActivity::class.java)
                    val intent_admin = Intent(this@UiLoginActivity, AdminActivity::class.java)
                    val intent = Intent(this@UiLoginActivity, AppActivity::class.java)
//                    intent.putExtra("user_token", userApiLogin!!.accessToken)
//                    intent.putExtra("user_id", userApiLogin!!.id)

                    when (sharedPreferences.getString("userRole", "user")) {
                        "admin" -> startActivity(intent_admin)
                        "teacher" -> startActivity(intent_teacher)
                        else -> {
                            startActivity(intent)
                        }
                    }
                }
            }
        })
    }
}
