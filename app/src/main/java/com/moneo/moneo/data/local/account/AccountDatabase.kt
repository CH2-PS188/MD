package com.moneo.moneo.data.local.account

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.moneo.moneo.data.local.transaction.TransactionDao
import com.moneo.moneo.data.local.transaction.TransactionDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Account::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {

        @Volatile
        private var INSTANCE: AccountDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AccountDatabase {
            if (INSTANCE == null) {
                synchronized(AccountDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AccountDatabase::class.java, "note_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as AccountDatabase
        }
    }
}