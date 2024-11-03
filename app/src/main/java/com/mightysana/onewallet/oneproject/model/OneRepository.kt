package com.mightysana.onewallet.oneproject.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OneRepository @Inject constructor() {
    val database: DatabaseReference = Firebase.database.reference
    val usersRef: DatabaseReference = database.child(USERS_REF)
    val oneWalletRef: DatabaseReference = database.child(ONE_WALLET_REF)

    fun updateUser(userData: OneUser) {
        usersRef.child(userData.uid).setValue(userData)
    }

    suspend fun isUserRegistered(userId: String): Boolean {
        return usersRef.child(userId).get().await().exists()
    }

    fun observeUser(
        userId: String,
        onUpdate: (OneUser) -> Unit
    ) {
        usersRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.child(userId).getValue(OneUser::class.java)
                user?.let { onUpdate(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(USERS_REF, "Failed to read value.", error.toException())
            }
        })
    }
}