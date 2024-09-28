package com.mightysana.onewallet.screens.login

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mightysana.onewallet.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
): ViewModel() {
    private val userRef = database.getReference("users")

    fun onSignInWithGoogle(credential: Credential, onUserCheck: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                accountService.signInWithGoogle(googleIdTokenCredential.idToken)
            }

            val currentUser = auth.currentUser
            currentUser?.let { user ->
                checkIfUserExists(user.uid, onUserCheck)
            }
        }
    }

    private fun checkIfUserExists(userId: String, onUserCheck: (Boolean) -> Unit) {
        userRef.child(userId).get().addOnSuccessListener { snapshot ->
            val isNewUser = !snapshot.exists()
            onUserCheck(isNewUser)
        }.addOnFailureListener { e ->
            Log.e("LoginViewModel", "Error checking user existence: ${e.message}")
        }
    }
}