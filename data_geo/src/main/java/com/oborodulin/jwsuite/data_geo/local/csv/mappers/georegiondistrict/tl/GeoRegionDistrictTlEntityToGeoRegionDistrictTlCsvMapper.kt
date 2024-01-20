package com.oborodulin.jwsuite.data_geo.local.csv.mappers.georegiondistrict.tl

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictTlCsv

class GeoRegionDistrictTlEntityToGeoRegionDistrictTlCsvMapper :
    Mapper<GeoRegionDistrictTlEntity, GeoRegionDistrictTlCsv> {
    override fun map(input: GeoRegionDistrictTlEntity) = GeoRegionDistrictTlCsv(
        regionDistrictTlId = input.regionDistrictTlId,
        regDistrictLocCode = input.regDistrictLocCode,
        regDistrictTlShortName = input.regDistrictTlShortName,
        regDistrictName = input.regDistrictName,
        regionDistrictsId = input.regionDistrictsId
    )
}