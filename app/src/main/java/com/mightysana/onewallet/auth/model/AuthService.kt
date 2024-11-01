package com.mightysana.onewallet.auth.model

interface AuthService {
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun signUpWithEmailAndPassword(email: String, password: String)
}