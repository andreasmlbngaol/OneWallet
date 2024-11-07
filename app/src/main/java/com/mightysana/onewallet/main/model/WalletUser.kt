package com.mightysana.onewallet.main.model

data class WalletUser(
    val wallets: List<Wallet> = emptyList(),
    val payables: List<Payable> = emptyList(),
    val receivables: List<Receivable> = emptyList(),
    val transactions: List<Transaction> = emptyList()
)
