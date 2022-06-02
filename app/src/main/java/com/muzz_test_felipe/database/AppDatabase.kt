package com.muzz_test_felipe.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muzz_test_felipe.database.dao.ChatMessageDao
import com.muzz_test_felipe.database.dao.UserDao
import com.muzz_test_felipe.database.model_entity.ChatMessageEntity
import com.muzz_test_felipe.database.model_entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ChatMessageEntity::class
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatMessageDao(): ChatMessageDao
}