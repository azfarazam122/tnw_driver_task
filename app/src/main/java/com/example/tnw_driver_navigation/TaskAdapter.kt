package com.example.tnw_driver_navigation

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tnw_driver_navigation.Constants.destinationLat
import com.example.tnw_driver_navigation.Constants.destinationLong
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.mapbox.navigation.examples.basics.TurnByTurnNavigation


class TaskAdapter(val taskList: ArrayList<TaskApiConstants>, val thisActivity: Activity) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_for_tasks, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = taskList[position]

        holder.taskName.text = currentItem.taskName
        if (currentItem.taskIsPaid == "0") {
            holder.taskName.setTextColor(Color.parseColor("#FFFF0000"));
        } else {
            holder.taskName.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if (currentItem.taskHaveAddOn == "1") {
            holder.addOnIcon.visibility = View.VISIBLE
        } else {
            holder.addOnIcon.visibility = View.GONE

        }
        holder.taskTimeSlot.text = currentItem.taskTimeSlot

        // Changing Color Of Task
        changeColorOfTaskOnBasisOfDelivery(holder, currentItem)



        holder.bikeIcon.setOnClickListener {
            if (holder.bikeIcon.contentDescription == "Delivery Not Started") {


                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(thisActivity)
//                alertDialog.setTitle("AlertDialog")
                alertDialog.setMessage("Do you want to Start Delivery?")
                alertDialog.setPositiveButton(
                    "yes"
                ) { _, _ ->
                    Toast.makeText(thisActivity, "Delivery Started.", Toast.LENGTH_LONG).show()

//                    ________________________________
//                    Delivery Starting Code
                    val httpAsync =
                        Constants.startDelivery
                            .httpPost(
                                listOf(
                                    "task_id" to currentItem.taskId,
                                    "str" to ""
                                )
                            )
                            .responseString { request, response, result ->
                                when (result) {
                                    is Result.Failure -> {
                                        val ex = result.getException()


                                    }
                                    is Result.Success -> {
                                        val data = result.get()
                                        if (data == "Updated Successfully") {
                                            holder.linearLayoutOfTask.setBackgroundColor(
                                                Color.parseColor(
                                                    "#FFE1CE19"
                                                )
                                            )
                                            holder.bikeIcon.setImageResource(R.drawable.tickicon)
                                            holder.bikeIcon.contentDescription = "Delivery Started"
                                            holder.bikeIcon.visibility = View.VISIBLE
                                            val lp =
                                                LinearLayout.LayoutParams(holder.phoneIcon.getLayoutParams())
                                            lp.setMargins(0, 0, 70, 0);
                                            holder.phoneIcon.setLayoutParams(lp);

                                        }

                                    }
                                }
                            }

                    httpAsync.join()
//                    ________________________________
                }
                alertDialog.setNegativeButton(
                    "No"
                ) { _, _ ->

                    Toast.makeText(thisActivity, "Delivery Not Started.", Toast.LENGTH_LONG).show()

                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()


            } else if (holder.bikeIcon.contentDescription == "Delivery Started") {


                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(thisActivity)
//                alertDialog.setTitle("AlertDialog")
                alertDialog.setMessage("Do you want to Finish Delivery?")
                alertDialog.setPositiveButton(
                    "yes"
                ) { _, _ ->
                    Toast.makeText(thisActivity, "Delivery Finished.", Toast.LENGTH_LONG).show()
                    val httpAsync =
                        Constants.endDelivery
                            .httpPost(
                                listOf(
                                    "task_id" to currentItem.taskId,
                                    "str" to ""
                                )
                            )
                            .responseString { request, response, result ->
                                when (result) {
                                    is Result.Failure -> {
                                        val ex = result.getException()


                                    }
                                    is Result.Success -> {
                                        val data = result.get()
                                        if (data == "Updated Successfully") {
                                            holder.linearLayoutOfTask.setBackgroundColor(
                                                Color.parseColor(
                                                    "#007225"
                                                )
                                            )
                                            holder.bikeIcon.setImageResource(R.drawable.background_for_hidingdeliveryicon);
                                            holder.bikeIcon.contentDescription = "Delivery Ended"
                                            holder.bikeIcon.visibility = View.GONE
                                            val lp =
                                                LinearLayout.LayoutParams(holder.phoneIcon.getLayoutParams())
                                            lp.setMargins(0, 0, 0, 30);
                                            holder.phoneIcon.setLayoutParams(lp);


                                        }

                                    }
                                }
                            }

                    httpAsync.join()

                }
                alertDialog.setNegativeButton(
                    "No"
                ) { _, _ ->

                    Toast.makeText(thisActivity, "Delivery Not Finished.", Toast.LENGTH_LONG).show()

                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()


            }
        }



        holder.phoneIcon.setOnClickListener {

            val dialIntent = Intent(Intent.ACTION_DIAL)
            val bundle = Bundle()
            dialIntent.data = Uri.parse("tel:" + currentItem.taskPhone.toString())
            startActivity(thisActivity, dialIntent, bundle)
        }

        holder.mapIcon.setOnClickListener {
//            currentItem.
            destinationLong = currentItem.taskDestinationLong.toString()
            destinationLat = currentItem.taskDestinationLat.toString()
            val bundle = Bundle()
            val intent =
                Intent(thisActivity, TurnByTurnNavigation::class.java)
            startActivity(thisActivity, intent, bundle)
        }

    }


    override fun getItemCount(): Int {
        return taskList.size
    }

    class ViewHolder(taskView: View) : RecyclerView.ViewHolder(taskView) {
        val taskName: TextView = taskView.findViewById(R.id.taskNameOfDriver)
        val taskTimeSlot: TextView = taskView.findViewById(R.id.timeSlotOfDriver)
        val bikeIcon: ImageView = taskView.findViewById(R.id.bikeiconId)
        val phoneIcon: ImageView = taskView.findViewById(R.id.phoneIconId)
        val mapIcon: ImageView = taskView.findViewById(R.id.mapIconId)
        val addOnIcon: ImageView = taskView.findViewById(R.id.addOnIconId)
        val linearLayoutOfTask: LinearLayout =
            taskView.findViewById(R.id.linearLayoutForTaskOfCardView)
    }

    fun changeColorOfTaskOnBasisOfDelivery(
        myHolder: TaskAdapter.ViewHolder,
        myCurrentItem: TaskApiConstants
    ) {

        if (myCurrentItem.taskStartDelivery == "null" && myCurrentItem.taskEndDelivery == "null") {
            myHolder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#000000"))
            myHolder.bikeIcon.setImageResource(R.drawable.bike_icon);
            myHolder.bikeIcon.contentDescription = "Delivery Not Started"
            myHolder.bikeIcon.visibility = View.VISIBLE
            val lp =
                LinearLayout.LayoutParams(myHolder.phoneIcon.getLayoutParams())
            lp.setMargins(0, 0, 70, 0);
            myHolder.phoneIcon.layoutParams = lp;
            if (myCurrentItem.taskType == "0" || myCurrentItem.taskType == "2") {
                myHolder.phoneIcon.visibility = View.GONE
                myHolder.mapIcon.visibility = View.GONE
            } else {
                myHolder.phoneIcon.visibility = View.VISIBLE
                myHolder.mapIcon.visibility = View.VISIBLE
            }

        } else if (myCurrentItem.taskStartDelivery != "null" && myCurrentItem.taskEndDelivery == "null") {
            myHolder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#FFE1CE19"))
            myHolder.bikeIcon.setImageResource(R.drawable.tickicon);
            myHolder.bikeIcon.contentDescription = "Delivery Started"
            myHolder.bikeIcon.visibility = View.VISIBLE
            val lp =
                LinearLayout.LayoutParams(myHolder.phoneIcon.getLayoutParams())
            lp.setMargins(0, 0, 70, 0);
            myHolder.phoneIcon.layoutParams = lp;

            if (myCurrentItem.taskType == "0" || myCurrentItem.taskType == "2") {
                myHolder.phoneIcon.visibility = View.GONE
                myHolder.mapIcon.visibility = View.GONE
            } else {
                myHolder.phoneIcon.visibility = View.VISIBLE
                myHolder.mapIcon.visibility = View.VISIBLE
            }

        } else {
            myHolder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#007225"))
            myHolder.bikeIcon.setImageResource(R.drawable.background_for_hidingdeliveryicon);
            myHolder.bikeIcon.contentDescription = "Delivery Ended"
            myHolder.bikeIcon.visibility = View.GONE
            val lp =
                LinearLayout.LayoutParams(myHolder.phoneIcon.getLayoutParams())
            lp.setMargins(0, 0, 0, 30);
            myHolder.phoneIcon.layoutParams = lp;

            if (myCurrentItem.taskType == "0" || myCurrentItem.taskType == "2") {
                myHolder.phoneIcon.visibility = View.GONE
                myHolder.mapIcon.visibility = View.GONE
            } else {
                myHolder.phoneIcon.visibility = View.VISIBLE
                myHolder.mapIcon.visibility = View.VISIBLE
            }

        }

    }

}