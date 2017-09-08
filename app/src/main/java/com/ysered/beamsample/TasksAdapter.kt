package com.ysered.beamsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ysered.beamsample.database.TaskEntity
import com.ysered.beamsample.util.setVisibility

class TasksAdapter(private val tasks: List<TaskEntity>) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent?.context)?.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        holder?.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val doneImage = itemView?.findViewById<ImageView>(R.id.doneImage)
        private val summaryText = itemView?.findViewById<TextView>(R.id.summaryText)

        fun bind(task: TaskEntity) {
            doneImage?.setVisibility(task.isDone)
            summaryText?.text = task.summary
        }
    }
}
