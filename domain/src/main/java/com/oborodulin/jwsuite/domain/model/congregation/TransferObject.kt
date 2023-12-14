package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.serialization.Serializable

@Serializable
data class TransferObject(
    val transferObjectType: TransferObjectType = TransferObjectType.ALL,
    val transferObjectName: String
) : DomainModel()
