package com.muzz_test_felipe.database.model_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ChatMessageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "from_user_id") val fromUserId: String,
    @ColumnInfo(name = "to_user_id") val toUserId: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "viewed") val viewed: Boolean,
    @ColumnInfo(name = "date") val date: Long
) : EntityModel