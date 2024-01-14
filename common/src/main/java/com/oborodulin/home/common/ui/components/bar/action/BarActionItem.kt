package com.oborodulin.home.common.ui.components.bar.action

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BarActionItem(
    @StringRes val titleResId: Int? = null,
    val iconImageVector: ImageVector? = null,
    @DrawableRes val iconPainterResId: Int? = null,
    @StringRes val cntDescResId: Int? = null,
    val userRoles: List<String> = emptyList(),
    val onClick: () -> Unit = {}
)