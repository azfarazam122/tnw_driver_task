package com.example.tnw_driver_navigation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(val taskList: ArrayList<TaskApiConstants>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_for_tasks, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val currentItem = taskList[position]

        holder.taskName.text = currentItem.taskName
        holder.taskTimeSlot.text = currentItem.taskTimeSlot
        if (currentItem.taskStartDelivery == null && currentItem.taskEndDelivery == null) {
//            holder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#FFE1CE19"))
            holder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#000000"))
        } else if (currentItem.taskStartDelivery != null && currentItem.taskEndDelivery == null) {
            holder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#FFE1CE19"))
        } else {
            holder.linearLayoutOfTask.setBackgroundColor(Color.parseColor("#007225"))
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class ViewHolder(taskView: View) : RecyclerView.ViewHolder(taskView) {
        val taskName: TextView = taskView.findViewById(R.id.taskNameOfDriver)
        val taskTimeSlot: TextView = taskView.findViewById(R.id.timeSlotOfDriver)
        val linearLayoutOfTask: LinearLayout =
            taskView.findViewById(R.id.linearLayoutForTaskOfCardView)
    }


}