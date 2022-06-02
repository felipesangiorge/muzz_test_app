package com.muzz_test_felipe

import android.content.Context
import androidx.room.Room
import com.muzz_test_felipe.core.AppExecutors
import com.muzz_test_felipe.data.UserRepository
import com.muzz_test_felipe.database.AppDatabase

object Injection {

    private var appDatabase: AppDatabase? = null
    fun provideAppDatabase(context: Context): AppDatabase {
        return appDatabase ?: let {
            appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "muzzdatabase")
                .fallbackToDestructiveMigration()
                .build()
            appDatabase!!
        }
    }

    private var appExcutors: AppExecutors? = null
    fun provideAppExecutors() = appExcutors ?: let {
        appExcutors = AppExecutors()
        appExcutors!!
    }

    private var userRepository: UserRepository? = null
    fun provideUserRepository(context: Context) = userRepository ?: let {
        userRepository = UserRepository(
            provideAppDatabase(context),
            provideAppDatabase(context).userDao(),
            provideAppDatabase(context).chatMessageDao(),
            provideAppExecutors()
        )
        userRepository!!
    }
}