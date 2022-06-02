package com.muzz_test_felipe.database.model_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id") val id: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "user_pictureUrl") val userPictureUrl: String?
) : EntityModel