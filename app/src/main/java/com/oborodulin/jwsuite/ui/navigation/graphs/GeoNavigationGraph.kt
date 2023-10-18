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
import com.oborodulin.jwsuite.presentation_geo.ui.geo.GeoScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single.RegionDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict.StreetLocalityDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict.StreetMicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetScreen
import timber.log.Timber

private const val TAG = "App.Navigation.geoNavGraph"

fun NavGraphBuilder.geoNavGraph(
    appState: AppState,
    paddingValues: PaddingValues,
    onChangeTopBarActions: (@Composable RowScope.() -> Unit) -> Unit
) {
    navigation(route = Graph.GEO, startDestination = NavRoutes.Geo.route) {
        // GeoScreen:
        composable(route = NavRoutes.Geo.route, arguments = NavRoutes.Geo.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to GeoScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Geo.arguments
                )
            GeoScreen(appState = appState)
        }
        // RegionScreen:
        composable(route = NavRoutes.Region.route, arguments = NavRoutes.Region.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to RegionScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Region.arguments
                )
            RegionScreen(appState = appState, regionInput = NavRoutes.Region.fromEntry(it))
        }
        // RegionDistrictScreen:
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
        // LocalityScreen:
        composable(route = NavRoutes.Locality.route, arguments = NavRoutes.Locality.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to LocalityScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Locality.arguments
                )
            LocalityScreen(appState = appState, localityInput = NavRoutes.Locality.fromEntry(it))
        }
        // MicrodistrictScreen:
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
        // StreetScreen:
        composable(route = NavRoutes.Street.route, arguments = NavRoutes.Street.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to StreetScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Street.arguments
                )
            StreetScreen(appState = appState, streetInput = NavRoutes.Street.fromEntry(it))
        }
        // StreetLocalityDistrictScreen:
        composable(
            route = NavRoutes.StreetLocalityDistrict.route,
            arguments = NavRoutes.StreetLocalityDistrict.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to StreetLocalityDistrictScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.StreetLocalityDistrict.arguments
                )
            StreetLocalityDistrictScreen(
                appState = appState,
                streetLocalityDistrictInput = NavRoutes.StreetLocalityDistrict.fromEntry(it)
            )
        }
        // StreetMicrodistrictScreen:
        composable(
            route = NavRoutes.StreetMicrodistrict.route,
            arguments = NavRoutes.StreetMicrodistrict.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to StreetMicrodistrictScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.StreetMicrodistrict.arguments
                )
            StreetMicrodistrictScreen(
                appState = appState,
                streetMicrodistrictInput = NavRoutes.StreetMicrodistrict.fromEntry(it)
            )
        }
    }
}
