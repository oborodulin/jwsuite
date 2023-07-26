package com.oborodulin.home.common.ui.components.field.util

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputsWrapper(
    @StringRes override val errorId: Int? = null,
    override val errorMsg: String? = null,
    val inputs: MutableMap<String, InputWrapper> = mutableMapOf()
) : InputWrapped, Parcelable