package com.spotapp.mobile.domain.usecases

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.spotapp.mobile.data.repository.GameRepository
import com.spotapp.mobile.data.sources.remote.firestore.model.Option
import com.spotapp.mobile.data.sources.remote.firestore.model.Question

class QuizMaster(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(option: Option, question: Question) {
        runCatching {
            gameRepository.registerQuestion(question.id)
        }.onSuccess {
            Log.d("QuizMasterUseCase", "question registered successfully")
            runCatching {
                gameRepository.setScore(option.id == question.idCorrectOption)
            }.onSuccess {
                Log.d("QuizMasterUseCase", "set scored successfully")
            }.onFailure {
                Log.d("QuizMasterUseCase", "fail: setting score")
                throw it
            }
        }.onFailure {
            Log.d("QuizMasterUseCase", "fail: registering question")
            throw it
        }
    }
}