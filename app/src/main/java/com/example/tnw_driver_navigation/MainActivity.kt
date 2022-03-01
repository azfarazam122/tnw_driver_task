package com.example.tnw_driver_navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tnw_driver_navigation.Constants.driverId
import com.github.kittinunf.result.Result
import com.github.kittinunf.fuel.httpPost
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //       getActionBar().hide()
        getSupportActionBar()?.hide()

        val loginBtn = findViewById<Button>(R.id.loginButton)



        loginBtn.setOnClickListener {
            val userName = findViewById<TextView>(R.id.usernameTextView)
            val password = findViewById<TextView>(R.id.passwordTextView)

            val httpAsync =
                "https://www.emeraldsoft.uk/projects/hamza/tnw_retail_calculator_1_2_4/web_services/mobile/login.php"
                    .httpPost(
                        listOf(
                            "user" to userName.text.toString(),
                            "password" to password.text.toString()
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
                                val data = result.get()
                                val json_data = JSONObject(data)
                                if (json_data["status"] == "success") {
                                    val intent =
                                        Intent(this@MainActivity, DatePickerActivity::class.java)
                                    val idOfDriver = json_data["status"]
//                                    intent.putExtra("driverId", json_data["id"].toString())
                                    driverId = json_data["id"].toString()
                                    startActivity(intent)
                                }

                            }
                        }
                    }

            httpAsync.join()


//            if (userName.text == "admin" && password.text == "admin") {
//                // Go To Next Activity
//                val intent = Intent(this@MainActivity, DatePickerActivity::class.java)
////                intent.putExtra("key", value)
//                startActivity(intent)
//            } else {
//            }
        }
    }
}