package com.prepzen.app.data

import android.content.Context
import com.prepzen.app.domain.QuizQuestion
import com.prepzen.app.domain.Topic
import com.prepzen.app.domain.TopicCategory
import org.json.JSONArray

class ContentRepository(private val context: Context) {

    private val topics: List<Topic> by lazy { loadTopics() }
    private val questions: List<QuizQuestion> by lazy { loadQuestions() }

    fun getTopics(category: TopicCategory): List<Topic> = topics.filter { it.category == category }

    fun getAllTopics(): List<Topic> = topics

    fun getTopicById(id: String): Topic? = topics.find { it.id == id }

    fun searchTopics(category: TopicCategory, query: String): List<Topic> {
        if (query.isBlank()) return getTopics(category)
        val term = query.trim().lowercase()
        return getTopics(category).filter {
            it.title.lowercase().contains(term) ||
                it.subCategory.lowercase().contains(term)
        }
    }

    fun getQuestions(topicId: String, difficulty: String): List<QuizQuestion> {
        return questions.filter {
            it.topicId == topicId && it.difficulty.equals(difficulty, ignoreCase = true)
        }
    }

    fun getAvailableQuizTopics(): List<Topic> {
        val topicIdsWithQuestions = questions.map { it.topicId }.toSet()
        return topics.filter { topicIdsWithQuestions.contains(it.id) }
    }

    private fun loadTopics(): List<Topic> {
        val json = context.assets.open("topics.json").bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        val result = mutableListOf<Topic>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            result += Topic(
                id = obj.getString("id"),
                title = obj.getString("title"),
                category = TopicCategory.valueOf(obj.getString("category")),
                subCategory = obj.getString("subCategory"),
                explanation = obj.getString("explanation"),
                example = obj.getString("example"),
                practice = obj.getString("practice")
            )
        }
        return result
    }

    private fun loadQuestions(): List<QuizQuestion> {
        val json = context.assets.open("quizzes.json").bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        val result = mutableListOf<QuizQuestion>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val optionsArray = obj.getJSONArray("options")
            val options = mutableListOf<String>()
            for (j in 0 until optionsArray.length()) {
                options += optionsArray.getString(j)
            }
            result += QuizQuestion(
                id = obj.getString("id"),
                topicId = obj.getString("topicId"),
                topicTitle = obj.getString("topicTitle"),
                difficulty = obj.getString("difficulty"),
                question = obj.getString("question"),
                options = options,
                answerIndex = obj.getInt("answerIndex"),
                explanation = obj.getString("explanation")
            )
        }
        return result
    }
}
