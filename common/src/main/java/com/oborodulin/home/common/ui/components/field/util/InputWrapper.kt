package com.oborodulin.home.common.ui.components.field.util

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputWrapper(
    val value: String = "",
    @StringRes override val errorId: Int? = null,
    override val errorMsg: String? = null,
    val isEmpty: Boolean = true,
    val isSaved: Boolean = true
) : InputWrapped, Parcelable