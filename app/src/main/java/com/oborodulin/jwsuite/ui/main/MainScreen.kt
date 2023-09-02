package com.oborodulin.home.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.home.common.util.rememberParentEntry
import com.oborodulin.jwsuite.R
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.BottomNavigationComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.rememberAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single.RegionDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.TerritoryDetailsScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.HandOutTerritoriesConfirmationScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.TerritoryStreetScreen
import com.oborodulin.jwsuite.ui.navigation.NavBarNavigationHost
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * Created by o.borodulin 10.June.2023
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
    HomeNavigationHost(appState = appState, nestedScrollConnection = nestedScrollConnection) {
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
}

@Composable
private fun HomeNavigationHost(
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("HomeNavigationHost(...) called")
    NavHost(appState.commonNavController, startDestination = NavRoutes.Home.route) {
        // Congregation:
        /*        composable(route = NavRoutes.Congregation.routeForCongregation()) {
                    Timber.tag(TAG)
                        .d("Navigation Graph: to CongregationScreen [route = '%s']", it.destination.route)
                    CongregationScreen(appState = appState)
                }*/
        composable(
            route = NavRoutes.Congregation.route, arguments = NavRoutes.Congregation.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to CongregationScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Congregation.arguments
                )
            CongregationScreen(
                appState = appState, congregationInput = NavRoutes.Congregation.fromEntry(it)
            )
        }
        composable(route = NavRoutes.Group.route, arguments = NavRoutes.Group.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to GroupScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Group.arguments
                )
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            GroupScreen(
                appState = appState,
                sharedViewModel = sharedViewModel,
                groupInput = NavRoutes.Group.fromEntry(it)
            )
        }
        composable(route = NavRoutes.Member.route, arguments = NavRoutes.Member.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to MemberScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Member.arguments
                )
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            MemberScreen(
                appState = appState,
                sharedViewModel = sharedViewModel,
                memberInput = NavRoutes.Member.fromEntry(it)
            )
        }

        // Territory:
        composable(
            route = NavRoutes.TerritoryCategory.route,
            arguments = NavRoutes.TerritoryCategory.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryCategoryScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryCategory.arguments
                )
            TerritoryCategoryScreen(
                appState = appState,
                territoryCategoryInput = NavRoutes.TerritoryCategory.fromEntry(it)
            )
        }
        composable(route = NavRoutes.HandOutTerritoriesConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HandOutTerritoriesConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            HandOutTerritoriesConfirmationScreen(
                appState = appState,
                sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel
            )
        }
        composable(route = NavRoutes.Territory.route, arguments = NavRoutes.Territory.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Territory.arguments
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(appState.commonNavController))
            TerritoryScreen(
                appState = appState,
                sharedViewModel = sharedViewModel,
                viewModel = territoryViewModel,
                territoryInput = NavRoutes.Territory.fromEntry(it)
            )
        }
        composable(
            route = NavRoutes.TerritoryDetails.route,
            arguments = NavRoutes.TerritoryDetails.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryDetailsScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryDetails.arguments
                )
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(appState.commonNavController))
            TerritoryDetailsScreen(
                appState = appState,
                territoryViewModel = territoryViewModel,
                territoryInput = NavRoutes.TerritoryDetails.fromEntry(it)
            )
        }
        composable(
            route = NavRoutes.TerritoryStreet.route, arguments = NavRoutes.TerritoryStreet.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryStreetScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryStreet.arguments
                )
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            TerritoryStreetScreen(
                appState = appState,
                sharedViewModel = sharedViewModel,
                territoryStreetInput = NavRoutes.TerritoryStreet.fromEntry(it)
            )
        }

        // Geo:
        composable(route = NavRoutes.Region.route, arguments = NavRoutes.Region.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to RegionScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Region.arguments
                )
            RegionScreen(appState = appState, regionInput = NavRoutes.Region.fromEntry(it))
        }
        composable(
            route = NavRoutes.RegionDistrict.route, arguments = NavRoutes.RegionDistrict.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to RegionDistrictScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.RegionDistrict.arguments
                )
            RegionDistrictScreen(
                appState = appState, regionDistrictInput = NavRoutes.RegionDistrict.fromEntry(it)
            )
        }
        composable(route = NavRoutes.Locality.route, arguments = NavRoutes.Locality.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to LocalityScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Locality.arguments
                )
            LocalityScreen(appState = appState, localityInput = NavRoutes.Locality.fromEntry(it))
        }
        composable(
            route = NavRoutes.Microdistrict.route, arguments = NavRoutes.Microdistrict.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to MicrodistrictScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Microdistrict.arguments
                )
            MicrodistrictScreen(
                appState = appState, microdistrictInput = NavRoutes.Microdistrict.fromEntry(it)
            )
        }
        composable(route = NavRoutes.Street.route, arguments = NavRoutes.Street.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to StreetScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Street.arguments
                )
            StreetScreen(appState = appState, streetInput = NavRoutes.Street.fromEntry(it))
        }

        // Nav Bar Navigation:
        composable(NavRoutes.Home.route) {
            // Dashboard: Congregations; Meters values; Payments
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
    JWSuiteTheme {
        Surface {
            MainScreen()
        }
    }
}
