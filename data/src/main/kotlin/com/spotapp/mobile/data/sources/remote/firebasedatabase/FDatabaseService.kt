package com.spotapp.mobile.data.sources.remote.firebasedatabase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.spotapp.mobile.data.sources.remote.firebasedatabase.model.UserData
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FDatabaseService(private val auth: FirebaseAuth) {

    private val firebaseDB: DatabaseReference = Firebase.database.reference

    fun subscribeToRealtimeUpdates() {
        auth.currentUser?.let {
            firebaseDB.child("users").addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    suspend fun getRanking() {
        runCatching {
            auth.currentUser?.let {
                firebaseDB.child("users").get().await()
            }
        }
    }

    suspend fun setUserScore(scored: Boolean) {
        runCatching {
            auth.currentUser?.run {
                firebaseDB.child("users").child(uid).get().await()
            }
        }.onSuccess { snapshot ->
            if (snapshot?.exists() == true) {
                val userData = snapshot.getValue(UserData::class.java)
                findAndSetScore(
                    score = if (scored) (userData?.score!!.plus(1)) else 0,
                    email = userData?.email!!
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