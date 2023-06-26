package com.oborodulin.jwsuite.data.local.db.mappers.entrance

data class EntranceMappers(
    val entranceEntityListToEntrancesListMapper: EntranceEntityListToEntrancesListMapper,
    val entranceEntityToEntranceMapper: EntranceEntityToEntranceMapper,
    val entrancesListToEntranceEntityListMapper: EntrancesListToEntranceEntityListMapper,
    val entranceToEntranceEntityMapper: EntranceToEntranceEntityMapper
)
