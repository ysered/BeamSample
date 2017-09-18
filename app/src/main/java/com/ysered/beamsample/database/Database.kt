package com.ysered.beamsample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(TaskEntity::class), version = 1)
abstract class TasksDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): TasksDatabase =
                Room.databaseBuilder(context, TasksDatabase::class.java, "beam-sample.db").build()
    }

    abstract val taskDao: TasksDao
}
