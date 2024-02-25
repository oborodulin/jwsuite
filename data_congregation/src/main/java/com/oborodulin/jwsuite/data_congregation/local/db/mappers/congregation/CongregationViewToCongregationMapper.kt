package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.domain.model.congregation.Congregation

class CongregationViewToCongregationMapper(
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val congregationMapper: CongregationEntityToCongregationMapper
) : Mapper<CongregationView, Congregation>, NullableMapper<CongregationView, Congregation> {
    override fun map(input: CongregationView) =
        congregationMapper.map(input.congregation, localityMapper.map(input.locality))

    override fun nullableMap(input: CongregationView?) = input?.let { map(it) }
}