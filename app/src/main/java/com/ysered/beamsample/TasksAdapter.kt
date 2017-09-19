package com.ysered.beamsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ysered.beamsample.database.TaskEntity
import com.ysered.beamsample.util.setVisibility

class TasksAdapter(
        val tasks: MutableList<TaskEntity> = mutableListOf(),
        private val onTaskClickListener: OnTaskClickListener
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent?.context)?.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder?, position: Int) {
        tasks.getOrNull(position)?.let { holder?.bind(it) }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateAll(tasks: List<TaskEntity>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        // TODO: use DiffUtil to notify which items were updated instead of refreshing all list
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val doneImage = itemView?.findViewById<ImageView>(R.id.doneImage)
        private val summaryText = itemView?.findViewById<TextView>(R.id.summaryText)

        init {
            itemView?.setOnClickListener {
                val task = tasks[adapterPosition].apply { isDone = !isDone }
                onTaskClickListener.onClicked(task)
            }
        }

        fun bind(task: TaskEntity) {
            doneImage?.setVisibility(task.isDone)
            summaryText?.text = task.summary
        }
    }

    interface OnTaskClickListener {
        fun onClicked(task: TaskEntity)
    }
}
