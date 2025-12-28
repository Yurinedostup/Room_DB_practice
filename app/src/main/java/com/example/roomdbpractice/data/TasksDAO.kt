package com.example.roomdbpractice.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface TasksDAO {
    @Insert
    suspend fun insert(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)

    @Update
    suspend fun update(task: Tasks)

    @Query("SELECT * FROM tasks_table ORDER BY created DESC")
    fun getAll(): LiveData<List<Tasks>>
}