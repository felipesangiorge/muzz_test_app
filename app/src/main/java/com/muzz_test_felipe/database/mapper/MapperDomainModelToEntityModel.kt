package com.muzz_test_felipe.database.mapper

import com.muzz_test_felipe.database.model_entity.EntityModel

interface MapperDomainModelToEntityModel
<Domain : com.muzz_test_felipe.domain.model_domain.DomainModel, DomainModel : EntityModel> {

    fun mapFromDomainToEntity(type: Domain): EntityModel
}