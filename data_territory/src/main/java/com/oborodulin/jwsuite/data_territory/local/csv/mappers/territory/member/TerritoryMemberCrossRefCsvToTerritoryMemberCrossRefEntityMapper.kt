package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberCrossRefCsv

class TerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper :
    Mapper<TerritoryMemberCrossRefCsv, TerritoryMemberCrossRefEntity> {
    override fun map(input: TerritoryMemberCrossRefCsv) = TerritoryMemberCrossRefEntity(
        territoryMemberId = input.territoryMemberId,
        receivingDate = input.receivingDate,
        deliveryDate = input.deliveryDate,
        tmcTerritoriesId = input.tmcTerritoriesId,
        tmcMembersId = input.tmcMembersId
    )
}