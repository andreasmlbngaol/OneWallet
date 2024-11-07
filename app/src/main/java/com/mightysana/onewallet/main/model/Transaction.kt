package com.mightysana.onewallet.main.model

data class Transaction(
    val id: String = "",
    val walletId: String = "",
    val type: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val timestamp: Long = 0L,
    val note: String = "",
)