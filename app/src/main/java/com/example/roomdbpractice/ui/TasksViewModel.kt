package com.example.roomdbpractice.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdbpractice.data.Task

class TasksViewModel : ViewModel() {

    private val _taskListLiveData = MutableLiveData<List<Task>>()

    val taskListLiveData: LiveData<List<Task>>
        get() = _taskListLiveData

    companion object {
        const val TAG = "ViewModel"
    }

    fun addTask(text: String) {
        // Берём текущее значение списка
        val currentList = _taskListLiveData.value ?: emptyList()

        val newTask = Task(text = text)
        // Создаём новый список, добавляя новый элемент списка
        val newList = currentList.toMutableList().apply {
            add(newTask)
        }
        // Кладём изменённый список в MutableLiveData<> контейнер
        _taskListLiveData.value = newList
        Log.d(TAG, "Добавили элемент $text, список ${_taskListLiveData.value}")
    }

    fun removeTask(taskId: Int) {
        val currentList = _taskListLiveData.value ?: return
        if (taskId in currentList.indices) {
            val newList = currentList.toMutableList()
            Log.d(TAG, "Удалили ${newList.get(taskId)}")
            newList.removeAt(taskId)
            _taskListLiveData.value = newList
            Log.d(TAG,"Текущий список ${_taskListLiveData.value}")
        }

    }
}