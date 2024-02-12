package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationEntityToCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.congregation.MemberLastCongregationViewToMemberCongregationMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.movement.MemberLastMovementViewToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.domain.model.congregation.Member

class MemberViewToMemberMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val congregationMapper: CongregationEntityToCongregationMapper,
    private val groupMapper: GroupViewToGroupMapper,
    private val lastCongregationMapper: MemberLastCongregationViewToMemberCongregationMapper,
    private val movementMapper: MemberLastMovementViewToMemberMovementMapper
) : NullableMapper<MemberView, Member>, Mapper<MemberView, Member> {
    override fun map(input: MemberView): Member {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val locality = localityMapper.map(input.locality, region, regionDistrict)
        val member = Member(
            congregation = congregationMapper.map(input.memberCongregation, locality),
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
        )
        member.id = input.member.memberId
        return member
    }

    override fun nullableMap(input: MemberView?) = input?.let { map(it) }
}