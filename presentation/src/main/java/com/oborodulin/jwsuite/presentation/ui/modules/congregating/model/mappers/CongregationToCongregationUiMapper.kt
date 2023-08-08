package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityToLocalityUiMapper

class CongregationToCongregationUiMapper(private val localityMapper: LocalityToLocalityUiMapper) :
    Mapper<Congregation, CongregationUi>,
    NullableMapper<Congregation, CongregationUi> {
    override fun map(input: Congregation): CongregationUi {
        val congregationUi = CongregationUi(
            congregationNum = input.congregationNum,
            congregationName = input.congregationName,
            territoryMark = input.territoryMark,
            isFavorite = input.isFavorite,
            locality = localityMapper.map(input.locality)
        )
        congregationUi.id = input.id
        return congregationUi
    }

    override fun nullableMap(input: Congregation?) = input?.let { map(it) }
}