package com.oborodulin.jwsuite.presentation_congregation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

data class MemberRolesListItem(
    val id: UUID? = null,
    val role: RolesListItem = RolesListItem(),
    val roleExpiredDate: OffsetDateTime? = null
) : Parcelable, ListItemModel(
    itemId = id, headline = role.roleName, supportingText = roleExpiredDate?.format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    )
)
