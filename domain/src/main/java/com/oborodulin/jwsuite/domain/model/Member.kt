package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.MemberType
import java.time.OffsetDateTime
import java.util.UUID

data class Member(
    val group: Group,
    val memberNum: String,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    var memberCongregationId: UUID? = null,
    val congregationId: UUID,
    val activityDate: OffsetDateTime,
    var memberMovementId: UUID? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val movementDate: OffsetDateTime
) : DomainModel() {
    val fullName = "$surname $memberName $patronymic [$pseudonym]".trim()
    val shortName = ("$surname "
        .plus(if (!memberName.isNullOrEmpty()) "${memberName[0]}." else "")
        .plus(if (!patronymic.isNullOrEmpty()) "${patronymic[0]}." else "")
        .plus(" [${pseudonym}]")).trim()
}
