package com.srithar.cscmoi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_data")
data class TransactionData(
    @PrimaryKey
    val id: String,
    val customer_id: String,
    val type: String,
    val date: String,
    val amount: Int,
    val note: String,
    var created_date: String
)