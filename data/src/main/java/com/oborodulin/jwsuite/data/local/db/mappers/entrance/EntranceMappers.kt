package com.oborodulin.jwsuite.data.local.db.mappers.entrance

data class EntranceMappers(
    val entranceEntityListToEntrancesListMapper: EntranceEntityListToEntrancesListMapper,
    val entranceViewToEntranceMapper: EntranceViewToEntranceMapper,
    val entrancesListToEntranceEntityListMapper: EntrancesListToEntranceEntityListMapper,
    val entranceToEntranceEntityMapper: EntranceToEntranceEntityMapper
)
