package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityUiToLocalityMapper

class CongregationUiToCongregationMapper(private val localityUiMapper: LocalityUiToLocalityMapper) :
    Mapper<CongregationUi, Congregation> {
    override fun map(input: CongregationUi): Congregation {
        val congregation = Congregation(
            congregationNum = input.congregationNum,
            congregationName = input.congregationName,
            territoryMark = input.territoryMark,
            isFavorite = input.isFavorite,
            locality = localityUiMapper.map(input.locality),
        )
        congregation.id = input.id
        return congregation
    }
}