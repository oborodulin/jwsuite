package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.model.Floor
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Room
import com.oborodulin.jwsuite.domain.model.Territory

class RoomEntityToRoomMapper(private val ctx: Context) : ConstructedMapper<RoomEntity, Room> {
    override fun map(input: RoomEntity, vararg properties: Any?): Room {
        if (properties.isEmpty() || properties.size < 4 ||
            properties[0] !is House || (properties[1] != null && properties[1] !is Entrance) ||
            (properties[2] != null && properties[2] !is Floor) || (properties[3] != null && properties[3] !is Territory)
        ) throw IllegalArgumentException(
            "RoomEntityToRoomMapper: properties is empty or properties size less then 4 or properties[0] is not House class or properties[1] is not Entrance class " +
                    "or properties[2] is not Floor class or properties[3] is not Territory class: size = %d; input.roomId = %s".format(
                        properties.size, input.roomId
                    )
        )
        val room = Room(
            ctx = ctx,
            house = properties[0] as House,
            entrance = properties[1] as? Entrance,
            floor = properties[2] as? Floor,
            territory = properties[3] as? Territory,
            roomNum = input.roomNum,
            isIntercom = input.isIntercomRoom,
            isResidential = input.isResidentialRoom,
            isForeignLanguage = input.isForeignLangRoom,
            territoryDesc = input.roomDesc
        )
        room.id = input.roomId
        return room
    }
}