package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryUi

class TerritoryToTerritoryUiMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
    private val localityMapper: LocalityToLocalityUiMapper
) : Mapper<Territory, TerritoryUi> {
    override fun map(input: Territory): TerritoryUi {
        val territoryUi = TerritoryUi(
            congregation = congregationMapper.map(input.congregation),
            territoryCategory = territoryCategoryMapper.map(input.territoryCategory),
            locality = localityMapper.map(input.locality),
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
        territoryUi.id = input.id
        return territoryUi
    }
}