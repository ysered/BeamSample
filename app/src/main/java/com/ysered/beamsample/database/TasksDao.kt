package com.ysered.beamsample.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(taskEntity: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAll(): LiveData<TaskEntity>
}
