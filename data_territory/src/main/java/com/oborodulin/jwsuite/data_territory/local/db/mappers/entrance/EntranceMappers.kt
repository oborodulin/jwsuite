package com.oborodulin.jwsuite.data_territory.local.db.mappers.entrance

data class EntranceMappers(
    val entranceViewListToEntrancesListMapper: EntranceViewListToEntrancesListMapper,
    val entranceViewToEntranceMapper: EntranceViewToEntranceMapper,
    val entrancesListToEntranceEntityListMapper: EntrancesListToEntranceEntityListMapper,
    val entranceToEntranceEntityMapper: EntranceToEntranceEntityMapper
)
