package com.spotapp.mobile.data.sources.remote.firestore

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.spotapp.mobile.data.models.User
import com.spotapp.mobile.data.sources.remote.firestore.model.Question
import com.spotapp.mobile.data.sources.remote.firestore.model.UserObjectType
import com.spotapp.mobile.data.sources.remote.firestore.model.VisitedQuestions
import kotlinx.coroutines.tasks.await

class FirestoreService(private val auth: FirebaseAuth) {
    private val firestoreDB: FirebaseFirestore = Firebase.firestore
    private val activeSnapshotListenerRegistrations: MutableList<ListenerRegistration> =
        mutableListOf()

    private val usersPath = "test/global/users"
    private val questionsPath = "test/global/questions"
    private val visitedQuestionsPath = "test/global/visitedQuestions"

    fun subscribeToRealtimeUpdates() {
        val collectionPaths = mutableListOf(
            usersPath,
            questionsPath,
        )
        auth.currentUser?.let {
            for (path in collectionPaths) {
                val collectionReference = firestoreDB.collection("game")
                val listenerRegistration =
                    collectionReference.addSnapshotListener(MetadataChanges.INCLUDE) { _, exception ->
                        exception?.let { firestoreException ->
                            Log.d("FirestoreService", "$firestoreException")
                            return@addSnapshotListener
                        }
                    }

                activeSnapshotListenerRegistrations.add(listenerRegistration)
            }
        }
    }

    suspend fun addUser(user: User) {
        val newUser = hashMapOf(
            "displayName" to user.name,
            "email" to user.email,
            "isAnonymous" to user.isAnonymous,
        )
        runCatching {
            firestoreDB.collection(usersPath).document(user.id!!).set(newUser).await()
        }.onSuccess {
            Log.d("FirestoreService", "addUser success, document reference:")
        }.onFailure {
            Log.d("FirestoreService", "addUser failure, error: ${it.message}")
            throw it
        }
    }

    suspend fun addQuestion(question: Question) {
        runCatching {
            firestoreDB.collection(questionsPath).add(mapQuestionToMap(question)).await()
        }.onSuccess {
            Log.d("FirestoreService", "onSuccess, question added!")
        }.onFailure {
            Log.d("FirestoreService", "onFailure, error ${it.message}")
        }
    }

    private suspend fun getVisitedQuestionsIds(): List<String> {
        runCatching {
            retrieveVisitedQuestions()
        }.fold(
            onSuccess = { visitedQuestions ->
                return visitedQuestions.filter {
                    it.userId == auth.currentUser!!.uid
                }.map {
                    it.questionId
                }
                /*retrieveQuestions().filter { question ->
                    question.id !in visitedQuestionIdsForUser
                }*/
            }, onFailure = {
                throw it
            }
        )
    }

    suspend fun retrieveQuestions(): List<Question> {
        return runCatching {
            firestoreDB.collection(questionsPath)
                .get().await().toObjects(Question::class.java)
        }.fold(
            onSuccess = { questions ->
                Log.d("FirestoreService", "getQuestionsList success, list: ${questions.size}")
                questions.filter { question ->
                    question.id !in getVisitedQuestionsIds()
                }
            },
            onFailure = {
                Log.d("FirestoreService", "getQuestionsList failure, error: ${it.message}")
                throw it
            }
        )
    }

    private suspend fun retrieveVisitedQuestions(): List<VisitedQuestions> {
        return runCatching {
            firestoreDB.collection(visitedQuestionsPath)
                .get().await().toObjects(VisitedQuestions::class.java)
        }.fold(
            onSuccess = {
                Log.d("FirestoreService", "retrieveVisitedQuestions success, list: ${it.size}")
                it
            },
            onFailure = {
                Log.d("FirestoreService", "retrieveVisitedQuestions failure, error: ${it.message}")
                throw it
            }
        )
    }

    suspend fun registerVisitedQuestion(questionId: String) {
        runCatching {
            firestoreDB.collection(visitedQuestionsPath).add(
                mapOf(
                    "questionId" to questionId,
                    "userId" to auth.currentUser!!.uid,
                )
            ).await()
        }.onSuccess { documentReference ->
            Log.d(
                "FirestoreService",
                "registerVisitedQuestion success, document reference => ${documentReference.id}"
            )

        }.onFailure {
            throw it
        }
    }

    private fun mapQuestionToMap(question: Question): Map<String, Any> {
        return mapOf(
            "id" to question.id,
            "questionText" to question.questionText,
            "options" to question.options.map { option ->
                mapOf(
                    "id" to option.id,
                    "optionText" to option.optionText,
                )
            },
            "idCorrectOption" to question.idCorrectOption
        )
    }
}