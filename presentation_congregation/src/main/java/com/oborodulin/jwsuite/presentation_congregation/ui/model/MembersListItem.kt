package com.oborodulin.jwsuite.presentation_congregation.ui.model

import android.os.Parcelable
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
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val movementDate: OffsetDateTime? = null
) : Parcelable, ListItemModel(
    itemId = id, headline = memberFullName, supportingText = "${group.groupNum}.$memberNum"
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "${group.groupNum}.$memberNum$memberFullName",
            "${group.groupNum}.$memberNum $memberFullName",
            "${group.groupNum}.$memberNum$memberShortName",
            "${group.groupNum}.$memberNum $memberShortName"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
