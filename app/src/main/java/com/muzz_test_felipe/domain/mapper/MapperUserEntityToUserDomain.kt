package com.muzz_test_felipe.domain.mapper

import com.muzz_test_felipe.database.model_entity.UserEntity
import com.muzz_test_felipe.domain.model_domain.UserModel

object MapperUserEntityToUserDomain : MapperEntityModelToDomainModel<UserEntity, UserModel> {

    override fun mapFromEntityToDomain(type: UserEntity) = UserModel(
        type.id,
        type.userName,
        type.userPictureUrl
    )
}