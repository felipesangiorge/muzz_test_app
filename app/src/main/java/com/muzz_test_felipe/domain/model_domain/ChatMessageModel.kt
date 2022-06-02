package com.muzz_test_felipe.domain.model_domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatMessageModel(
    val id: String,
    val fromUser: String,
    val toUser: String,
    val message: String,
    val viewed: Boolean,
    val date: Long
) : DomainModel, Parcelable