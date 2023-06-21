package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoryViewToTerritoryMapper(
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
    private val localityMapper: GeoLocalityViewToGeoLocalityMapper
) : Mapper<TerritoryView, Territory> {
    override fun map(input: TerritoryView): Territory {
        val territory = Territory(
            congregation = congregationMapper.map(input.congregation),
            territoryCategory = territoryCategoryMapper.map(input.territoryCategory),
            locality = localityMapper.map(input.locality),
            localityDistrictId = input.territory.tLocalityDistrictsId,
            districtShortName = input.localityDistrict?.districtShortName,
            microdistrictId = input.territory.tMicrodistrictsId,
            microdistrictShortName = input.microdistrict?.microdistrictShortName,
            territoryNum = input.territory.territoryNum,
            isBusiness = input.territory.isBusinessTerritory,
            isGroupMinistry = input.territory.isGroupMinistry,
            isInPerimeter = input.territory.isInPerimeter,
            isProcessed = input.territory.isProcessed,
            isActive = input.territory.isActive,
            territoryDesc = input.territory.territoryDesc
        )
        territory.id = input.territory.territoryId
        return territory
    }
}