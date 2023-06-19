package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoryViewToTerritoryMapper(
    private val congregationMapper: CongregationViewToCongregationMapper,
    private val territoryCategoryMapper: TerritoryCategoryEntityToTerritoryCategoryMapper
) : Mapper<TerritoryView, Territory> {
    override fun map(input: TerritoryView): Territory {
        val territory = Territory(
            congregation = congregationMapper.map(input.congregation),
            territoryCategory = territoryCategoryMapper.map(input.territoryCategory),
            territoryNum = input.territory.territoryNum,
            isBusiness = input.territory.isBusiness,
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