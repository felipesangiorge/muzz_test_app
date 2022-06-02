package com.muzz_test_felipe

import android.content.Context
import androidx.room.Room
import com.muzz_test_felipe.database.AppDatabase

object Injection {

    private var appDatabase: AppDatabase? = null
    fun provideAppDatabase(context: Context): AppDatabase {
        return appDatabase ?: let {
            appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "urmomenzdatabase")
                .fallbackToDestructiveMigration()
                .build()
            appDatabase!!
        }
    }
}