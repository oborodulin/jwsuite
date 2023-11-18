package com.oborodulin.jwsuite.presentation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import java.util.UUID

data class RoleTransferObjectsListItem(
    val id: UUID? = null,
    val isPersonalData: Boolean,
    val transferObject: TransferObjectsListItem = TransferObjectsListItem()
) : Parcelable, ListItemModel(
    itemId = id,
    headline = transferObject.transferObjectName,
    supportingText = isPersonalData.toString()
)
