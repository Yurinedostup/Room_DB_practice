package com.example.roomdbpractice.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksViewModel : ViewModel() {

    private val _taskListLiveData = MutableLiveData<List<String>>()

    val taskListLiveData: LiveData<List<String>>
        get() = _taskListLiveData

    companion object {
        const val VM_TAG = "ViewModel"
    }

    fun addTask(text: String) {
        // Берём текущее значение списка
        val currentList = _taskListLiveData.value ?: emptyList()

        // Создаём новый список, добавляя новый элемент списка
        val newList = currentList.toMutableList().apply {
            add(text)
        }
        // Кладём изменённый список в MutableLiveData<> контейнер
        _taskListLiveData.value = newList
        Log.d(VM_TAG, "Добавили элемент $text, список ${_taskListLiveData.value}")
    }

    fun removeTask(position: Int) {
        val currentList = _taskListLiveData.value ?: return
        if (position in currentList.indices) {
            val newList = currentList.toMutableList()
            Log.d(VM_TAG, "Удалили ${newList.get(position)}")
            newList.removeAt(position)
            _taskListLiveData.value = newList
            Log.d(VM_TAG,"Текущий список ${_taskListLiveData.value}")
        }

    }
}