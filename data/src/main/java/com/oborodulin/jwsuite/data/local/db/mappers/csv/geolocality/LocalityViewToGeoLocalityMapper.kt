package com.oborodulin.jwsuite.data.local.db.mappers.csv.geolocality

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView

class LocalityViewToGeoLocalityMapper(private val ctx: Context) :
    ConstructedMapper<LocalityView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv>,
    NullableConstructedMapper<LocalityView, com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv> {
    override fun map(input: LocalityView, vararg properties: Any?): com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv {
        if (properties.size != 2 || properties[0] !is com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv || (properties[1] != null && properties[1] !is com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv)) throw IllegalArgumentException(
            "LocalityViewToGeoLocalityMapper: properties size not equal 2 or properties[0] is not GeoRegionCsv class or properties[1] is not GeoRegionDistrict class: size = %d; input.data.localityId = %s".format(
                properties.size, input.data.localityId
            )
        )
        val locality = com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv(
            ctx = ctx,
            region = properties[0] as com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv,
            regionDistrict = properties[1] as? com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv,
            localityCode = input.data.localityCode,
            localityType = input.data.localityType,
            localityShortName = input.tl.localityShortName,
            localityName = input.tl.localityName
        )
        locality.id = input.data.localityId
        locality.tlId = input.tl.localityTlId
        return locality
    }

    override fun nullableMap(input: LocalityView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}