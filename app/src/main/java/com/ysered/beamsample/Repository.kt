package com.ysered.beamsample

import android.content.Context
import com.ysered.beamsample.database.TasksDatabase


class Repository(context: Context) {
    private val tasksDao = TasksDatabase.create(context).taskDao

    fun getTasks() {
        tasksDao.getAll()
    }
}