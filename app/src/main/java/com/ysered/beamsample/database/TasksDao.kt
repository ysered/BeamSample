package com.ysered.beamsample.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(taskEntity: TaskEntity)

    @Update
    fun update(taskEntity: TaskEntity)

    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<TaskEntity>>
}
