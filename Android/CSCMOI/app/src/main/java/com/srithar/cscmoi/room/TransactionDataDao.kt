package com.srithar.cscmoi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.srithar.cscmoi.domain.model.TransactionData

@Dao
interface TransactionDataDao {
    @Query("SELECT * FROM transaction_data")
    suspend fun getAll(): List<TransactionData>

    @Query("SELECT * FROM transaction_data WHERE customer_id = :customerId")
    suspend fun getTransactionsForCustomer(customerId: Int): List<TransactionData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactionData: List<TransactionData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionData: TransactionData)

    @Query("DELETE FROM transaction_data")
    suspend fun clearAll()
}
