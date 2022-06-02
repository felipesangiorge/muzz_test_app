package com.muzz_test_felipe.database.mapper

import com.muzz_test_felipe.database.model_entity.ChatMessageEntity
import com.muzz_test_felipe.domain.model_domain.ChatMessageModel

object MapperChatMessageDomainToChatMessageEntity : MapperDomainModelToEntityModel<ChatMessageModel, ChatMessageEntity> {

    override fun mapFromDomainToEntity(type: ChatMessageModel): ChatMessageEntity = ChatMessageEntity(
        type.id,
        type.fromUser,
        type.toUser,
        type.message,
        type.viewed,
        type.date
    )
}