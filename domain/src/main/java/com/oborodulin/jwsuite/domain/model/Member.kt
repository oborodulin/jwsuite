package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.MemberType
import java.time.OffsetDateTime

data class Member(
    val group: Group,
    val memberNum: String,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val inactiveDate: OffsetDateTime? = null,
) : DomainModel() {
    val fullName = "$surname $memberName $patronymic ".trim().plus(" [${pseudonym}]".trim())
    val shortName = "$surname ".trim()
        .plus(if (!memberName.isNullOrEmpty()) "${memberName[0]}." else "")
        .plus(if (!patronymic.isNullOrEmpty()) "${patronymic[0]}." else "")
        .plus(" [${pseudonym}]".trim())
}
