package com.prepzen.app.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prepzen.app.data.ContentRepository
import com.prepzen.app.domain.Topic

class QuizSelectionViewModel(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _topics = MutableLiveData<List<Topic>>()
    val topics: LiveData<List<Topic>> = _topics

    init {
        _topics.value = contentRepository.getAvailableQuizTopics()
    }

    class Factory(
        private val contentRepository: ContentRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizSelectionViewModel(contentRepository) as T
        }
    }
}
