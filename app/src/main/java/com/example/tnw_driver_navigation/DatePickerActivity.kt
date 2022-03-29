package com.example.tnw_driver_navigation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tnw_driver_navigation.Constants.driverIdSelectedByAdmin
import com.example.tnw_driver_navigation.Constants.driverSelectedByAdmin
import com.example.tnw_driver_navigation.Constants.logoutButtonIsClicked
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_date_picker.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class DatePickerActivity : AppCompatActivity() {

    private var button_date: Button? = null

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskArrayList: ArrayList<TaskApiConstants>

    private lateinit var taskLinearLayoutManager: LinearLayoutManager

    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)


        //       getActionBar().hide()
        getSupportActionBar()?.hide()
        taskArrayList = ArrayList<TaskApiConstants>()

        //______________________________________________________________________________________________
        // Spinner


        val categories: MutableList<String> = ArrayList()
        val driversId: MutableList<Int> = ArrayList()
        categories.add("Select Driver")
        driversId.add(123)

        val spinner: Spinner = findViewById(R.id.selectBoxForDrivers)
        // Create an ArrayAdapter using the string array and a default spinner layout


        if (Constants.nameForCheckingAdmin.lowercase(Locale.getDefault()) == "jake" || Constants.nameForCheckingAdmin.lowercase(
                Locale.getDefault()
            ) == "wes"
        ) {

            spinner.visibility = View.VISIBLE
        } else {
            spinner.visibility = View.GONE
//            updateDateInView()
        }
//        ______________________________________
//        Getting All The Driver's Name
        val httpAsync =
            Constants.getDrivers
                .httpPost(
                    listOf()
                )
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()

                            Toast.makeText(
                                applicationContext,
                                "Getting Drivers Name Failed",
                                Toast.LENGTH_LONG
                            )
                                .show()

                        }
                        is Result.Success -> {
                            val data = result.get()
                            val json_data = JSONObject(data)
                            val lengthOfTasks = (json_data["data"] as JSONArray).length()
//                            ((json_data["data"] as JSONArray)[0] as JSONObject)["task"]
                            taskArrayList.clear()
                            var arrayCurrentTask = (json_data["data"] as JSONArray)
                            for (i in 0..(lengthOfTasks - 1)) {
                                val newName =
                                    (arrayCurrentTask[i] as JSONObject).getString("name")
                                categories.add(newName)
                                val newId =
                                    (arrayCurrentTask[i] as JSONObject).getInt("id")
                                driversId.add(newId)
                            }


                            spinner.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View, position: Int, id: Long
                                ) {
                                    if (categories[position] != "Select Driver") {

                                        Toast.makeText(
                                            this@DatePickerActivity,
                                            categories[position], Toast.LENGTH_SHORT
                                        ).show()

                                        driverSelectedByAdmin = categories[position]
                                        driverIdSelectedByAdmin = driversId[position]
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {
                                    // write code to perform some action
                                }
                            }
                            val dataAdapter =
                                ArrayAdapter(
                                    this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    categories
                                )
//                            dataAdapter.setDropDownViewResource(R.layout.);
                            spinner.adapter = dataAdapter
                        }
                    }
                }

        httpAsync.join()
//        ______________________________________


        // Spinner
        //______________________________________________________________________________________________


        // get the references from layout file
        button_date = this.button_date_1
        val backIcon: ImageView = findViewById(R.id.back_Icon)
        val backText: TextView = findViewById(R.id.back_TextView)
        val logoutIcon: ImageView = findViewById(R.id.logout_Icon)
        val logoutText: TextView = findViewById(R.id.logout_TextView)

        backIcon.setOnClickListener {
            goingBackToLoginPage()
        }
        backText.setOnClickListener {
            goingBackToLoginPage()
        }
        logoutIcon.setOnClickListener {
            loggingOut()
        }
        logoutText.setOnClickListener {
            loggingOut()
        }




        taskRecyclerView = findViewById(R.id.recyclerViewForTasks)

        //    taskRecyclerView.layoutManager =
        //        LinearLayoutManager(this)
        taskLinearLayoutManager = LinearLayoutManager(this)
        taskRecyclerView.layoutManager = taskLinearLayoutManager

        taskRecyclerView.adapter = TaskAdapter(taskArrayList, this@DatePickerActivity)
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                taskArrayList.clear()
                updateDateInView()

//                val users = ArrayList<TaskApiConstants>()
//
//                //adding some dummy data to the list
//                users.add(TaskApiConstants("Belal Khan"))
//                users.add(TaskApiConstants("Ramiz Khan"))
//                users.add(TaskApiConstants("Faiz Khan"))
//

                // taskRecyclerView.setHasFixedSize(true)

//                            taskRecyclerView.setAdapter(TaskAdapter(taskArrayList));
                val textViewIfNoTaskFound = findViewById<TextView>(R.id.ifNoTaskFound)
                if (taskArrayList.size < 1) {
                    textViewIfNoTaskFound.text = "No Task Found"
                    taskRecyclerView.adapter =
                        TaskAdapter(taskArrayList, this@DatePickerActivity)
                } else {
                    textViewIfNoTaskFound.text = ""
                    taskRecyclerView.adapter =
                        TaskAdapter(taskArrayList, this@DatePickerActivity)
                }
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@DatePickerActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })
    }


    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val dateSelected = sdf.format(cal.getTime())
        button_date!!.text = sdf.format(cal.getTime())

