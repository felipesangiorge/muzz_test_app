package com.muzz_test_felipe.domain.mapper

import com.muzz_test_felipe.database.model_entity.EntityModel
import com.muzz_test_felipe.domain.model_domain.DomainModel

interface MapperEntityModelToDomainModel
<Entity : EntityModel, Domain : com.muzz_test_felipe.domain.model_domain.DomainModel> {

    fun mapFromEntityToDomain(type: Entity): DomainModel
}