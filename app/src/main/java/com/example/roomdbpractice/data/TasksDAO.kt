package com.example.roomdbpractice.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
abstract interface TasksDAO {
    @Insert
    suspend fun insert(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)

    @Update
    suspend fun update(task: Tasks)

    @Query("SELECT * FROM tasks_table ORDER BY created DESC")
    fun getAll(): LiveData<List<Tasks>>

    @Query("DELETE FROM tasks_table WHERE id = :taskId") // Посмотреть что за синтаксис :
    suspend fun deleteTaskById(taskId: Int)

    @Query("SELECT * FROM tasks_table WHERE id = :taskId") // Посмотреть что за синтаксис :
    suspend fun getTaskById(taskId: Int): Tasks?

    @Query("UPDATE tasks_table SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean)
}