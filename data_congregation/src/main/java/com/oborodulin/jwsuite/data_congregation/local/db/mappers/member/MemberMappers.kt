package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberToMemberCongregationCrossRefEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleToMemberRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToMemberRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.RoleEntityListToRolesListMapper

data class MemberMappers(
    val memberViewListToMembersListMapper: MemberViewListToMembersListMapper,
    val memberViewToMemberMapper: MemberViewToMemberMapper,
    val membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
    val memberToMemberEntityMapper: MemberToMemberEntityMapper,
    val memberToMemberCongregationCrossRefEntityMapper: MemberToMemberCongregationCrossRefEntityMapper,
    val roleEntityListToRolesListMapper: RoleEntityListToRolesListMapper,
    val memberRoleViewListToMemberRolesListMapper: MemberRoleViewListToMemberRolesListMapper,
    val memberRoleToMemberRoleEntityMapper: MemberRoleToMemberRoleEntityMapper,
    val memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper
)
