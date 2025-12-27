package com.example.roomdbpractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.roomdbpractice.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!! // Геттер для безопасного доступа

    private val viewModel: TasksViewModel by activityViewModels()

    companion object {
        fun newInstance() = AddFragment()
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
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonConfirmTask.setOnClickListener {
            val taskText = binding.textEditTask.text.toString()
            viewModel.addTask(taskText) // Добавляем задачу в список
            Log.d("AddFragment", "Нажали сохранить")
            Log.d("AddFragment", "Ввели $taskText")
            // Возвращаемся к предыдущему фрагменту
            findNavController().navigateUp()
        }

        binding.buttonCancelTask.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}