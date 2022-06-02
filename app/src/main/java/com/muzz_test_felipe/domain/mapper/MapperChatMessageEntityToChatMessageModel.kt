package com.muzz_test_felipe.domain.mapper

import com.muzz_test_felipe.database.model_entity.ChatMessageEntity
import com.muzz_test_felipe.domain.model_domain.ChatMessageModel

object MapperChatMessageEntityToChatMessageModel : MapperEntityModelToDomainModel<ChatMessageEntity, ChatMessageModel> {

    override fun mapFromEntityToDomain(type: ChatMessageEntity) = ChatMessageModel(
        type.id,
        type.fromUserId,
        type.toUserId,
        type.message,
        type.viewed,
        type.date
    )
}