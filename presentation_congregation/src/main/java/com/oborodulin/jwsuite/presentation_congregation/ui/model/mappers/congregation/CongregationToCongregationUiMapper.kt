package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper

class CongregationToCongregationUiMapper(private val localityMapper: LocalityToLocalityUiMapper) :
    Mapper<Congregation, CongregationUi>, NullableMapper<Congregation, CongregationUi> {
    override fun map(input: Congregation) = CongregationUi(
        congregationNum = input.congregationNum,
        congregationName = input.congregationName,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        lastVisitDate = input.lastVisitDate,
        locality = localityMapper.map(input.locality)
    ).also { it.id = input.id }

    override fun nullableMap(input: Congregation?) = input?.let { map(it) }
}