package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityToLocalityUiMapper
import java.util.UUID

class CongregationToCongregationsListItemMapper(private val localityMapper: LocalityToLocalityUiMapper) :
    Mapper<Congregation, CongregationsListItem> {
    override fun map(input: Congregation) = CongregationsListItem(
        id = input.id ?: UUID.randomUUID(),
        congregationName = input.congregationName,
        congregationNum = input.congregationNum,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        locality = localityMapper.map(input.locality)
    )
}