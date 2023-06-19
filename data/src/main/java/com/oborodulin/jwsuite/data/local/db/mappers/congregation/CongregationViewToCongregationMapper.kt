package com.oborodulin.jwsuite.data.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.views.CongregationView
import com.oborodulin.jwsuite.domain.model.Congregation

class CongregationViewToCongregationMapper(private val localityMapper: GeoLocalityViewToGeoLocalityMapper) :
    Mapper<CongregationView, Congregation> {
    override fun map(input: CongregationView): Congregation {
        val congregation = Congregation(
            congregationNum = input.congregation.congregationNum,
            congregationName = input.congregation.congregationName,
            territoryMark = input.congregation.territoryMark,
            isFavorite = input.congregation.isFavorite,
            locality = localityMapper.map(input.locality),
        )
        congregation.id = input.congregation.congregationId
        return congregation
    }
}