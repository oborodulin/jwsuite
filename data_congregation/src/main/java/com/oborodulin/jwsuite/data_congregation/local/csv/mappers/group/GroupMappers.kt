package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.group

data class GroupMappers(
    val groupViewListToGroupsListMapper: GroupViewListToGroupsListMapper,
    val groupViewToGroupMapper: GroupViewToGroupMapper,
    val groupsListToGroupEntityListMapper: GroupsListToGroupEntityListMapper,
    val groupToGroupEntityMapper: GroupToGroupEntityMapper
)
