package com.oborodulin.jwsuite.data.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.CongregationView
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class CongregationViewToCongregationMapper : Mapper<CongregationView, Congregation> {
    override fun map(input: CongregationView): Congregation {
        val region = GeoRegion(
            regionCode = input.region.data.regionCode,
            regionName = input.region.tl.regionName
        )
        region.id = input.region.data.regionId
        region.tlId = input.region.tl.regionTlId

        var regionDistrict: GeoRegionDistrict? = null
        input.district?.let {
            regionDistrict = GeoRegionDistrict(
                region = region,
                districtShortName = it.data.regDistrictShortName,
                districtName = it.tl.regDistrictName
            )
            regionDistrict!!.id = it.data.regionDistrictId
            regionDistrict!!.tlId = it.tl.regionDistrictTlId
        }
        val locality = GeoLocality(
            region = region,
            regionDistrict = regionDistrict,
            localityCode = input.locality.data.localityCode,
            localityType = input.locality.data.localityType,
            localityShortName = input.locality.tl.localityShortName,
            localityName = input.locality.tl.localityName
        )
        locality.id = input.locality.data.localityId
        locality.tlId = input.locality.tl.localityTlId
        val congregation = Congregation(
            congregationNum = input.congregation.congregationNum,
            congregationName = input.congregation.congregationName,
            territoryMark = input.congregation.territoryMark,
            isFavorite = input.congregation.isFavorite,
            locality = locality,
        )
        congregation.id = input.congregation.congregationId
        return congregation
    }
}