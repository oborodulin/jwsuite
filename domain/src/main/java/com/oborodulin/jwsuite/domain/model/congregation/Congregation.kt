package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.territory.Territory
import java.time.OffsetDateTime

data class Congregation(
    val locality: GeoLocality,
    val congregationNum: String,
    val congregationName: String,
    val territoryMark: String,
    val isFavorite: Boolean = false,
    val lastVisitDate: OffsetDateTime? = null,
    val groups: List<Group> = emptyList(),
    val territories: List<Territory> = emptyList()
) : DomainModel()
