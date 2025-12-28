package com.example.roomdbpractice.data

data class Task(
    val id: Int = 0,
    val text: String,
    val isCompleted: Boolean = false,
    val created: Long = System.currentTimeMillis()
)
