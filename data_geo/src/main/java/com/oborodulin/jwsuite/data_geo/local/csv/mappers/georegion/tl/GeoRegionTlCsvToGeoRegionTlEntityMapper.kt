package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegion.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionTlCsv

class GeoRegionTlCsvToGeoRegionTlEntityMapper : Mapper<GeoRegionTlCsv, GeoRegionTlEntity> {
    override fun map(input: GeoRegionTlCsv) = GeoRegionTlEntity(
        regionTlId = input.regionTlId,
        regionLocCode = input.regionLocCode,
        regionTlCode = input.regionTlCode,
        regionName = input.regionName,
        regionsId = input.regionsId
    )
}