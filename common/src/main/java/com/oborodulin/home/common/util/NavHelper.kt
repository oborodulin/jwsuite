package com.oborodulin.home.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

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
