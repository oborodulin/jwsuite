package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict.GeoLocalityDistrictViewToGeoLocalityDistrictMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict.GeoMicrodistrictViewToGeoMicrodistrictMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.model.GeoStreet

class GeoStreetViewToGeoStreetMapper(
    private val ctx: Context, private val localityMapper: GeoLocalityViewToGeoLocalityMapper,
    private val localityDistrictMapper: GeoLocalityDistrictViewToGeoLocalityDistrictMapper,
    private val microdistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper
) : Mapper<GeoStreetView, GeoStreet> {
    override fun map(input: GeoStreetView): GeoStreet {
        val street = GeoStreet(
            ctx = ctx,
            locality = localityMapper.map(input.locality),
            localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),
            microdistrict = microdistrictMapper.nullableMap(input.microdistrict),
            streetHashCode = input.street.data.streetHashCode,
            roadType = input.street.data.roadType,
            isPrivateSector = input.street.data.isStreetPrivateSector,
            estimatedHouses = input.street.data.estStreetHouses,
            streetName = input.street.tl.streetName
        )
        street.id = input.street.data.streetId
        street.tlId = input.street.tl.streetTlId
        return street
    }
}