package com.oborodulin.jwsuite.presentation_congregation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single.MemberRoleScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberScreen
import timber.log.Timber

private const val TAG = "App.Navigation.congregationNavGraph"

fun NavGraphBuilder.congregationNavGraph(
    startDestination: String? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit
) {
    navigation(
        route = Graph.CONGREGATION,
        startDestination = startDestination ?: NavRoutes.Congregation.route
    ) {
        composable(
            route = NavRoutes.Congregation.route, arguments = NavRoutes.Congregation.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to CongregationScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Congregation.arguments.firstOrNull()
            )
            CongregationScreen(
                congregationInput = NavRoutes.Congregation.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.Group.route, arguments = NavRoutes.Group.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to GroupScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Group.arguments.firstOrNull()
            )
            onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            GroupScreen(
                //sharedViewModel = sharedViewModel,
                groupInput = NavRoutes.Group.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.Member.route, arguments = NavRoutes.Member.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to MemberScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Member.arguments.firstOrNull()
            )
            onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            MemberScreen(
                //sharedViewModel = sharedViewModel,
                memberInput = NavRoutes.Member.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.MemberRole.route, arguments = NavRoutes.MemberRole.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to MemberRoleScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.MemberRole.arguments.firstOrNull()
            )
            onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            MemberRoleScreen(
                //sharedViewModel = sharedViewModel,
                memberRoleInput = NavRoutes.MemberRole.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
    }
}