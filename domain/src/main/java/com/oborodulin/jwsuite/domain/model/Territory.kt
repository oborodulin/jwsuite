package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class Territory(
    val congregation: Congregation,
    val territoryCategory: TerritoryCategory,
    val territoryNum: Int,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val isActive: Boolean = true,
    val territoryDesc: String? = null,
    val streets: MutableList<TerritoryStreet> = mutableListOf(),
    val houses: MutableList<House> = mutableListOf(),
    val entrances: MutableList<Entrance> = mutableListOf(),
    val floors: MutableList<Floor> = mutableListOf(),
    val rooms: MutableList<Room> = mutableListOf()
) : DomainModel()
