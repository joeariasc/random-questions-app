package com.spotapp.mobile.data.sources.remote.firebasedatabase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FDatabaseService(
    private val auth: FirebaseAuth,
) {

    private val firebaseDB: DatabaseReference = Firebase.database.reference

    val subscribeToRankingUpdates: Flow<List<UserData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("subscribeToRankingUpdates", "onDataChange!")
                val dataList = mutableListOf<UserData>()
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.getValue(UserData::class.java)
                    dataList.add(value!!)
                }
                trySend(dataList.sortedByDescending { it.score })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("rankinUpdate", "Error => ${error.message}")
                cancel()
            }
        }

        trySend(emptyList())

        firebaseDB.child("users").addValueEventListener(listener)
        awaitClose { firebaseDB.removeEventListener(listener) }
    }

    suspend fun getRanking(): List<UserData> {
        runCatching {
            auth.currentUser?.run {
                firebaseDB.child("users").get().await()
            }
        }.fold(
            onSuccess = { dataSnapshot ->
                val userList = mutableListOf<UserData>()
                dataSnapshot?.children?.forEach { snapshot ->
                    snapshot.getValue(UserData::class.java)?.let { userList.add(it) }
                }
                return userList
            },
            onFailure = {
                throw it
            }
        )
    }

    suspend fun setUserScore(scored: Boolean) {
        runCatching {
            auth.currentUser?.run {
                firebaseDB.child("users").child(uid).get().await()
            }
        }.onSuccess { snapshot ->
            if (snapshot?.exists() == true) {
                val userData = snapshot.getValue(UserData::class.java)
                val score = if (scored) (userData?.score!!.plus(1)) else userData?.score!!
                findAndSetScore(
                    score = if (scored) (userData.score.plus(1)) else score,
                    email = userData.email!!
                )
            } else {
                findAndSetScore(
                    score = if (scored) 1 else 0,
                    email = auth.currentUser?.email!!
                )
            }
            Log.d("FDatabaseService", "onSuccess")
        }.onFailure {
            Log.d("FDatabaseService", "onFailure, error => ${it.message}")
        }

    }

    private fun findAndSetScore(score: Int, email: String) {
        firebaseDB.child("users").child(auth.currentUser?.uid!!).let {
            it.setValue(
                UserData(
                    email = email,
                    score = score,
                    lastConnection = getCurrentTimeFormatted()
                )
            )
        }
    }

    private fun getCurrentTimeFormatted(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
        return dateFormat.format(currentTime)
    }

}