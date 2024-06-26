package com.oborodulin.jwsuite.data_geo.remote.osm.mappers.geolocality

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.util.Constants.RES_DELIMITER
import com.oborodulin.jwsuite.data_geo.remote.osm.mappers.GeometryToGeoCoordinatesMapper
import com.oborodulin.jwsuite.data_geo.remote.osm.model.LocalityElement
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.types.LocalityType

class LocalityElementToGeoLocalityMapper(
    private val ctx: Context,
    private val mapper: GeometryToGeoCoordinatesMapper
) : Mapper<LocalityElement, GeoLocality> {
    override fun map(input: LocalityElement): GeoLocality {
        val resArray =
            ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.locality_full_types)
        val type = LocalityType.valueOf(input.tags.place.uppercase())
        val resType = resArray.firstOrNull { res ->
            res.splitToSequence(RES_DELIMITER).any {
                input.tags.officialStatus.contains(it, true) ||
                        input.tags.prefix.contains(it, true)
            }
        }
        return GeoLocality(
            region = GeoRegion().also { it.id = input.tags.regionId },
            regionDistrict = input.tags.regionDistrictId?.let { rdId ->
                GeoRegionDistrict().also { it.id = rdId }
            },
            localityCode = input.tags.postalCode.ifBlank { input.tags.wikidata.ifBlank { input.tags.gnisId } },
            localityType = resType?.let { LocalityType.entries[resArray.indexOf(it)] } ?: type,
            localityShortName = GeoLocality.shortNameFromName(
                prefix = input.tags.wikidata,
                name = input.tags.name
            ),
            localityGeocode = input.tags.geocodeArea.ifBlank { input.tags.name },
            localityOsmId = input.id,
            coordinates = mapper.map(input.geometry),
            localityName = input.tags.nameLoc.ifBlank { input.tags.name }
        )
    }
}