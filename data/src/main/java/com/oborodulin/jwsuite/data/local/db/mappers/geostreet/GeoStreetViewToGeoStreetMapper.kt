package com.oborodulin.jwsuite.data.local.db.mappers.geostreet

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.model.GeoStreet

class GeoStreetViewToGeoStreetMapper(private val mapper: GeoLocalityViewToGeoLocalityMapper) :
    Mapper<GeoStreetView, GeoStreet> {
    override fun map(input: GeoStreetView): GeoStreet {
        val locality = mapper.map(input.locality)
        var localityDistrict: GeoLocalityDistrict? = null
        input.district?.let {
            localityDistrict = GeoLocalityDistrict(
                locality = locality,
                districtShortName = input.district.district.data.locDistrictShortName,
                districtName = input.district.district.tl.locDistrictName
            )
            localityDistrict!!.id = input.district.district.data.localityDistrictId
            localityDistrict!!.tlId = input.district.district.tl.localityDistrictsId
        }
        var microdistrict: GeoMicrodistrict? = null
        input.microdistrict?.let {
            microdistrict = GeoMicrodistrict(
                locality = locality,
                localityDistrict = localityDistrict!!,
                microdistrictType = input.microdistrict.microdistrict.data.microdistrictType,
                microdistrictShortName = input.microdistrict.microdistrict.data.microdistrictShortName,
                microdistrictName = input.microdistrict.microdistrict.tl.microdistrictName
            )
            microdistrict!!.id = input.microdistrict.microdistrict.data.microdistrictId
            microdistrict!!.tlId = input.microdistrict.microdistrict.tl.microdistrictTlId
        }
        val street = GeoStreet(
            locality = mapper.map(input.locality),
            localityDistrict = localityDistrict,
            microdistrict = microdistrict,
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