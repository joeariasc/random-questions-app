package com.spotapp.mobile.data.repository

import android.util.Log
import com.spotapp.mobile.data.sources.remote.firestore.FirestoreService
import com.spotapp.mobile.data.sources.remote.firebasedatabase.FDatabaseService
import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import com.spotapp.mobile.data.sources.remote.firestore.model.Question

class GameRepository(
    private val firestoreService: FirestoreService,
    private val firebaseDB: FDatabaseService,
) {

    suspend fun getQuestionList(): List<Question> = firestoreService.retrieveQuestions()

    suspend fun getRanking(): List<UserData> =
        firebaseDB.getRanking().sortedByDescending { it.score }

    suspend fun registerQuestion(questionId: String) {
        runCatching {
            firestoreService.registerVisitedQuestion(questionId)
        }.onSuccess {
            Log.d(
                "GameRepository",
                "registerQuestion success"
            )
        }.onFailure {
            throw it
        }
    }

    suspend fun setScore(scored: Boolean) {
        runCatching {
            firebaseDB.setUserScore(scored)
        }.onSuccess {
            Log.d(
                "GameRepository",
                "registerQuestion success"
            )
        }.onFailure {
            throw it
        }
    }

    suspend fun addQuestions(questions: List<Question>) {
        runCatching {
            questions.forEach {
                firestoreService.addQuestion(it)
            }
        }.onSuccess {
            Log.d("addQuestions", "Questions added successfully")
        }.onFailure {
            Log.d("addQuestions", "Error! ${it.message}")
        }
    }
}