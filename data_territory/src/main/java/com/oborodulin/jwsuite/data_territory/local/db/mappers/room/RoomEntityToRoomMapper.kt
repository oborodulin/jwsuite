package com.oborodulin.jwsuite.data_territory.local.db.mappers.room

import android.content.Context
import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoStreet
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.domain.model.territory.Room
import com.oborodulin.jwsuite.domain.model.territory.Territory

class RoomEntityToRoomMapper(private val ctx: Context) : ConstructedMapper<RoomEntity, Room> {
    override fun map(input: RoomEntity, vararg properties: Any?): Room {
        if (properties.isEmpty() || properties.size < 8 ||
            properties[0] !is GeoLocality || (properties[1] != null && properties[1] !is GeoLocalityDistrict) ||
            (properties[2] != null && properties[2] !is GeoMicrodistrict) ||
            properties[3] !is GeoStreet ||
            properties[4] !is House || (properties[5] != null && properties[5] !is Entrance) ||
            (properties[6] != null && properties[6] !is Floor) || (properties[7] != null && properties[7] !is Territory)
        ) throw IllegalArgumentException(
            "RoomEntityToRoomMapper: properties is empty or properties size less then 8 or " +
                    "properties[0] is not GeoLocality class or properties[1] is not GeoLocalityDistrict? class " +
                    "properties[2] is not GeoMicrodistrict? class or properties[3] is not GeoStreet class " +
                    "properties[4] is not House class or properties[5] is not Entrance class " +
                    "or properties[6] is not Floor class or properties[7] is not Territory class: size = %d; input.roomId = %s".format(
                        properties.size, input.roomId
                    )
        )
        return Room(
            ctx = ctx,
            locality = properties[0] as GeoLocality,
            localityDistrict = properties[1] as? GeoLocalityDistrict,
            microdistrict = properties[2] as? GeoMicrodistrict,
            street = properties[3] as GeoStreet,
            house = properties[4] as House,
            entrance = properties[5] as? Entrance,
            floor = properties[6] as? Floor,
            territory = properties[7] as? Territory,
            roomNum = input.roomNum,
            isIntercom = input.isIntercomRoom,
            isResidential = input.isResidentialRoom,
            isForeignLanguage = input.isForeignLangRoom,
            roomDesc = input.roomDesc
        ).also { it.id = input.roomId }
    }
}