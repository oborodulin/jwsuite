package com.oborodulin.jwsuite.ui.main

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.components.BottomNavigationComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.ui.navigation.MainNavigationHost
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "App.ui.MainScreen"

@Composable
fun MainScreen(sessionViewModel: SessionViewModel) { // Impl = hiltViewModel()
    Timber.tag(TAG).d("MainScreen(...) called")
    val appState = LocalAppState.current
    val activity = (LocalContext.current as? Activity)
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
    val bottomBar = @Composable {
        if (appState.shouldShowBottomNavBar) {
            BottomNavigationComponent(
                modifier = Modifier
                    .height(bottomBarHeight)
                    .offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.roundToInt()) }
            )
        }
    }
    val handleLogoutActionClick: () -> Unit = {
        Timber.tag(TAG).d("MainScreen: Logout Button onClick...")
        sessionViewModel.handleActionJob(
            { sessionViewModel.submitAction(SessionUiAction.Logout(appState.mainNavCurrentRoute)) })
        { activity?.finish() }
    }
    // Action Bar:
    var actionBar: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onActionBarChange: (@Composable (() -> Unit)?) -> Unit = { actionBar = it }

    // Action Bar -> Titles:
    var actionBarTitle by rememberSaveable { mutableStateOf("") } //appState.appName
    val onActionBarTitleChange: (String) -> Unit =
        { actionBarTitle = it } //"${appState.appName} - $it"

    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }

    // Action Bar -> Navigation Icon:
    var topBarNavImageVector: ImageVector? by remember { mutableStateOf(null) }
    val onTopBarNavImageVectorChange: (ImageVector?) -> Unit = { topBarNavImageVector = it }

    var onTopBarNavClick: () -> Unit by remember { mutableStateOf({}) }
    val onTopBarNavClickChange: (() -> Unit) -> Unit = { onTopBarNavClick = it }

    var navIcon: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onNavIconChange: (@Composable (() -> Unit)?) -> Unit = { navIcon = it }

    // Action Bar -> Actions:
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    var isTopBarActionsLeading by rememberSaveable { mutableStateOf(false) }
    val onTopBarActionsChange: (Boolean, @Composable RowScope.() -> Unit) -> Unit =
        { isActionsLeading, actions ->
            isTopBarActionsLeading = isActionsLeading
            topBarActions = actions
        }

    // Bottom Navigation:
    var isNestedScrollConnection by rememberSaveable { mutableStateOf(false) }
    val shouldUseNestedScrollConnection: (Boolean) -> Unit =
        { isNestedScrollConnection = it }

    var isBottomNavigation by rememberSaveable { mutableStateOf(false) }
    val areUsingBottomNavigation: (Boolean) -> Unit = { isBottomNavigation = it }

    // FAB:
    var floatingActionButton: @Composable () -> Unit by remember { mutableStateOf({}) }
    val onFabChange: (@Composable () -> Unit) -> Unit = { floatingActionButton = it }

    /*ScaffoldComponent(
        nestedScrollConnection = nestedScrollConnection,
        isNestedScrollConnection = isNestedScrollConnection,
        topBarNavImageVector = topBarNavImageVector,
        onTopBarNavClick = onTopBarNavClick,
        topBarTitle = actionBarTitle,
        topBarSubtitle = actionBarSubtitle,
        actionBar = actionBar,
        defTopBarActions = {
            IconButton(onClick = handleLogoutActionClick) {
                Icon(Icons.Outlined.ExitToApp, null)
            }
        },
        topBarActions = topBarActions,
        isActionsLeading = isTopBarActionsLeading,
        bottomBar = {
            if (appState.shouldShowBottomNavBar) {
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
        isBottomNavigation = isBottomNavigation,
        floatingActionButton = floatingActionButton
    ) { innerPadding ->*/
    MainNavigationHost(
        sessionViewModel = sessionViewModel,
        //innerPadding = innerPadding,
        onActionBarChange = onActionBarChange,
        onActionBarTitleChange = onActionBarTitleChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onNavIconChange = onNavIconChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarNavClickChange = onTopBarNavClickChange,
        shouldUseNestedScrollConnection = shouldUseNestedScrollConnection,
        defTopBarActions = {
            IconButton(onClick = handleLogoutActionClick) {
                Icon(Icons.Outlined.ExitToApp, null)
            }
        },
        onTopBarActionsChange = onTopBarActionsChange,
        areUsingBottomNavigation = areUsingBottomNavigation,
        onFabChange = onFabChange
    )
    //}
    /*LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MainScreen -> LaunchedEffect() BEFORE collect ui state flow")
        sessionViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is SessionUiSingleEvent.OpenLoginScreen -> {
                    appState.rootNavController.navigate(it.navRoute) {
                        popUpTo(it.navRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                else -> {}
            }
        }
    }*/
}

@Composable
private fun HomeNavigationHost(
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("HomeNavigationHost(...) called")
    NavHost(appState.mainNavController, startDestination = NavRoutes.Signup.route) {
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
            /* BarNavigationHost(
                 appState = appState,
                 nestedScrollConnection = nestedScrollConnection,
                 bottomBar = bottomBar
             )*/
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMainScreen() {
    JWSuiteTheme { Surface { MainScreen(SessionViewModelImpl.previewModel(LocalContext.current)) } }
}
