package com.prepzen.app.ui.quiz

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.prepzen.app.R
import com.prepzen.app.databinding.FragmentQuizSessionBinding
import com.prepzen.app.utils.ServiceLocator

class QuizSessionFragment : Fragment(R.layout.fragment_quiz_session) {

    private var _binding: FragmentQuizSessionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizSessionViewModel by viewModels {
        QuizSessionViewModel.Factory(
            ServiceLocator.contentRepository(requireContext()),
            ServiceLocator.userPrefsRepository(requireContext())
        )
    }

    private var interstitialAd: InterstitialAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizSessionBinding.bind(view)

        loadInterstitial()

        val topicId = requireArguments().getString("topicId").orEmpty()
        val difficulty = requireArguments().getString("difficulty").orEmpty()
        viewModel.start(topicId, difficulty)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.completed) {
                binding.questionTitle.text = getString(R.string.quiz_complete)
                binding.questionCounter.text = getString(R.string.score_text, state.score, state.total)
                binding.optionsContainer.children.forEach { it.visibility = View.GONE }
                binding.nextButton.text = getString(R.string.back_to_quiz)
                binding.nextButton.setOnClickListener {
                    showInterstitialAndNavigateBack()
                }
                return@observe
            }

            val question = state.question ?: return@observe
            binding.questionCounter.text = getString(
                R.string.question_counter,
                state.currentIndex + 1,
                state.total
            )
            binding.questionTitle.text = question.question

            val buttons = binding.optionsContainer.children.filterIsInstance<Button>().toList()
            buttons.forEachIndexed { index, button ->
                button.visibility = View.VISIBLE
                button.text = question.options.getOrNull(index).orEmpty()
                button.setOnClickListener {
                    buttons.forEach { it.isEnabled = false }
                    viewModel.submit(index)
                }
                button.isEnabled = true
            }

            binding.nextButton.text = getString(R.string.next)
            binding.nextButton.setOnClickListener { viewModel.next() }
        }

        viewModel.feedbackEvent.observe(viewLifecycleOwner) { (isCorrect, explanation) ->
            val title = if (isCorrect) getString(R.string.correct) else getString(R.string.incorrect)
            AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(explanation)
                .setPositiveButton(R.string.continue_text, null)
                .show()
        }
    }

    private fun loadInterstitial() {
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            findNavController().popBackStack()
                        }
                    }
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    private fun showInterstitialAndNavigateBack() {
        val ad = interstitialAd
        if (ad != null) {
            ad.show(requireActivity())
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
