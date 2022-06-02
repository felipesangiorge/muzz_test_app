package com.muzz_test_felipe.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.muzz_test_felipe.database.model_entity.UserEntity

@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    @Query("SELECT * FROM userentity")
    abstract fun getUsers(): List<UserEntity>

}