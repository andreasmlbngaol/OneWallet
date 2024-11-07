package com.mightysana.onewallet.oneproject.auth.model.service

import com.google.firebase.auth.FirebaseUser

interface AccountService {
    val currentUser: FirebaseUser?
    val userId: String?
    fun hasUser(): Boolean
    suspend fun reloadUser()
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun signUpWithEmailAndPassword(email: String, password: String)
    suspend fun sendEmailVerification()
    suspend fun isEmailVerified(): Boolean
    suspend fun reloadCurrentUser()
    suspend fun signOut()
}
