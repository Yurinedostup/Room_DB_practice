package com.example.roomdbpractice.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdbpractice.data.TasksDAO

class TasksViewModelFactory(
    private val dataSource: TasksDAO,
    private val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Неизвестный ViewModel class")
    }
}