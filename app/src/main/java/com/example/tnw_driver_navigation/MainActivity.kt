package com.example.tnw_driver_navigation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.SharedPreferences
import android.widget.TextView
import android.widget.Toast
import com.example.tnw_driver_navigation.Constants.driverId
import com.example.tnw_driver_navigation.Constants.logoutButtonIsClicked
import com.example.tnw_driver_navigation.Constants.nameForCheckingAdmin
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.httpPost
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //       getActionBar().hide()
        getSupportActionBar()?.hide()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        if (logoutButtonIsClicked == true) {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }

        val loginBtn = findViewById<Button>(R.id.loginButton)


        val userName_AL = sharedPreferences.getString("user_name_AutoLogin", "defaultname")
        val userPass_AL = sharedPreferences.getString("user_pass_AutoLogin", "defaultname")
        if (userName_AL != "defaultname" && userPass_AL != "defaultname") {
            loginFunction(userName_AL.toString(), userPass_AL.toString(), sharedPreferences)
        }



        loginBtn.setOnClickListener {

            loginFunction(userName_AL.toString(), userPass_AL.toString(), sharedPreferences)

        }


    }

    fun loginFunction(
        userName_AL: String,
        userPass_AL: String,
        sharedPreferences: SharedPreferences
    ) {
        var userName = ""
        var password = ""
        if (userName_AL != "defaultname" && userPass_AL != "defaultname") {
            userName = userName_AL.toString()
            password = userPass_AL.toString()
        } else {
            userName = findViewById<TextView>(R.id.usernameTextView).text.toString()
            password = findViewById<TextView>(R.id.passwordTextView).text.toString()
        }

        if (userName.lowercase(Locale.getDefault()) == "jake" && password == ".45auto") {
            nameForCheckingAdmin = userName
            val intent =
                Intent(this@MainActivity, DatePickerActivity::class.java)
            startActivity(intent)
        } else if (userName.lowercase(Locale.getDefault()) == "wes" && password == ".45auto") {
            nameForCheckingAdmin = userName
            val intent =
                Intent(this@MainActivity, DatePickerActivity::class.java)
            startActivity(intent)
        } else {
            val httpAsync = Constants.login
                .httpPost(
                    listOf(
                        "user" to userName,
                        "password" to password
                    )
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()

                            Toast.makeText(
                                applicationContext,
                                "Login Failed",
                                Toast.LENGTH_LONG
                            )
                                .show()

                        }
                        is Result.Success -> {
//                            user_name_AutoLogin = userName.text.toString()
//                            user_pass_AutoLogin = password.text.toString()

                            nameForCheckingAdmin = userName
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("user_name_AutoLogin", userName)
                            editor.putString("user_pass_AutoLogin", password)
                            editor.apply()
                            editor.commit()

                            val data = result.get()
                            val json_data = JSONObject(data)
                            if (json_data["status"] == "success") {
                                val intent =
                                    Intent(this@MainActivity, DatePickerActivity::class.java)

                                driverId = json_data["id"].toString()
                                startActivity(intent)
                            }

                        }
                    }
                }

            httpAsync.join()

        }


    }


}


