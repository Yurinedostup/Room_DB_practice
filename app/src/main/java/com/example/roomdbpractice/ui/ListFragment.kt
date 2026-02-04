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

    // Адаптер
    private lateinit var adapter: TasksAdapter

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

        // 1. Инициализируем адаптер
        adapter = TasksAdapter(
            onCheckboxClick = { taskId, isChecked ->
                viewModel.taskCompletion(taskId, isChecked)
            },
            onDeleteClick = { taskId ->
                viewModel.removeTask(taskId)
                Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show()
            }
        )

        // 2. Настраиваем RecyclerView
        binding.recyclerView.adapter = adapter

        // 3. Подписываемся на LiveData
        viewModel.taskListLiveData.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
            Log.d(TAG, "Задачи обновились: ${tasks.size} шт")
        }


        binding.buttonAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    // 4. Удалили старый метод updateTasksList со всей логикой внутри(теперь она в адаптере и тут выше)

    override fun onDestroyView() {
        super.onDestroyView()
        // ВАЖНО: обнуляем binding при уничтожении view
        _binding = null
    }
}