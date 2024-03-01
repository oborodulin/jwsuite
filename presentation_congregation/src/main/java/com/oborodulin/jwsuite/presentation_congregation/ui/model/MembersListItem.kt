package com.oborodulin.jwsuite.presentation_congregation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.types.MemberType
import java.time.OffsetDateTime
import java.util.UUID

data class MembersListItem(
    val id: UUID,
    val memberNum: String? = null,
    val memberFullName: String,
    val memberShortName: String,
    val phoneNumber: String? = null,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val memberType: MemberType? = MemberType.PREACHER,
    val movementDate: OffsetDateTime? = null,
    val loginExpiredDate: OffsetDateTime? = null,
    val fullNum: String
) : Parcelable, ListItemModel(
    itemId = id,
    headline = memberFullName,
    supportingText = fullNum
) {
    override fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$fullNum$memberFullName",
            "$fullNum $memberFullName",
            "$fullNum$memberShortName",
            "$fullNum $memberShortName"
        )
        return matchingCombinations.any { it.contains(query, ignoreCase = true) }
    }
}
