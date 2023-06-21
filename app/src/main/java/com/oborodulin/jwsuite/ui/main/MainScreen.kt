package com.oborodulin.home.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.R
import com.oborodulin.jwsuite.accounting.ui.payer.single.PayerScreen
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.BottomNavigationComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.rememberAppState
import com.oborodulin.jwsuite.ui.navigation.NavBarNavigationHost
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * Created by tfakioglu on 12.December.2021
 */
private const val TAG = "App.ui.MainScreen"

@Composable
fun MainScreen() {
    Timber.tag(TAG).d("MainScreen() called")
    val appState = rememberAppState(appName = stringResource(R.string.app_name))

    val bottomBarHeight = 56.dp
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
    HomeNavigationHost(appState = appState, nestedScrollConnection = nestedScrollConnection,
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
        }
    )
}

@Composable
private fun HomeNavigationHost(
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("HomeNavigationHost(...) called")
    NavHost(appState.commonNavController, startDestination = NavRoutes.Home.route) {
        composable(route = NavRoutes.Congregation.routeForCongregation()) {
            Timber.tag(TAG)
                .d("Navigation Graph: to PayerScreen [route = '%s']", it.destination.route)
            PayerScreen(appState = appState)
        }
        composable(route = NavRoutes.Congregation.route, arguments = NavRoutes.Congregation.arguments) {
            Timber.tag(TAG)
                .d("Navigation Graph: to PayerScreen [route = '%s']", it.destination.route)
            PayerScreen(
                appState = appState,
                payerInput = NavRoutes.Congregation.fromEntry(it)
            )
        }
        composable(NavRoutes.Home.route) {
            // Dashboard: Payers; Meters values; Payments
            Timber.tag(TAG)
                .d("Navigation Graph: to NavBarNavigationHost [route = '%s']", it.destination.route)
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
    MainScreen()
}
