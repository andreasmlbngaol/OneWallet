package com.mightysana.onewallet.auth.model

interface AuthService {
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun signUpWithEmailAndPassword(email: String, password: String)
    suspend fun signOut()
}