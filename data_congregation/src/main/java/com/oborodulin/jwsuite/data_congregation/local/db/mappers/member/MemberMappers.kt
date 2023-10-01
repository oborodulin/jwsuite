package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

data class MemberMappers(
    val memberViewListToMembersListMapper: MemberViewListToMembersListMapper,
    val memberViewToMemberMapper: MemberViewToMemberMapper,
    val membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
    val memberToMemberEntityMapper: MemberToMemberEntityMapper,
    val memberToMemberCongregationCrossRefEntityMapper: MemberToMemberCongregationCrossRefEntityMapper,
    val memberToMemberMovementEntityMapper: MemberToMemberMovementEntityMapper
)
