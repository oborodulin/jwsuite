package com.oborodulin.jwsuite.presentation_congregation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class GroupsListItem(
    val id: UUID,
    val groupNum: Int
) : Parcelable, ListItemModel(itemId = id, headline = groupNum.toString())
