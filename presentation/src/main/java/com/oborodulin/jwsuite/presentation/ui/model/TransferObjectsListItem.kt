package com.oborodulin.jwsuite.presentation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.TransferObjectType
import java.util.UUID

data class TransferObjectsListItem(
    val id: UUID? = null,
    val transferObjectType: TransferObjectType = TransferObjectType.ALL,
    val transferObjectName: String = ""
) : Parcelable, ListItemModel(itemId = id, headline = transferObjectName)
