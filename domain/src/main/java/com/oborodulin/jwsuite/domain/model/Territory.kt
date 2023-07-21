package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class Territory(
    val congregation: Congregation,
    val territoryCategory: TerritoryCategory,
    val locality: GeoLocality,
    val localityDistrictId: UUID? = null,
    val districtShortName: String?,
    val microdistrictId: UUID? = null,
    val microdistrictShortName: String?,
    val territoryNum: Int,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val isActive: Boolean = true,
    val territoryDesc: String? = null,
    val territoryStreets: MutableList<TerritoryStreet> = mutableListOf(),
    val houses: MutableList<House> = mutableListOf(),
    val entrances: MutableList<Entrance> = mutableListOf(),
    val floors: MutableList<Floor> = mutableListOf(),
    val rooms: MutableList<Room> = mutableListOf(),
    val member: Member? = null,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null,
    val handOutDays: Int? = null,
    val expiredDays: Int? = null
) : DomainModel() {
    val cardNum =
        "${congregation.territoryMark}${territoryCategory.territoryCategoryMark}".plus("-${territoryNum}")
    val cardLocation =
        "[".plus(if (locality.id != congregation.locality.id) "${locality.localityShortName}:" else "")
            .plus(if (!districtShortName.isNullOrEmpty()) "$districtShortName:" else "")
            .plus(if (!microdistrictShortName.isNullOrEmpty()) "$microdistrictShortName]" else "]")
            .replace("[]", "")
            .replace(":]", "]")
}
