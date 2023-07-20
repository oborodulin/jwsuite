package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.MemberType
import java.time.OffsetDateTime
import java.util.UUID

data class MembersListItem(
    val id: UUID,
    val group: GroupUi,
    val memberNum: String,
    val memberFullName: String,
    val memberShortName: String,
    val phoneNumber: String? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val inactiveDate: OffsetDateTime? = null
) : ListItemModel(
    itemId = id, headline = memberFullName, supportingText = "${group.groupNum}.$memberNum"
)
