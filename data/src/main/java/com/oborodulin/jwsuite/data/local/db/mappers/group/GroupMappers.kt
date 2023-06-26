package com.oborodulin.jwsuite.data.local.db.mappers.group

data class GroupMappers(
    val groupEntityListToGroupsListMapper: GroupEntityListToGroupsListMapper,
    val groupEntityToGroupMapper: GroupEntityToGroupMapper,
    val groupsListToGroupEntityListMapper: GroupsListToGroupEntityListMapper,
    val groupToGroupEntityMapper: GroupToGroupEntityMapper
)
