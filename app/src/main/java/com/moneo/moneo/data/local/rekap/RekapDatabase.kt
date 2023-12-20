package com.moneo.moneo.data.local.rekap

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moneo.moneo.data.local.rekening.RekeningDao
import com.moneo.moneo.data.local.rekening.RekeningDatabase

@Database(entities = [Rekap::class], version = 1, exportSchema = false)
abstract class RekapDatabase : RoomDatabase() {

    abstract fun rekapDao(): RekapDao

    companion object {

        @Volatile
        private var INSTANCE: RekapDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): RekapDatabase {
            if (INSTANCE == null) {
                synchronized(RekapDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RekapDatabase::class.java, "rekap_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as RekapDatabase
        }
    }

}