package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberToMemberUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoryUiMapper
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
        territoryNum = input.territoryNum,
        isBusiness = input.isBusiness,
        isGroupMinistry = input.isGroupMinistry,
        isInPerimeter = input.isInPerimeter,
        isProcessed = input.isProcessed,
        isActive = input.isActive,
        territoryDesc = input.territoryDesc,
        streetNames = input.streetNames,
        houseNums = input.houseNums,
        member = memberMapper.nullableMap(input.member),
        congregationId = input.congregationId,
        isPrivateSector = input.isPrivateSector,
        cardNum = input.cardNum,
        cardLocation = input.cardLocation,
        fullCardNum = input.fullCardNum,
        handOutDaysPeriod = input.handOutDaysPeriod,
        expiredDaysPeriod = input.expiredDaysPeriod
    )
}