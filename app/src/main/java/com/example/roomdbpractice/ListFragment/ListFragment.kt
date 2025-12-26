package com.example.roomdbpractice.ListFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.roomdbpractice.R
import com.example.roomdbpractice.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!! // Геттер для безопасного доступа

    companion object {
        fun newInstance() = ListFragment()
    }

    private val viewModel: ListViewModel by viewModels()

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
        binding.buttonAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            /* :hmm: работает. А зачем тогда делать так view ->
            view.findNavController().navigate(R.id.action_listFragment_to_addFragment)?
             */
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ВАЖНО: обнуляем binding при уничтожении view
        _binding = null
    }
}