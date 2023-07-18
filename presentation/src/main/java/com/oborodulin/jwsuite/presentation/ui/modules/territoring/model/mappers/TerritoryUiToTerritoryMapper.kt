package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryUi

class TerritoryUiToTerritoryMapper(
    private val congregationUiMapper: CongregationUiToCongregationMapper,
    private val territoryCategoryUiMapper: TerritoryCategoryUiToTerritoryCategoryMapper,
    private val localityUiMapper: LocalityUiToLocalityMapper
) : Mapper<TerritoryUi, Territory> {
    override fun map(input: TerritoryUi): Territory {
        val territory = Territory(
            congregation = congregationUiMapper.map(input.congregation),
            territoryCategory = territoryCategoryUiMapper.map(input.territoryCategory),
            locality = localityUiMapper.map(input.locality),
            localityDistrictId = input.localityDistrictId,
            districtShortName = input.districtShortName,
            microdistrictId = input.microdistrictId,
            microdistrictShortName = input.microdistrictShortName,
            territoryNum = input.territoryNum,
            isPrivateSector = input.isPrivateSector,
            isBusiness = input.isBusiness,
            isGroupMinistry = input.isGroupMinistry,
            isInPerimeter = input.isInPerimeter,
            isProcessed = input.isProcessed,
            isActive = input.isActive,
            territoryDesc = input.territoryDesc
        )
        territory.id = input.id
        return territory
    }
}