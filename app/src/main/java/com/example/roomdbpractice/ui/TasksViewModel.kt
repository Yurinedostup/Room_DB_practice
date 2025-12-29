package com.example.roomdbpractice.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.util.copy
import com.example.roomdbpractice.data.Tasks
import com.example.roomdbpractice.data.TasksDAO
import kotlinx.coroutines.launch

class TasksViewModel(
    val database: TasksDAO,
    application: Application
) : AndroidViewModel(application) {

    val taskListLiveData: LiveData<List<Tasks>> = database.getAll()

    companion object {
        const val TAG = "ViewModel"
    }

    fun addTask(text: String) {
        // Запускаем в корутие, операции БД дб асинхронны
        viewModelScope.launch {
            val newTask = Tasks(
                text = text,
                created = System.currentTimeMillis()/1000,
                isCompleted = false
            )
            // Добавляем задачу в БД
            database.insert(newTask)
        }

        Log.d(TAG, "Добавили задачу в БД: $text")
    }

    fun removeTask(taskId: Int) {
        viewModelScope.launch {
            // Удаляем из БД
            Log.d(TAG, "Удалили задачу из БД: ${database.getTaskById(taskId)}")
            database.deleteTaskById(taskId)
        }
    }


    fun taskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            database.updateTaskCompletion(taskId, isCompleted)
            Log.d(
                TAG, "Обновили статус задачи ${database.getTaskById(taskId)}:" +
                        "$isCompleted"
            )
        }
    }
}
