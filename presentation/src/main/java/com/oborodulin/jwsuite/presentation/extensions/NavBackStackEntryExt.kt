package com.oborodulin.jwsuite.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.oborodulin.jwsuite.presentation.navigation.Graph

@Composable
fun rememberParentEntry(navController: NavHostController): NavBackStackEntry {
    // NavBackStackEntry.
    // First, get the parent of the current destination
    // This always exists since every destination in your graph has a parent
    navController.currentBackStackEntry?.destination?.parent?.let {
        val parentId = it.id

        // Now get the NavBackStackEntry associated with the parent
        // making sure to remember it
        return remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(parentId)
        }
    }
    return remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(Graph.ROOT) //NavRoutes.Home.route
    }
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
