package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationListItem
import java.util.UUID

class CongregationToCongregationListItemMapper(private val localityMapper: LocalityToLocalityUiMapper) :
    Mapper<Congregation, CongregationListItem> {
    override fun map(input: Congregation) = CongregationListItem(
        id = input.id ?: UUID.randomUUID(),
        congregationName = input.congregationName,
        congregationNum = input.congregationNum,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        locality = localityMapper.map(input.locality)
    )
}