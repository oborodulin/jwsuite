package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv

class GeoRegionEntityToGeoRegionCsvMapper : Mapper<GeoRegionEntity, GeoRegionCsv> {
    override fun map(input: GeoRegionEntity) = GeoRegionCsv(
        regionId = input.regionId,
        regionCode = input.regionCode,
        regionGeocode = input.regionGeocode,
        regionOsmId = input.regionOsmId,
        latitude = input.coordinates.latitude,
        longitude = input.coordinates.longitude,
        rCountriesId = input.rCountriesId
    )
}