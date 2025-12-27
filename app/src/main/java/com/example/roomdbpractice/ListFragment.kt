package com.example.roomdbpractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.roomdbpractice.databinding.FragmentListBinding
import com.example.roomdbpractice.databinding.ItemTaskSimpleBinding
import android.graphics.Paint

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!! // Геттер для безопасного доступа

    private val viewModel: TasksViewModel by activityViewModels()

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализируем binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Подписка на изменение данных
        viewModel.taskList.observe(viewLifecycleOwner) { tasks ->
            Log.d("ListFragment", "Задачи обновились: $tasks")
            updateTasksList(tasks) // Работа с Task-листом, а поскольку у нас есть LiveData
            // то изменения будут динамичны
        }


        binding.buttonAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    // Обработка любых действий с Task-листом
    private fun updateTasksList(tasks: List<String>) {
        // Очищаем контейнер
        binding.tasksContainer.removeAllViews()


        for (task in tasks) {
            // Для каждой задачи создаём и добавляем View
            val itemBinding = ItemTaskSimpleBinding.inflate(
                layoutInflater,
                binding.tasksContainer, // Будующий родитель(куда кладём)
                false // Не добавлять сразу в родителя
            )
            itemBinding.textTask.text = task
            binding.tasksContainer.addView(itemBinding.root)

            itemBinding.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    itemBinding.textTask.paintFlags =
                        itemBinding.textTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    itemBinding.textTask.paintFlags =
                        itemBinding.textTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            itemBinding.buttonDelete.setOnClickListener {
                viewModel.removeTask(task.indexOf(task))
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ВАЖНО: обнуляем binding при уничтожении view
        _binding = null
    }
}