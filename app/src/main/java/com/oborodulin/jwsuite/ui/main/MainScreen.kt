package com.oborodulin.jwsuite.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.components.BottomNavigationComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.rememberAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.ui.navigation.NavBarNavigationHost
import com.oborodulin.jwsuite.ui.navigation.graphs.MainNavigationGraph
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "App.ui.MainScreen"

@Composable
fun MainScreen(appState: AppState, viewModel: SessionViewModelImpl = hiltViewModel()) {
    Timber.tag(TAG).d("MainScreen() called")
    val bottomBarHeight = 72.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    var bottomBarOffsetHeightPx by rememberSaveable { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx + delta
                bottomBarOffsetHeightPx = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    var actionBar: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onChangeActionBar: (@Composable (() -> Unit)?) -> Unit = { actionBar = it }

    var actionBarTitle by rememberSaveable { mutableStateOf(appState.appName) }
    val onChangeActionBarTitle: (String) -> Unit = { actionBarTitle = "${appState.appName} - $it" }

    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onChangeActionBarSubtitle: (String) -> Unit = { actionBarSubtitle = it }

    var isUseNestedScrollConnection by rememberSaveable { mutableStateOf(false) }
    val areUsingNestedScrollConnection: (Boolean) -> Unit =
        { isUseNestedScrollConnection = it }

    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf({}) }
    val onChangeTopBarActions: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }

    var isUseBottomNavigation by rememberSaveable { mutableStateOf(false) }
    val areUsingBottomNavigation: (Boolean) -> Unit = { isUseBottomNavigation = it }

    var floatingActionButton: @Composable () -> Unit by remember { mutableStateOf({}) }
    val onChangeFab: (@Composable () -> Unit) -> Unit = { floatingActionButton = it }

    JWSuiteTheme { //(darkTheme = true)
        ScaffoldComponent(
            appState = appState,
            nestedScrollConnection = nestedScrollConnection,
            isUseNestedScrollConnection = isUseNestedScrollConnection,
            topBarNavImageVector = Icons.Outlined.ArrowBack,
            topBarNavOnClick = { appState.backToBottomBarScreen() },
            actionBar = actionBar,
            topBarActions = topBarActions,
            bottomBar = {
                if (true) {//appState.shouldShowBottomNavBar
                    BottomNavigationComponent(
                        modifier = Modifier
                            .height(bottomBarHeight)
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = -bottomBarOffsetHeightPx.roundToInt()
                                )
                            },
                        appState
                    )
                }
            },
            isUseBottomNavigation = isUseBottomNavigation,
            floatingActionButton = floatingActionButton
        ) { paddingValues ->
            MainNavigationGraph(
                appState = appState,
                paddingValues = paddingValues,
                onChangeActionBar = onChangeActionBar,
                onChangeActionBarTitle = onChangeActionBarTitle,
                onChangeActionBarSubtitle = onChangeActionBarSubtitle,
                areUsingNestedScrollConnection = areUsingNestedScrollConnection,
                onChangeTopBarActions = onChangeTopBarActions,
                areUsingBottomNavigation = areUsingBottomNavigation,
                onChangeFab = onChangeFab
            )
        }
    }
}

@Composable
private fun HomeNavigationHost(
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("HomeNavigationHost(...) called")
    NavHost(appState.commonNavController, startDestination = NavRoutes.Signup.route) {
        // Congregation:
        // Territory:
        // Geo:
        // Nav Bar Navigation:
        composable(NavRoutes.Home.route) {
            // Dashboard: Congregations; Meters values; Payments
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to NavBarNavigationHost [route = '%s']",
                    it.destination.route
                )
            NavBarNavigationHost(
                appState = appState,
                nestedScrollConnection = nestedScrollConnection,
                bottomBar = bottomBar
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMainScreen() {
    JWSuiteTheme {
        Surface {
            MainScreen(rememberAppState())
        }
    }
}
