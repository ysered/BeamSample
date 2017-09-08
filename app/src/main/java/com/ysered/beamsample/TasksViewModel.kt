package com.ysered.beamsample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ysered.beamsample.database.TaskEntity
import com.ysered.beamsample.database.TasksDao
import javax.inject.Inject


class TasksViewModel @Inject constructor(
        private val tasksDao: TasksDao
) : ViewModel() {

    val tasks = MutableLiveData<List<TaskEntity>>()


}