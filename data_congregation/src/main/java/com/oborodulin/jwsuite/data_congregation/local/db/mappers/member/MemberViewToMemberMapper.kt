package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationEntityToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberLastCongregationViewToMemberCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberLastMovementViewToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.domain.model.congregation.Member

class MemberViewToMemberMapper(
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val congregationMapper: CongregationEntityToCongregationMapper,
    private val groupMapper: GroupViewToGroupMapper,
    private val lastCongregationMapper: MemberLastCongregationViewToMemberCongregationMapper,
    private val movementMapper: MemberLastMovementViewToMemberMovementMapper
) : NullableMapper<MemberView, Member>, Mapper<MemberView, Member> {
    override fun map(input: MemberView) = Member(
        congregation = congregationMapper.map(
            input.memberCongregation, localityMapper.map(input.locality)
        ),
        group = groupMapper.nullableMap(input.group),
        memberNum = input.member.memberNum,
        memberName = input.member.memberName,
        surname = input.member.surname,
        patronymic = input.member.patronymic,
        pseudonym = input.member.pseudonym,
        phoneNumber = input.member.phoneNumber,
        dateOfBirth = input.member.dateOfBirth,
        dateOfBaptism = input.member.dateOfBaptism,
        loginExpiredDate = input.member.loginExpiredDate,
        lastCongregation = lastCongregationMapper.map(input.lastCongregation),
        lastMovement = movementMapper.map(input.lastMovement)
    ).also { it.id = input.member.memberId }

    override fun nullableMap(input: MemberView?) = input?.let { map(it) }
}