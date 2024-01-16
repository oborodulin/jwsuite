package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

data class TransferObjectMappers(
    val transferObjectEntityToTransferObjectMapper: TransferObjectEntityToTransferObjectMapper,
    val transferObjectEntityListToTransferObjectsListMapper: TransferObjectEntityListToTransferObjectsListMapper,
    val roleTransferObjectViewListToRoleTransferObjectsListMapper: RoleTransferObjectViewListToRoleTransferObjectsListMapper,
    val memberRoleTransferObjectToRoleTransferObjectEntityMapper: MemberRoleTransferObjectToRoleTransferObjectEntityMapper,
    val memberRoleTransferObjectViewToTransferObjectMapper: MemberRoleTransferObjectViewToTransferObjectMapper,
    val memberRoleTransferObjectViewListToTransferObjectsListMapper: MemberRoleTransferObjectViewListToTransferObjectsListMapper,
    val memberRoleTransferObjectViewToMemberRoleTransferObjectMapper: MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper,
    val memberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper: MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper
)
