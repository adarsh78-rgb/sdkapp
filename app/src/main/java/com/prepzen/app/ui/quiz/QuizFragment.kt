package com.prepzen.app.ui.quiz

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prepzen.app.R
import com.prepzen.app.databinding.FragmentQuizBinding
import com.prepzen.app.utils.ServiceLocator

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizSelectionViewModel by viewModels {
        QuizSelectionViewModel.Factory(ServiceLocator.contentRepository(requireContext()))
    }

    private var topicIdByTitle: Map<String, String> = emptyMap()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizBinding.bind(view)

        val levels = listOf("Easy", "Medium", "Hard")
        binding.difficultyDropdown.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, levels)
        )
        binding.difficultyDropdown.setText("Easy", false)

        viewModel.topics.observe(viewLifecycleOwner) { topics ->
            val titles = topics.map { it.title }
            topicIdByTitle = topics.associate { it.title to it.id }
            binding.topicDropdown.setAdapter(
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, titles)
            )
            if (titles.isNotEmpty()) {
                binding.topicDropdown.setText(titles.first(), false)
            }
        }

        binding.startQuizButton.setOnClickListener {
            val selectedTitle = binding.topicDropdown.text.toString()
            val selectedDifficulty = binding.difficultyDropdown.text.toString()
            val topicId = topicIdByTitle[selectedTitle] ?: return@setOnClickListener
            findNavController().navigate(
                R.id.quizSessionFragment,
                Bundle().apply {
                    putString("topicId", topicId)
                    putString("difficulty", selectedDifficulty)
                }
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
