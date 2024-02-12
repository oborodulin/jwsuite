package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberLastMovementView
import com.oborodulin.jwsuite.domain.model.congregation.MemberMovement

class MemberLastMovementViewToMemberMovementMapper(private val mapper: MemberMovementEntityToMemberMovementMapper) :
    NullableMapper<MemberLastMovementView, MemberMovement>,
    Mapper<MemberLastMovementView, MemberMovement> {
    override fun map(input: MemberLastMovementView) = mapper.map(input.lastMemberMovement)

    override fun nullableMap(input: MemberLastMovementView?) = input?.let { map(it) }
}