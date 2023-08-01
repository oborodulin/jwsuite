package com.oborodulin.home.common.ui.components.tab

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TabRowItem (
    val title: String,
    val icon: ImageVector? = null,
    val view: @Composable () -> Unit
)