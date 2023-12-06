package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import java.time.OffsetDateTime
import java.util.UUID

data class Member(
    val congregation: Congregation,
    val group: Group? = null,
    val memberNum: String? = null,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val loginExpiredDate: OffsetDateTime? = null,
    var memberCongregationId: UUID? = null,
    val congregationId: UUID,
    val activityDate: OffsetDateTime,
    val lastMovement: MemberMovement
) : DomainModel() {
    val fullName =
        "${surname.orEmpty()} ${memberName.orEmpty()} ${patronymic.orEmpty()} [$pseudonym]".trim()
    val shortName = ("$surname"
        .plus(if (!memberName.isNullOrEmpty()) " ${memberName[0]}." else "")
        .plus(if (!patronymic.isNullOrEmpty()) "${patronymic[0]}." else "")
        .plus(" [$pseudonym]")).trim()

    companion object {
        fun getPseudonym(
            surname: String?, memberName: String?, groupNum: Int?, memberNum: String?
        ) =
            "${surname?.firstOrNull() ?: ""}${memberName?.firstOrNull() ?: ""}${groupNum?.toString() ?: "0"}${
                memberNum?.let { ".$it" }.orEmpty()
            }"
    }
}
