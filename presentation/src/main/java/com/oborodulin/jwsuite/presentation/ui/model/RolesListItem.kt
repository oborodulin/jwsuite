package com.oborodulin.jwsuite.presentation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.MemberRoleType
import java.util.UUID

data class RolesListItem(
    val id: UUID,
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String
) : Parcelable, ListItemModel(itemId = id, headline = roleName)
