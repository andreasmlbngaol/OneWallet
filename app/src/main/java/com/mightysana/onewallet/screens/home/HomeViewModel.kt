package com.mightysana.onewallet.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
): ViewModel() {
    private val _isUserRegistered = MutableStateFlow(false)
    val isUserRegistered = _isUserRegistered

    init {
        checkUserRegistration()
    }

    private fun checkUserRegistration() {
        val currentUser = auth.currentUser ?: return

        val userRef = database.getReference("users").child(currentUser.uid)
        userRef.get().addOnSuccessListener { snapshot ->
            Log.d("HomeViewModel", "checkUserRegistration1: ${snapshot.exists()}")
            _isUserRegistered.value = snapshot.exists()
        }.addOnFailureListener {
            _isUserRegistered.value = false
        }
        Log.d("HomeViewModel", "checkUserRegistration2: ${_isUserRegistered.value}")
    }
}