package com.mightysana.onewallet.oneproject.auth.model.impl

import android.util.Log
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mightysana.onewallet.isNotNull
import com.mightysana.onewallet.oneproject.auth.model.AuthService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor() : AuthService {

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean = false

    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendEmailVerification() {
        val user = Firebase.auth.currentUser
        if(user.isNotNull()) {
            user!!.sendEmailVerification().await()
            Log.d("AuthServiceImpl", "Email sent.")
        } else {
            Log.d("AuthServiceImpl", "User is null.")
        }
    }

    override suspend fun isEmailVerified(): Boolean {
        return Firebase.auth.currentUser!!.isEmailVerified
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }
}