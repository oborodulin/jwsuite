package com.oborodulin.jwsuite.data.local.db.mappers.member

data class MemberMappers(
    val memberEntityListToMemberListMapper: MemberEntityListToMemberListMapper,
    val memberEntityToMemberMapper: MemberEntityToMemberMapper,
    val memberListToMemberEntityListMapper: MemberListToMemberEntityListMapper,
    val memberToMemberEntityMapper: MemberToMemberEntityMapper
)
