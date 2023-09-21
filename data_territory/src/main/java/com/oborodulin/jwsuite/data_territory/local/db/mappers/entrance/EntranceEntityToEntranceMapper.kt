package com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Territory

class EntranceEntityToEntranceMapper(private val ctx: Context) :
    ConstructedMapper<EntranceEntity, Entrance>,
    NullableConstructedMapper<EntranceEntity, Entrance> {
    override fun map(input: EntranceEntity, vararg properties: Any?): Entrance {
        if (properties.isEmpty() || properties.size < 2 || properties[0] !is House || (properties[1] != null && properties[1] !is Territory)) throw IllegalArgumentException(
            "EntranceEntityToEntranceMapper: properties is empty or properties size less then 2 or properties[0] is not Territory class: size = %d; input.entranceId = %s".format(
                properties.size, input.entranceId
            )
        )
        val entrance = Entrance(
            ctx = ctx,
            house = properties[0] as House,
            territory = properties[1] as? Territory,
            entranceNum = input.entranceNum,
            isSecurity = input.isSecurityEntrance,
            isIntercom = input.isIntercomEntrance,
            isResidential = input.isResidentialEntrance,
            floorsQty = input.entranceFloorsQty,
            roomsByFloor = input.roomsByEntranceFloor,
            estimatedRooms = input.estEntranceRooms,
            entranceDesc = input.entranceDesc,
        )
        entrance.id = input.entranceId
        return entrance
    }

    override fun nullableMap(input: EntranceEntity?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}