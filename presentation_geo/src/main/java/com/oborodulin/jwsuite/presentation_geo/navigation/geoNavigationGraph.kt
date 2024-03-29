package com.oborodulin.jwsuite.presentation_geo.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation_geo.ui.geo.GeoScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single.RegionDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict.StreetLocalityDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict.StreetMicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetScreen
import timber.log.Timber

private const val TAG = "App.Navigation.geoNavGraph"

fun NavGraphBuilder.geoNavGraph(
    startDestination: String? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    navigation(route = Graph.GEO, startDestination = startDestination ?: NavRoutes.Geo.route) {
        // GeoScreen:
        /*composable(route = NavRoutes.Geo.route, arguments = NavRoutes.Geo.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to GeoScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.Geo.arguments
            )
            GeoScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // RegionScreen:
        composable(route = NavRoutes.Region.route, arguments = NavRoutes.Region.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to RegionScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.Region.arguments
            )
            RegionScreen(
                regionInput = NavRoutes.Region.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // RegionDistrictScreen:
        composable(
            route = NavRoutes.RegionDistrict.route, arguments = NavRoutes.RegionDistrict.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to RegionDistrictScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.RegionDistrict.arguments
            )
            RegionDistrictScreen(
                regionDistrictInput = NavRoutes.RegionDistrict.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // LocalityScreen:
        composable(route = NavRoutes.Locality.route, arguments = NavRoutes.Locality.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to LocalityScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Locality.arguments
            )
            LocalityScreen(
                localityInput = NavRoutes.Locality.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // LocalityDistrictScreen:
        composable(
            route = NavRoutes.LocalityDistrict.route,
            arguments = NavRoutes.LocalityDistrict.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to LocalityDistrictScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.LocalityDistrict.arguments
            )
            LocalityDistrictScreen(
                localityDistrictInput = NavRoutes.LocalityDistrict.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // MicrodistrictScreen:
        composable(
            route = NavRoutes.Microdistrict.route, arguments = NavRoutes.Microdistrict.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to MicrodistrictScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Microdistrict.arguments
            )
            MicrodistrictScreen(
                microdistrictInput = NavRoutes.Microdistrict.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // StreetScreen:
        composable(route = NavRoutes.Street.route, arguments = NavRoutes.Street.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to StreetScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Street.arguments
            )
            StreetScreen(
                streetInput = NavRoutes.Street.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // StreetLocalityDistrictScreen:
        composable(
            route = NavRoutes.StreetLocalityDistrict.route,
            arguments = NavRoutes.StreetLocalityDistrict.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to StreetLocalityDistrictScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.StreetLocalityDistrict.arguments
            )
            StreetLocalityDistrictScreen(
                streetLocalityDistrictInput = NavRoutes.StreetLocalityDistrict.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // StreetMicrodistrictScreen:
        composable(
            route = NavRoutes.StreetMicrodistrict.route,
            arguments = NavRoutes.StreetMicrodistrict.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to StreetMicrodistrictScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.StreetMicrodistrict.arguments
            )
            StreetMicrodistrictScreen(
                streetMicrodistrictInput = NavRoutes.StreetMicrodistrict.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }*/
    }
}
