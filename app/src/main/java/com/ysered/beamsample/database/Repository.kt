package com.ysered.beamsample.database


class Repository(private val tasksDao: TasksDao) {

    fun saveTask(summary: String) {
        tasksDao.save(TaskEntity(id = 0, isDone = false, summary = summary))
    }
}
