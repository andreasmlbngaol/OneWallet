package com.mightysana.onewallet.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
): ViewModel() {

    private val _wallets = MutableStateFlow<List<Wallet>>(emptyList())
    val wallets: StateFlow<List<Wallet>> = _wallets

    fun addWallet(name: String, balance: Int) {
        val newWallet = Wallet(name = name, balance = balance)
        _wallets.value = _wallets.value + newWallet
    }

    fun saveWalletsToDatabase(onComplete: () -> Unit) {
        val currentUser = firebaseAuth.currentUser ?: return
        val userRef = database.getReference("users").child(currentUser.uid)

        val walletsMap = _wallets.value.associate { it.name to it.balance }

        userRef.child("wallets").setValue(walletsMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete()
            } else {
                Log.e("RegisterViewModel", "Error saving wallets: ${task.exception?.message}")
            }
        }
    }

}

data class Wallet(
    val name: String = "",
    val balance: Int = 0
)