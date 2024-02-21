package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.pojo.Coordinates
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv

class GeoRegionCsvToGeoRegionEntityMapper : Mapper<GeoRegionCsv, GeoRegionEntity> {
    override fun map(input: GeoRegionCsv) = GeoRegionEntity(
        regionId = input.regionId,
        regionCode = input.regionCode,
        regionOsmId = input.regionOsmId,
        coordinates = input.latitude?.let { latitude ->
            input.longitude?.let { longitude ->
                Coordinates(latitude = latitude, longitude = longitude)
            }
        }
    )
}