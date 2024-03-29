package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality

class CongregationEntityToCongregationMapper :
    ConstructedMapper<CongregationEntity, Congregation>,
    NullableConstructedMapper<CongregationEntity, Congregation> {
    override fun map(input: CongregationEntity, vararg properties: Any?): Congregation {
        if (properties.size != 1 || properties[0] !is GeoLocality) throw IllegalArgumentException(
            "CongregationEntityToCongregationMapper: properties size not equal 1 or properties[0] is not GeoLocality class: size = %d; input.congregationId = %s".format(
                properties.size, input.congregationId
            )
        )
        return Congregation(
            congregationNum = input.congregationNum,
            congregationName = input.congregationName,
            territoryMark = input.territoryMark,
            isFavorite = input.isFavorite,
            lastVisitDate = input.lastVisitDate,
            locality = properties[0] as GeoLocality
        ).also { it.id = input.congregationId }
    }

    override fun nullableMap(input: CongregationEntity?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}