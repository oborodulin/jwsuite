package com.oborodulin.jwsuite.data_geo.local.csv.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv

class GeoLocalityEntityToGeoLocalityCsvMapper : Mapper<GeoLocalityEntity, GeoLocalityCsv> {
    override fun map(input: GeoLocalityEntity) = GeoLocalityCsv(
        localityId = input.localityId,
        localityCode = input.localityCode,
        localityType = input.localityType,
        localityOsmId = input.localityOsmId,
        latitude = input.coordinates.latitude,
        longitude = input.coordinates.longitude,
        lRegionDistrictsId = input.lRegionDistrictsId,
        lRegionsId = input.lRegionsId
    )
}