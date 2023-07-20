package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesIdleView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoriesIdleViewToTerritoryMapper(
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper,
    private val localityMapper: GeoLocalityViewToGeoLocalityMapper
) : Mapper<TerritoriesIdleView, Territory> {
    override fun map(input: TerritoriesIdleView): Territory {
        var territory: Territory
        with(input.territory) {
            territory = Territory(
                congregation = congregationMapper.map(this.congregation),
                territoryCategory = territoryCategoryMapper.map(this.territoryCategory),
                locality = localityMapper.map(this.locality),
                localityDistrictId = this.territory.tLocalityDistrictsId,
                districtShortName = this.localityDistrict?.locDistrictShortName,
                microdistrictId = this.territory.tMicrodistrictsId,
                microdistrictShortName = this.microdistrict?.microdistrictShortName,
                territoryNum = this.territory.territoryNum,
                isBusiness = this.territory.isBusinessTerritory,
                isGroupMinistry = this.territory.isGroupMinistry,
                isInPerimeter = this.territory.isInPerimeter,
                isProcessed = this.territory.isProcessed,
                isActive = this.territory.isActive,
                territoryDesc = this.territory.territoryDesc,
                congregationId = input.ctCongregationsId,
                isPrivateSector = input.isPrivateSector
            )
            territory.id = this.territory.territoryId
        }
        return territory
    }
}