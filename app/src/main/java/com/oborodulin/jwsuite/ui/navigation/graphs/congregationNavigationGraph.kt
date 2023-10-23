package com.oborodulin.jwsuite.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberScreen
import timber.log.Timber

private const val TAG = "App.Navigation.congregationNavGraph"

fun NavGraphBuilder.congregationNavGraph(
    appState: AppState,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    navigation(
        route = Graph.CONGREGATION, startDestination = NavRoutes.Congregation.route
    ) {
        composable(
            route = NavRoutes.Congregation.route, arguments = NavRoutes.Congregation.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to CongregationScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Congregation.arguments.firstOrNull()
                )
            CongregationScreen(
                appState = appState, congregationInput = NavRoutes.Congregation.fromEntry(it),
                paddingValues = paddingValues,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.Group.route, arguments = NavRoutes.Group.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to GroupScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Group.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            GroupScreen(
                appState = appState,
                //sharedViewModel = sharedViewModel,
                groupInput = NavRoutes.Group.fromEntry(it),
                paddingValues = paddingValues,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(route = NavRoutes.Member.route, arguments = NavRoutes.Member.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to MemberScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Member.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            MemberScreen(
                appState = appState,
                //sharedViewModel = sharedViewModel,
                memberInput = NavRoutes.Member.fromEntry(it),
                paddingValues = paddingValues,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
    }
}
