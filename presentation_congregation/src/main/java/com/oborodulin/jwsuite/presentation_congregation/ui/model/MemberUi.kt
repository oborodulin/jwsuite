package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.MemberType
import java.time.OffsetDateTime
import java.util.UUID

data class MemberUi(
    val congregation: CongregationUi = CongregationUi(),
    val group: GroupUi? = null,
    val memberNum: String? = null,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String = "",
    val fullNum: String = "",
    val memberFullName: String? = null,
    val memberShortName: String? = null,
    val phoneNumber: String? = null,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val memberCongregationId: UUID? = null,
    val congregationId: UUID? = null,
    val activityDate: OffsetDateTime? = OffsetDateTime.now(),
    val memberMovementId: UUID? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val movementDate: OffsetDateTime = OffsetDateTime.now(),
    val loginExpiredDate: OffsetDateTime? = null
) : ModelUi()

fun MemberUi.toMembersListItem() = MembersListItem(
    id = this.id ?: UUID.randomUUID(),
    memberNum = this.memberNum,
    fullNum = this.fullNum,
    memberFullName = this.memberFullName.orEmpty(),
    memberShortName = this.memberShortName.orEmpty(),
    phoneNumber = this.phoneNumber,
    memberType = this.memberType,
    dateOfBirth = this.dateOfBirth,
    dateOfBaptism = this.dateOfBaptism,
    loginExpiredDate = this.loginExpiredDate,
    movementDate = this.movementDate
)

fun ListItemModel?.toMemberUi(congregation: CongregationUi) = MemberUi(
    congregation = congregation, congregationId = congregation.id,
    memberFullName = this?.headline.orEmpty()
).also { it.id = this?.itemId }