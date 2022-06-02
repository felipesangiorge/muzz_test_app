package com.muzz_test_felipe.database.mapper

import com.muzz_test_felipe.database.model_entity.UserEntity
import com.muzz_test_felipe.domain.model_domain.UserModel

object MapperUserDomainToUserEntity : MapperDomainModelToEntityModel<UserModel, UserEntity> {

    override fun mapFromDomainToEntity(type: UserModel): UserEntity = UserEntity(
        type.id,
        type.name,
        type.photoUrl
    )
}