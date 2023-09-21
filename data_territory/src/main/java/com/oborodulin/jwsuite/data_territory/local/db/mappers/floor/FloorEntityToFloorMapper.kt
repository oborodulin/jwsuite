package com.oborodulin.jwsuite.data_territory.local.db.mappers.floor

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.Entrance
import com.oborodulin.jwsuite.domain.model.Floor
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.domain.model.Territory

class FloorEntityToFloorMapper(private val ctx: Context) : ConstructedMapper<FloorEntity, Floor>,
    NullableConstructedMapper<FloorEntity, Floor> {
    override fun map(input: FloorEntity, vararg properties: Any?): Floor {
        if (properties.isEmpty() || properties.size < 3 || properties[0] !is House || (properties[1] != null && properties[1] !is Entrance) ||
            (properties[2] != null && properties[2] !is Territory)
        ) throw IllegalArgumentException(
            "FloorEntityToFloorMapper: properties is empty or properties size less then 3 or properties[0] is not House class or properties[1] is not Entrance class " +
                    "or properties[2] is not Territory class: size = %d; input.floorId = %s".format(
                        properties.size, input.floorId
                    )
        )
        val floor = Floor(
            ctx = ctx,
            house = properties[0] as House,
            entrance = properties[1] as? Entrance,
            territory = properties[2] as? Territory,
            floorNum = input.floorNum,
            isSecurity = input.isSecurityFloor,
            isIntercom = input.isIntercomFloor,
            isResidential = input.isResidentialFloor,
            roomsByFloor = input.roomsByFloor,
            estimatedRooms = input.estFloorRooms,
            floorDesc = input.floorDesc
        )
        floor.id = input.floorId
        return floor
    }

    override fun nullableMap(input: FloorEntity?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}