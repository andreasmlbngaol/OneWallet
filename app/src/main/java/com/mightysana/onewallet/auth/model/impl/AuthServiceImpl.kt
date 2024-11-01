package com.mightysana.onewallet.auth.model.impl

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mightysana.onewallet.auth.model.AuthService
import javax.inject.Inject

class AuthServiceImpl @Inject constructor() : AuthService {
    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String) {
    }
}