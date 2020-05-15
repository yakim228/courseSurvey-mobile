package com.ipnetinstitute.csc394.mobile.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.User_api_login
import com.ipnetinstitute.csc394.mobile.data.model.User_app_login
import com.ipnetinstitute.csc394.mobile.services.Constants.Companion.BASE_URL
import com.ipnetinstitute.csc394.mobile.services.RestAPI
import com.ipnetinstitute.csc394.mobile.ui.app.SurveyList
import kotlinx.android.synthetic.main.activity_ui_login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Ui_login_activity : AppCompatActivity() {

    lateinit var user_app_login: User_app_login

//    lateinit var username: String
//    lateinit var password: String
//    @BindView(R.id.username) lateinit var username: EditText
//    @BindView(R.id.password) lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_login_activity)


        login_btn.setOnClickListener {
            val usernameValue = username.editText!!.text.toString()
            val passwordValue = password.editText!!.text.toString()
//            Toast.makeText(this@Ui_login_activity, usernameValue, Toast.LENGTH_LONG).show()
//            Toast.makeText(this@Ui_login_activity, passwordValue, Toast.LENGTH_LONG).show()
            user_app_login = User_app_login(usernameValue, passwordValue)
            login(user_app_login)
        }
    }

    fun login(userAppLogin: User_app_login) {

//        val BASEURL = "http://ec2-18-224-141-43.us-east-2.compute.amazonaws.com/services/"

        val builder =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        val restAPI = retrofit.create(RestAPI::class.java)

        val call = restAPI.login(userAppLogin)

        var userApiLogin: User_api_login?

        call.enqueue(object : Callback<User_api_login> {
            override fun onFailure(call: Call<User_api_login>, t: Throwable) {
                Toast.makeText(this@Ui_login_activity, "Api Non atteint", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<User_api_login>,
                response: Response<User_api_login>
            ) {
                if (response.isSuccessful) {
                    userApiLogin = response.body()
                    Toast.makeText(this@Ui_login_activity, "Api Atteint", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Ui_login_activity, SurveyList::class.java)
                    intent.putExtra("user_token", userApiLogin!!.accessToken)
                    intent.putExtra("user_id", userApiLogin!!.id)
                    startActivity(intent)
                }
            }
        })
    }
}
