package com.oborodulin.jwsuite.data_territory.local.csv.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.EntranceCsv

class EntranceEntityToEntranceCsvMapper : Mapper<EntranceEntity, EntranceCsv> {
    override fun map(input: EntranceEntity) = EntranceCsv(
        entranceId = input.entranceId,
        entranceNum = input.entranceNum,
        isSecurityEntrance = input.isSecurityEntrance,
        isIntercomEntrance = input.isIntercomEntrance,
        isResidentialEntrance = input.isResidentialEntrance,
        entranceFloorsQty = input.entranceFloorsQty,
        roomsByEntranceFloor = input.roomsByEntranceFloor,
        estEntranceRooms = input.estEntranceRooms,
        entranceDesc = input.entranceDesc,
        eTerritoriesId = input.eTerritoriesId,
        eHousesId = input.eHousesId
    )
}