package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv

class GeoLocalityCsvToGeoLocalityEntityMapper : Mapper<GeoLocalityCsv, GeoLocalityEntity> {
    override fun map(input: GeoLocalityCsv) = GeoLocalityEntity(
        localityId = input.localityId,
        localityCode = input.localityCode,
        localityType = input.localityType,
        lRegionDistrictsId = input.lRegionDistrictsId,
        lRegionsId = input.lRegionsId
    )
}