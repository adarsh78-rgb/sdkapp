package com.prepzen.app.domain

enum class TopicCategory {
    ENGLISH,
    APTITUDE
}

data class Topic(
    val id: String,
    val title: String,
    val category: TopicCategory,
    val subCategory: String,
    val explanation: String,
    val example: String,
    val practice: String
)

data class QuizQuestion(
    val id: String,
    val topicId: String,
    val topicTitle: String,
    val difficulty: String,
    val question: String,
    val options: List<String>,
    val answerIndex: Int,
    val explanation: String
)

data class QuizScore(
    val topicId: String,
    val topicTitle: String,
    val score: Int,
    val total: Int,
    val timestamp: Long
)
