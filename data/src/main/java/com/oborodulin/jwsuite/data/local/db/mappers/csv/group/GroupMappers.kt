package com.oborodulin.jwsuite.data.local.db.mappers.csv.group

data class GroupMappers(
    val groupViewListToGroupsListMapper: GroupViewListToGroupsListMapper,
    val groupViewToGroupMapper: GroupViewToGroupMapper,
    val groupsListToGroupEntityListMapper: GroupsListToGroupEntityListMapper,
    val groupToGroupEntityMapper: GroupToGroupEntityMapper
)
