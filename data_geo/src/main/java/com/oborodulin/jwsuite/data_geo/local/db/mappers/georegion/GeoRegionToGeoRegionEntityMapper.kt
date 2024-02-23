package com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import java.util.UUID

class GeoRegionToGeoRegionEntityMapper : Mapper<GeoRegion, GeoRegionEntity> {
    override fun map(input: GeoRegion) = GeoRegionEntity(
        regionId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        regionCode = input.regionCode,
        regionGeocode = input.regionGeocode,
        regionOsmId = input.regionOsmId,
        coordinates = Coordinates.fromLatAndLon(lat = input.latitude, lon = input.longitude),
        rCountriesId = input.country.id!!
    )
}