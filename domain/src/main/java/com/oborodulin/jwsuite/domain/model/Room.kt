package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class Room(
    val house: House,
    val entranceId: UUID? = null,
    val floorId: UUID? = null,
    val territory: Territory? = null,
    val roomNum: Int,
    val isIntercom: Boolean? = null,
    val isResidential: Boolean = true,
    val isForeignLanguage: Boolean = false,
    val territoryDesc: String? = null
) : DomainModel()
