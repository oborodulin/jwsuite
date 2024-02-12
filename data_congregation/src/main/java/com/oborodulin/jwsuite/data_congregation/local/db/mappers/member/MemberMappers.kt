package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberToMemberCongregationCrossRefEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberLastMovementViewToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberToMemberMovementEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleToMemberRoleEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToMemberRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewListToRolesListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.MemberRoleViewToMemberRoleMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectToRoleTransferObjectEntityMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewListToTransferObjectsListMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer.MemberRoleTransferObjectViewToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.role.RoleEntityListToRolesListMapper

data class MemberMappers(
    // Member:
    val memberViewListToMembersListMapper: MemberViewListToMembersListMapper,
    val memberViewToMemberMapper: MemberViewToMemberMapper,
    val membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
    val memberToMemberEntityMapper: MemberToMemberEntityMapper,
    // Congregation:
    val memberToMemberCongregationCrossRefEntityMapper: MemberToMemberCongregationCrossRefEntityMapper,
    // Movement:
    val memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper,
    val memberLastMovementViewToMemberMovementMapper: MemberLastMovementViewToMemberMovementMapper,
    // Role:
    val roleEntityListToRolesListMapper: RoleEntityListToRolesListMapper,
    // Member Role:
    val memberRoleViewToMemberRoleMapper: MemberRoleViewToMemberRoleMapper,
    val memberRoleViewListToMemberRolesListMapper: MemberRoleViewListToMemberRolesListMapper,
    val memberRoleViewListToRolesListMapper: MemberRoleViewListToRolesListMapper,
    val memberRoleToMemberRoleEntityMapper: MemberRoleToMemberRoleEntityMapper,
    // Member Role Transfer Object:
    val memberRoleTransferObjectToRoleTransferObjectEntityMapper: MemberRoleTransferObjectToRoleTransferObjectEntityMapper,
    val memberRoleTransferObjectViewToTransferObjectMapper: MemberRoleTransferObjectViewToTransferObjectMapper,
    val memberRoleTransferObjectViewListToTransferObjectsListMapper: MemberRoleTransferObjectViewListToTransferObjectsListMapper,
    val memberRoleTransferObjectViewToMemberRoleTransferObjectMapper: MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper,
    val memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper: MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
)
