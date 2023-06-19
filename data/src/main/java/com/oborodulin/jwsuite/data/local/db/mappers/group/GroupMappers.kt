package com.oborodulin.jwsuite.data.local.db.mappers.group

data class GroupMappers(
    val groupEntityListToGroupListMapper: GroupEntityListToGroupListMapper,
    val groupEntityToGroupMapper: GroupEntityToGroupMapper,
    val groupListToGroupEntityListMapper: GroupListToGroupEntityListMapper,
    val groupToGroupEntityMapper: GroupToGroupEntityMapper
)
