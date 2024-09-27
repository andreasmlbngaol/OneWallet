package com.mightysana.onewallet.model.service

interface AccountService {
    suspend fun signInWithGoogle(idToken: String)
}