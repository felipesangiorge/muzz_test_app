package com.muzz_test_felipe.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.muzz_test_felipe.database.model_entity.ChatMessageEntity

@Dao
abstract class ChatMessageDao : BaseDao<ChatMessageEntity>() {

    @Query("SELECT * FROM chatmessageentity")
    abstract fun getMessages(): List<ChatMessageEntity>


    @Query(
        """SELECT * FROM chatmessageentity
        WHERE from_user_id = :selectedUserId 
        AND to_user_id = :toUserId
        OR to_user_id = :selectedUserId
        AND from_user_id = :toUserId"""
    )
    abstract fun getChatMessage(selectedUserId: String, toUserId: String): List<ChatMessageEntity>


    @Query(
        """
        UPDATE OR ABORT 
            `chatmessageentity` 
        SET `viewed` = :viewedMessage
        WHERE from_user_id = :toUserId 
        AND to_user_id = :selectedUserId"""
    )
    abstract fun updateMessageVisibility(viewedMessage: Boolean, selectedUserId: String, toUserId: String)
}
