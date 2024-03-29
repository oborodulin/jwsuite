package com.oborodulin.home.common.ui.components.field.util

import android.os.Parcelable
import androidx.annotation.StringRes
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputListItemWrapper<T : ListItemModel>(
    val item: T? = null,
    @StringRes override val errorId: Int? = null,
    override val errorMsg: String? = null,
    val isEmpty: Boolean = true,
    val isSaved: Boolean = true
) : InputWrapped, Parcelable

fun InputListItemWrapper<ListItemModel>.toInputWrapper() = InputWrapper(
    value = item?.headline.orEmpty(), errorId = errorId,
    errorMsg = errorMsg,
    isEmpty = isEmpty,
    isSaved = isSaved
)