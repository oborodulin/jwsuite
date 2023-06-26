package com.oborodulin.jwsuite.data.local.db.mappers.member

data class MemberMappers(
    val memberEntityListToMembersListMapper: MemberEntityListToMembersListMapper,
    val memberEntityToMemberMapper: MemberEntityToMemberMapper,
    val membersListToMemberEntityListMapper: MembersListToMemberEntityListMapper,
    val memberToMemberEntityMapper: MemberToMemberEntityMapper
)
