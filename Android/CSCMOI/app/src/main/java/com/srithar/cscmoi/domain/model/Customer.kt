package com.srithar.cscmoi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey val id:Int,
    val page_no: Int,
    val village: String,
    val tr_village:String?,
    val first_name: String,
    val tr_first_name:String?,
    val last_name: String,
    val note: String="",
    val amount: Int,
    val max:Int
)


data class CustomerRequest(
    val id:Int,
    val page_no: String,
    val village: String,
    val tr_village:String?,
    val first_name: String,
    val tr_first_name:String?,
    val last_name: String,
    val note: String="",
)
