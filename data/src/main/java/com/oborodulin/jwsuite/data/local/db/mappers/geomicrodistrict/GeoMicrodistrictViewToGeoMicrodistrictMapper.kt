package com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class GeoMicrodistrictViewToGeoMicrodistrictMapper(private val mapper: GeoLocalityViewToGeoLocalityMapper) :
    Mapper<GeoMicrodistrictView, GeoMicrodistrict> {
    override fun map(input: GeoMicrodistrictView): GeoMicrodistrict {
        val locality = mapper.map(input.locality)
        val localityDistrict = GeoLocalityDistrict(
            locality = locality,
            districtShortName = input.district.data.locDistrictShortName,
            districtName = input.district.tl.locDistrictName
        )
        localityDistrict.id = input.district.data.localityDistrictId
        localityDistrict.tlId = input.district.tl.localityDistrictsId
        val microdistrict = GeoMicrodistrict(
            locality = locality,
            localityDistrict = localityDistrict,
            microdistrictType = input.microdistrict.data.microdistrictType,
            microdistrictShortName = input.microdistrict.data.microdistrictShortName,
            microdistrictName = input.microdistrict.tl.microdistrictName
        )
        microdistrict.id = input.microdistrict.data.microdistrictId
        microdistrict.tlId = input.microdistrict.tl.microdistrictTlId
        return microdistrict
    }
}