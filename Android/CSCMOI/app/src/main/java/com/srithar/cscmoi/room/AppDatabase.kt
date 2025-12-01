package com.srithar.cscmoi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srithar.cscmoi.domain.model.Customer
import com.srithar.cscmoi.domain.model.TransactionData

@Database(entities = [Customer::class, TransactionData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun transactionDataDao(): TransactionDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
