package com.example.roomdbpractice.ListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    //Приватное изменяемое значение LiveDaata
    private val _taskList = MutableLiveData<List<String>>()

    //Публичное неизменяемое LiveData (только для чтения)
    val taskList: LiveData<List<String>>
        get() = _taskList

    fun addTask(text: String) {
        // Берём текущее значение списка
        val currentList = _taskList.value ?: emptyList()

        // Создаём новый список, добавляя новый элемент списка
        val newList = currentList.toMutableList().apply {
            add(text)
        }
        // Кладём изменённый список в MutableLiveData<> контейнер
        _taskList.value = newList
    }

    fun removeTask(position: Int) {
        val currentList = _taskList.value ?: return
        if (position in currentList.indices) {
            val newList = currentList.toMutableList()
            newList.removeAt(position)
            _taskList.value = newList
        }
    }
}