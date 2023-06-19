package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class Congregation(
    val congregationNum: String,
    val congregationName: String,
    val territoryMark: String,
    val isFavorite: Boolean = false,
    val locality: GeoLocality,
    val groups: List<Group> = emptyList(),
    val territories: List<Territory> = emptyList()
) : DomainModel()
