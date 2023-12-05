package com.moneo.moneo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {

        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TransactionDatabase {
            if (INSTANCE == null) {
                synchronized(TransactionDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TransactionDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as TransactionDatabase
        }
    }
}