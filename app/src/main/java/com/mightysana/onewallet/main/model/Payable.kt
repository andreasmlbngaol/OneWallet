package com.mightysana.onewallet.main.model

data class Payable(
    val id: String = "",
    val toUserId: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val createdAt: String = "",
    val status: String = ""
)
