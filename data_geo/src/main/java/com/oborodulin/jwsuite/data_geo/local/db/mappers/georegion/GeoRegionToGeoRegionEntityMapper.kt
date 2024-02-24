package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.mappers.GeoCoordinatesToCoordinatesMapper
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import java.util.UUID

class GeoRegionToGeoRegionEntityMapper(private val mapper: GeoCoordinatesToCoordinatesMapper) :
    Mapper<GeoRegion, GeoRegionEntity> {
    override fun map(input: GeoRegion) = GeoRegionEntity(
        regionId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        regionCode = input.regionCode,
        regionGeocode = input.regionGeocode,
        regionOsmId = input.regionOsmId,
        coordinates = mapper.map(input.coordinates),
        rCountriesId = input.country?.id!!
    )
}