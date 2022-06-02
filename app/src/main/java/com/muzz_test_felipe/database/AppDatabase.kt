package com.muzz_test_felipe.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muzz_test_felipe.database.dao.UserDao
import com.muzz_test_felipe.database.model_entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}