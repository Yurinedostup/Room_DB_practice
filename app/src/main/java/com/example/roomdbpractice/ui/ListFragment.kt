package com.example.roomdbpractice.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.roomdbpractice.databinding.FragmentListBinding
import com.example.roomdbpractice.databinding.ItemTaskSimpleBinding
import android.graphics.Paint
import android.widget.Toast
import com.example.roomdbpractice.R
import com.example.roomdbpractice.data.Tasks
import com.example.roomdbpractice.data.TasksDatabase

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!! // Геттер для безопасного доступа

//    private val viewModel: TasksViewModel by activityViewModels()

    private val viewModel: TasksViewModel by activityViewModels {
        TasksViewModelFactory(
            TasksDatabase.getDatabase(requireContext()).taskDao(),
            requireActivity().application
        )
    }

    companion object {
        fun newInstance() = ListFragment()
        const val TAG = "ListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.taskListLiveData.observe(viewLifecycleOwner) { tasks ->
            updateTasksList(tasks) // Работа с Task-листом, а поскольку у нас есть LiveData
            // то изменения будут динамичны
            Log.d(TAG, "Задачи обновились: $tasks")
        }


        binding.buttonAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    // Обработка всех действий с Task-листом
    private fun updateTasksList(tasks: List<Tasks>) {
        // Очищаем контейнер
        binding.tasksContainer.removeAllViews() //???


        tasks.forEach { task ->
            // Для каждой задачи создаём и добавляем View
            val itemBinding = ItemTaskSimpleBinding.inflate(
                layoutInflater,
//                null,
                binding.tasksContainer, // Будующий родитель(куда кладём)
                false // Не добавлять сразу в родителя
            )

            itemBinding.textTask.text = task.text

            binding.tasksContainer.addView(itemBinding.root)

            itemBinding.checkBoxCompleted.isChecked = task.isCompleted

            // Изменяем UI в соответствии с состоянием задачи
            if (task.isCompleted) {
                itemBinding.textTask.paintFlags =
                    itemBinding.textTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                itemBinding.textTask.paintFlags =
                    itemBinding.textTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Меняем состояние задачи по нажатию на чекбокс
            itemBinding.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                viewModel.taskCompletion(task.id, isChecked)
            }

            itemBinding.buttonDelete.setOnClickListener {
                viewModel.removeTask(task.id)
                Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show()

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ВАЖНО: обнуляем binding при уничтожении view
        _binding = null
    }
}