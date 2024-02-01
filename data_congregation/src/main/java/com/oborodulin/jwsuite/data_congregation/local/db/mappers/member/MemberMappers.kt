package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberToMemberCongregationCrossRefEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleToMemberRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToMemberRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToMemberRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleEntityListToRolesListMapper

data class MemberMappers(
    val memberViewListToMembersListMapper: MemberViewListToMembersListMapper,
    val memberViewToMemberMapper: MemberViewToMemberMapper,
    val membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
    val memberToMemberEntityMapper: MemberToMemberEntityMapper,
    val memberToMemberCongregationCrossRefEntityMapper: MemberToMemberCongregationCrossRefEntityMapper,
    val roleEntityListToRolesListMapper: RoleEntityListToRolesListMapper,
    val memberRoleViewToMemberRoleMapper: MemberRoleViewToMemberRoleMapper,
    val memberRoleViewListToMemberRolesListMapper: MemberRoleViewListToMemberRolesListMapper,
    val memberRoleViewListToRolesListMapper: MemberRoleViewListToRolesListMapper,
    val memberRoleToMemberRoleEntityMapper: MemberRoleToMemberRoleEntityMapper,
    val memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper
)
