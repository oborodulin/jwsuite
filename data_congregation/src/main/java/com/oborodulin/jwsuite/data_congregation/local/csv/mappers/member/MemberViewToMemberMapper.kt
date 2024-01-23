package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationEntityToCongregationCsvMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.member.movement.MemberMovementEntityToMemberMovementMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data.services.csv.mappers.geolocality.LocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.services.csv.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper

class MemberViewToMemberMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper,
    private val localityMapper: LocalityViewToGeoLocalityMapper,
    private val congregationMapper: com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationEntityToCongregationCsvMapper,
    private val groupMapper: GroupViewToGroupMapper,
    private val movementMapper: MemberMovementEntityToMemberMovementMapper
) : NullableMapper<MemberView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv>, Mapper<MemberView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv> {
    override fun map(input: MemberView): com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv {
        val region = regionMapper.map(input.region)
        val regionDistrict = regionDistrictMapper.nullableMap(input.district, region)
        val locality = localityMapper.map(input.locality, region, regionDistrict)
        val memberCsv = com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv(
            congregation = congregationMapper.map(input.memberCongregation, locality),
            groupCsv = groupMapper.nullableMap(input.group),
            memberNum = input.member.memberNum,
            memberName = input.member.memberName,
            surname = input.member.surname,
            patronymic = input.member.patronymic,
            pseudonym = input.member.pseudonym,
            phoneNumber = input.member.phoneNumber,
            dateOfBirth = input.member.dateOfBirth,
            dateOfBaptism = input.member.dateOfBaptism,
            loginExpiredDate = input.member.loginExpiredDate,
            memberCongregationId = input.lastCongregation.memberCongregationId,
            congregationId = input.lastCongregation.mcCongregationsId,
            activityDate = input.lastCongregation.activityDate,
            lastMovement = movementMapper.map(input.lastMovement)
        )
        memberCsv.id = input.member.memberId
        return memberCsv
    }

    override fun nullableMap(input: MemberView?) = input?.let { map(it) }
}