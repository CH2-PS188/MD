package com.moneo.moneo.data.local.rekening

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Rekening::class], version = 1, exportSchema = false)
abstract class RekeningDatabase : RoomDatabase() {

    abstract fun rekeningDao(): RekeningDao

    companion object {

        @Volatile
        private var INSTANCE: RekeningDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): RekeningDatabase {
            if (INSTANCE == null) {
                synchronized(RekeningDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RekeningDatabase::class.java, "rekening_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as RekeningDatabase
        }
    }
}