//        ______________________________________
//        Getting All The Tasks

        if (driverSelectedByAdmin != "" && driverIdSelectedByAdmin != 0) {
            val httpAsync =
                Constants.taskList
                    .httpPost(
                        listOf(
                            "date" to dateSelected,
                            "id" to driverIdSelectedByAdmin
                        )
                    )
                    .responseString { request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()

                                Toast.makeText(
                                    applicationContext,
                                    "Getting Task Failed",
                                    Toast.LENGTH_LONG
                                )
                                    .show()

                            }
                            is Result.Success -> {
                                val data = result.get()


                                val json_data = JSONObject(data)
//                            val json_data_data = JSONObject(json_data["data"])
                                val lengthOfTasks = (json_data["data"] as JSONArray).length()
//                            ((json_data["data"] as JSONArray)[0] as JSONObject)["task"]
                                taskArrayList.clear()

                                // WHEN DRIVER IS NOT SELECTED
                                for (i in 0..(lengthOfTasks - 1)) {
                                    var arrayCurrentTask = (json_data["data"] as JSONArray)
                                    val task = TaskApiConstants()
                                    //  user.id = json_user.getInt(i)
                                    task.taskId = (arrayCurrentTask[i] as JSONObject).getInt("id")
                                    task.taskName =
                                        (arrayCurrentTask[i] as JSONObject).getString("task")
                                    task.taskTimeSlot =
                                        (arrayCurrentTask[i] as JSONObject).getString("time_slot")
                                    task.taskStartDelivery =
                                        (arrayCurrentTask[i] as JSONObject).getString("start_delivery")
                                    task.taskEndDelivery =
                                        (arrayCurrentTask[i] as JSONObject).getString("end_delivery")
                                    task.taskPhone =
                                        (arrayCurrentTask[i] as JSONObject).getString("phone")
                                    task.taskType =
                                        (arrayCurrentTask[i] as JSONObject).getString("type")
                                    task.taskHaveAddOn =
                                        (arrayCurrentTask[i] as JSONObject).getString("have_add_ons")
                                    task.taskIsPaid =
                                        (arrayCurrentTask[i] as JSONObject).getString("is_paid")

                                    task.taskDestinationLong =
                                        (arrayCurrentTask[i] as JSONObject).getDouble("long")

                                    task.taskDestinationLat =
                                        (arrayCurrentTask[i] as JSONObject).getDouble("lat")


                                    taskArrayList.add(task)
                                }
                            }
                        }
                    }

            httpAsync.join()
//        ______________________________________
        } else {
            val httpAsync =
                Constants.taskList
                    .httpPost(
                        listOf(
                            "date" to dateSelected,
                            "id" to Constants.driverId.toInt()
                        )
                    )
                    .responseString { request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()

                                Toast.makeText(
                                    applicationContext,
                                    "Getting Task Failed",
                                    Toast.LENGTH_LONG
                                )
                                    .show()

                            }
                            is Result.Success -> {
                                val data = result.get()


                                val json_data = JSONObject(data)
//                            val json_data_data = JSONObject(json_data["data"])
                                val lengthOfTasks = (json_data["data"] as JSONArray).length()
//                            ((json_data["data"] as JSONArray)[0] as JSONObject)["task"]
                                taskArrayList.clear()

                                // WHEN DRIVER IS NOT SELECTED
                                for (i in 0..(lengthOfTasks - 1)) {
                                    var arrayCurrentTask = (json_data["data"] as JSONArray)
                                    val task = TaskApiConstants()
                                    //  user.id = json_user.getInt(i)
                                    task.taskId = (arrayCurrentTask[i] as JSONObject).getInt("id")
                                    task.taskName =
                                        (arrayCurrentTask[i] as JSONObject).getString("task")
                                    task.taskTimeSlot =
                                        (arrayCurrentTask[i] as JSONObject).getString("time_slot")
                                    task.taskStartDelivery =
                                        (arrayCurrentTask[i] as JSONObject).getString("start_delivery")
                                    task.taskEndDelivery =
                                        (arrayCurrentTask[i] as JSONObject).getString("end_delivery")
                                    task.taskPhone =
                                        (arrayCurrentTask[i] as JSONObject).getString("phone")
                                    task.taskType =
                                        (arrayCurrentTask[i] as JSONObject).getString("type")
                                    task.taskHaveAddOn =
                                        (arrayCurrentTask[i] as JSONObject).getString("have_add_ons")
                                    task.taskIsPaid =
                                        (arrayCurrentTask[i] as JSONObject).getString("is_paid")

                                    task.taskDestinationLong =
                                        (arrayCurrentTask[i] as JSONObject).getDouble("long")

                                    task.taskDestinationLat =
                                        (arrayCurrentTask[i] as JSONObject).getDouble("lat")


                                    taskArrayList.add(task)
                                }
                            }
                        }
                    }

            httpAsync.join()
        }
//        ______________________________________


    }


    private fun loggingOut() {
        logoutButtonIsClicked = true
        val intent =
            Intent(this@DatePickerActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goingBackToLoginPage() {
        logoutButtonIsClicked = true
        val intent =
            Intent(this@DatePickerActivity, MainActivity::class.java)
        startActivity(intent)
    }
}