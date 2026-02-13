package com.prepzen.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prepzen.app.R
import com.prepzen.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.cardEnglish.setOnClickListener { findNavController().navigate(R.id.englishFragment) }
        binding.cardAptitude.setOnClickListener { findNavController().navigate(R.id.aptitudeFragment) }
        binding.cardQuiz.setOnClickListener { findNavController().navigate(R.id.quizFragment) }
        binding.cardProgress.setOnClickListener { findNavController().navigate(R.id.progressFragment) }
        binding.cardAbout.setOnClickListener { findNavController().navigate(R.id.aboutFragment) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
