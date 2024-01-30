package com.spotapp.mobile.data.sources.remote.firestore.model

data class Question(
    val id: String = "",
    val questionText: String = "",
    val options: List<Option> = emptyList(),
    val idCorrectOption: Int = 0
)

data class Option(
    val id: Int = 0,
    val optionText: String = "",
)

data class VisitedQuestions(
    val questionId: String = "",
    val userId: String = "",
)