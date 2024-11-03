package com.mightysana.onewallet.oneproject.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import javax.inject.Inject

class OneRepository @Inject constructor() {
    private val database: DatabaseReference = Firebase.database.reference
    private val usersRef: DatabaseReference = database.child(USERS_REF)
    private val oneWalletRef: DatabaseReference = database.child(ONE_WALLET_REF)

    fun updateUser(userId: String, userData: OneUser) {
        usersRef.child(userId).setValue(userData)
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