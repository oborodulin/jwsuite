package com.oborodulin.home.common.ui.components.field.util

import android.os.Parcelable
import androidx.annotation.StringRes
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputListItemWrapper(
    val item: ListItemModel = ListItemModel(),
    @StringRes override val errorId: Int? = null,
    override val errorMsg: String? = null,
    val isEmpty: Boolean = true,
    val isSaved: Boolean = true
) : InputWrapped, Parcelable

fun InputListItemWrapper.toInputWrapper() = InputWrapper(
    value = item.headline, errorId = errorId,
    errorMsg = errorMsg,
    isEmpty = isEmpty,
    isSaved = isSaved
)