package com.mightysana.onewallet.oneproject.model

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

const val MAX_FORM_WIDTH = 500
const val USERS_REF = "users"
const val ONE_WALLET_REF = "one_wallet"
val currentUser = Firebase.auth.currentUser
