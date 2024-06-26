package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.georegion

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.util.Constants.LOC_DELIMITER
import com.oborodulin.home.common.util.LogLevel
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionElement
import com.oborodulin.jwsuite.domain.model.geo.GeoCountry
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.types.RegionType
import timber.log.Timber

private const val TAG = "Data_Geo.RegionElementToGeoRegionMapper"

class RegionElementToGeoRegionMapper(
    private val ctx: Context,
    private val mapper: GeometryToGeoCoordinatesMapper
) : Mapper<RegionElement, GeoRegion> {
    override fun map(input: RegionElement): GeoRegion {
        if (LogLevel.LOG_API_MAPPER) {
            Timber.tag(TAG).d("map(...) called: input = %s", input)
        }
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.region_full_types)
        val resType = resArray.firstOrNull {
            input.tags.officialStatus.contains(it, true) ||
                    input.tags.nameLoc.contains(it, true) || // RegionType.REGION
                    input.tags.officialName.contains(it, true) ||
                    input.tags.officialNameLoc.contains(it, true)
        }
        val regionName = input.tags.nameLoc.ifBlank { input.tags.name }
        if (LogLevel.LOG_API_MAPPER) {
            Timber.tag(TAG).d("regionName = %s", regionName)
        }
        return GeoRegion(
            country = GeoCountry().also { it.id = input.tags.countryId },
            regionCode = input.tags.isoCode.substringAfter(LOC_DELIMITER)
                .ifBlank { input.tags.cadasterCode.ifBlank { input.tags.shortNameLoc.ifBlank { input.tags.refLoc.ifBlank { input.tags.gost.ifBlank { input.tags.shortName.ifBlank { input.tags.ref } } } } } },
            regionType = resType?.let { RegionType.entries[resArray.indexOf(it)] }
                ?: RegionType.REGION,
            isRegionTypePrefix = resType?.let { regionName.startsWith(it, true) } ?: false,
            regionGeocode = input.tags.geocodeArea.ifBlank { input.tags.name },
            regionOsmId = input.id,
            coordinates = mapper.map(input.geometry),
            regionName = regionName.replace(resType.orEmpty().toRegex(RegexOption.IGNORE_CASE), "")
                .trim()
        )
    }
}