package com.oborodulin.home.common.ui.components.fab

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class MinFabItem(
    @StringRes val labelResId: Int? = null,
    val imageVector: ImageVector? = null,
    @DrawableRes val painterResId: Int? = null,
    @StringRes val contentDescriptionResId: Int? = null,
    val onClick: (MinFabItem) -> Unit = {}
)