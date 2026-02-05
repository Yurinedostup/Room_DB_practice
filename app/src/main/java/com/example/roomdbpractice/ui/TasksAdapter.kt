package com.example.roomdbpractice.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbpractice.data.Tasks
import com.example.roomdbpractice.databinding.ItemTaskSimpleBinding

class TasksAdapter (
    private val onCheckboxClick: (Int, Boolean) -> Unit,
    private val onDeleteClick: (Int) -> Unit
): RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private var tasks = emptyList<Tasks>()

    // 1. ViewHolder
    inner class TaskViewHolder(
        private val binding: ItemTaskSimpleBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Tasks) {
            binding.textTask.text = task.text // устанавливаем текст в задачу пришедший в функцию
            binding.checkBoxCompleted.setOnCheckedChangeListener(null) // У каждой задачи свой слушатель чекбокса
            binding.checkBoxCompleted.isChecked = task.isCompleted /* тоже для чекбокса, всё берётся
            из БД Tasks*/

            // Зачеркивание текста
            if (task.isCompleted) {
                binding.textTask.paintFlags =
                    binding.textTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textTask.paintFlags =
                    binding.textTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Слушатели нажатий
            binding.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
                onCheckboxClick(task.id, isChecked)
            }

            binding.buttonDelete.setOnClickListener {
                onDeleteClick(task.id)
            }
        }
    }

    // 2. Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskSimpleBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return TaskViewHolder(binding)
    }

    // 3. Привязка данных
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    // 4. Количество элементов
    override fun getItemCount(): Int = tasks.size

    // 5. Обновление списка
    fun submitList(newTasks: List<Tasks>) {
        val diffResult = DiffUtil.calculateDiff(
            TasksDiffCallback(tasks, newTasks))
        tasks = newTasks
        diffResult.dispatchUpdatesTo(this)
    }

    class TasksDiffCallback(
        private val oldList: List<Tasks>,
        private val newList: List<Tasks>
    ): DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return oldList[oldPos].id == newList[newPos].id
        }

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return oldList[oldPos] == newList[newPos]
        }

    }
}