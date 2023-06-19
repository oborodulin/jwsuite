package com.oborodulin.jwsuite.data.local.db.mappers.entrance

data class EntranceMappers(
    val entranceEntityListToEntranceListMapper: EntranceEntityListToEntranceListMapper,
    val entranceEntityToEntranceMapper: EntranceEntityToEntranceMapper,
    val entranceListToEntranceEntityListMapper: EntranceListToEntranceEntityListMapper,
    val entranceToEntranceEntityMapper: EntranceToEntranceEntityMapper
)
