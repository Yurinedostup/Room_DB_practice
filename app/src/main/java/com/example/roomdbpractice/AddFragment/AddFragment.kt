package com.example.roomdbpractice.AddFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.roomdbpractice.R
import com.example.roomdbpractice.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!! // Геттер для безопасного доступа

    companion object {
        fun newInstance() = AddFragment()
    }

    private val viewModel: AddViewModel by viewModels()

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

            Log.d("AddFragment", "Нажали сохранить")
            Log.d("AddFragment", "Ввели $taskText")
            // Возвращаемся к предыдущему фрагменту
            findNavController().navigateUp()
            //navigate(R.id.action_addFragment_to_listFragment)
        }

        binding.buttonCancelTask.setOnClickListener {
            findNavController().navigateUp()
            //navigateUp()
            //navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}