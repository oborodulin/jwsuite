package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegiondistrict

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionDistrictElement
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict

class RegionDistrictElementToGeoRegionDistrictMapper(
    private val ctx: Context,
    private val mapper: GeometryToGeoCoordinatesMapper
) :
    Mapper<RegionDistrictElement, GeoRegionDistrict> {
    override fun map(input: RegionDistrictElement): GeoRegionDistrict {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.region_district_full_types)
        val resType = resArray.firstOrNull {
            input.tags.nameLoc.contains(it, true) || input.tags.name.contains(it, true)
        }
        return GeoRegionDistrict(
            region = GeoRegion().also { it.id = input.tags.regionId },
            districtShortName = input.tags.countyAbbrev.ifEmpty {
                GeoRegionDistrict.shortNameFromName(
                    prefix = input.tags.wikidata,
                    name = input.tags.name
                )
            },
            districtGeocode = input.tags.geocodeArea.ifEmpty { input.tags.name },
            districtOsmId = input.id,
            coordinates = mapper.map(input.geometry),
            districtName = input.tags.nameLoc.ifEmpty { input.tags.name }
                .replace(resType.orEmpty().toRegex(RegexOption.IGNORE_CASE), "").trim()
        )
    }
}