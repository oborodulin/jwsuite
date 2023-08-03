package com.oborodulin.home.common.util

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// UI:
fun Context.toast(messageId: Int) {
    Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun NavBackStackEntry.rememberParentEntry(navController: NavHostController): NavBackStackEntry {
    // First, get the parent of the current destination
    // This always exists since every destination in your graph has a parent
    val parentId = this.destination.parent!!.id

    // Now get the NavBackStackEntry associated with the parent
    // making sure to remember it
    return remember(this) {
        navController.getBackStackEntry(parentId)
    }
}

fun Modifier.textWidthMatchedTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "textWidthMatchedTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

//create an extension function on a date class which returns a string
fun Date.dateToString(format: String): String {
    //simple date formatter
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

    //return the formatted date string
    return dateFormatter.format(this)
}