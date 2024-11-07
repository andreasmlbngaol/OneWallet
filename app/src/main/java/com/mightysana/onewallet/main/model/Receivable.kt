package com.mightysana.onewallet.main.model

data class Receivable(
    val id: String,
    val fromUserId: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val createdAt: String = "",
    val status: String = ""
)
