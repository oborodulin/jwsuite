package com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import java.util.UUID

class EntranceToEntranceEntityMapper : Mapper<Entrance, EntranceEntity> {
    override fun map(input: Entrance) = EntranceEntity(
        entranceId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        entranceNum = input.entranceNum,
        isSecurityEntrance = input.isSecurity,
        isIntercomEntrance = input.isIntercom,
        isResidentialEntrance = input.isResidential,
        entranceFloorsQty = input.floorsQty,
        roomsByEntranceFloor = input.roomsByFloor,
        estEntranceRooms = input.estimatedRooms,
        entranceDesc = input.entranceDesc,
        eTerritoriesId = input.territory?.id,
        eHousesId = input.house.id!!
    )
}