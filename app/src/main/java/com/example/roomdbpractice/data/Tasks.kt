package com.example.roomdbpractice.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val text: String,

    @ColumnInfo
    val isCompleted: Boolean = false,

    @ColumnInfo
    val created: Long = System.currentTimeMillis()/1000
)
