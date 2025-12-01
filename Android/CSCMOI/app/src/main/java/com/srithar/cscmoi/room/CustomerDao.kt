package com.srithar.cscmoi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.srithar.cscmoi.domain.model.Customer

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers order by max desc")
    suspend fun getAll(): List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(customers: List<Customer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer)

    @Query("DELETE FROM customers")
    suspend fun clearAll()
}
