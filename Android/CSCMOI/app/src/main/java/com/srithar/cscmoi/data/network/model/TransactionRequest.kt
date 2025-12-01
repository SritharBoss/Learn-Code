package com.srithar.cscmoi.data.network.model

data class TransactionRequest(
    val id: String,
    val type: String,
    val date: String,
    val amount: String,
    val note: String,
    val customer_id: Int
)
