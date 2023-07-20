package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberToMemberUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import java.util.UUID

class TerritoryToTerritoriesListItemMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
    private val localityMapper: LocalityToLocalityUiMapper,
    private val memberMapper: MemberToMemberUiMapper
) : Mapper<Territory, TerritoriesListItem> {
    override fun map(input: Territory) = TerritoriesListItem(
        id = input.id ?: UUID.randomUUID(),
        congregation = congregationMapper.map(input.congregation),
        territoryCategory = territoryCategoryMapper.map(input.territoryCategory),
        locality = localityMapper.map(input.locality),
        cardNum = input.cardNum,
        cardLocation = input.cardLocation,
        territoryNum = input.territoryNum,
        isBusiness = input.isBusiness,
        isGroupMinistry = input.isGroupMinistry,
        isInPerimeter = input.isInPerimeter,
        isProcessed = input.isProcessed,
        isActive = input.isActive,
        territoryDesc = input.territoryDesc,
        member = memberMapper.nullableMap(input.member),
        congregationId = input.congregationId,
        isPrivateSector = input.isPrivateSector,
        expiredDays = input.expiredDays
    )
}