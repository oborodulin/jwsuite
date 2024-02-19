package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper

class CongregationUiToCongregationMapper(private val localityUiMapper: LocalityUiToLocalityMapper) :
    Mapper<CongregationUi, Congregation> {
    override fun map(input: CongregationUi) = Congregation(
        congregationNum = input.congregationNum,
        congregationName = input.congregationName,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        lastVisitDate = input.lastVisitDate,
        locality = localityUiMapper.map(input.locality),
    ).also { it.id = input.id }
}