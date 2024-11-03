package com.mightysana.onewallet.oneproject.auth.model

import com.google.firebase.auth.FirebaseUser

interface AuthService {
    val currentUser: FirebaseUser?
    fun hasUser(): Boolean
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun signUpWithEmailAndPassword(email: String, password: String)
    suspend fun sendEmailVerification()
    suspend fun isEmailVerified(): Boolean
    suspend fun reloadCurrentUser()
    suspend fun signOut()
}
