package com.ysered.beamsample.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var isDone: Boolean,
        var summary: String
)
