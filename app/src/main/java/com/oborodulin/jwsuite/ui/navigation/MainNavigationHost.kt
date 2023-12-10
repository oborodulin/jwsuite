package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.home.common.util.rememberParentEntry
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.appsetting.AppSettingScreen
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.session.login.LoginScreen
import com.oborodulin.jwsuite.presentation.ui.session.signup.SignupScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.CurrentMemberViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.CongregatingScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single.MemberRoleScreen
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.MemberScreen
import com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.DashboardingScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.GeoScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single.RegionDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict.StreetLocalityDistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict.StreetMicrodistrictScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetScreen
import com.oborodulin.jwsuite.presentation_territory.ui.housing.HousingScreen
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseScreen
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single.RoomScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.TerritoryHouseScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.TerritoryRoomScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.TerritoryDetailsScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork.ProcessConfirmationScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout.HandOutConfirmationScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.TerritoryStreetScreen
import timber.log.Timber

private const val TAG = "App.Navigation.MainNavigationHost"

// https://medium.com/@mathroda/nested-navigation-graph-in-jetpack-compose-with-bottom-navigation-d983c2d4119f
// https://stackoverflow.com/questions/69738397/jetpackcompose-navigation-nested-graphs-cause-viewmodelstore-should-be-set-befo
@Composable
fun MainNavigationHost(
    sessionViewModel: SessionViewModel,
    innerPadding: PaddingValues,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onNavIconChange: (@Composable (() -> Unit)?) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    shouldUseNestedScrollConnection: (Boolean) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    areUsingBottomNavigation: (Boolean) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("MainNavigationHost(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val context = LocalContext.current
    Timber.tag(TAG).d(
        "MainNavigationHost: session.startDestination = %s",
        session.startDestination
    ) //session.mainRoute = %s", session.mainRoute)
    NavHost(
        navController = appState.mainNavController,// .rootNavController,
        route = Graph.MAIN,
        startDestination = session.startDestination,// NavRoutes.Login.route,//session.mainRoute, //NavRoutes.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        Timber.tag(TAG).d("MainNavigationHost -> NavHost(...)")
        // AUTH Nav Graph:
        composable(route = NavRoutes.Login.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to LoginScreen [route = '%s']", it.destination.route
                )
            LoginScreen(sessionViewModel)
        }
        composable(route = NavRoutes.Signup.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to SignupScreen [route = '%s']", it.destination.route
                )
            SignupScreen(sessionViewModel)
        }

        // CONGREGATION Nav Graph:
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
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
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
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
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
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(route = NavRoutes.MemberRole.route, arguments = NavRoutes.MemberRole.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to MemberRoleScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.MemberRole.arguments.firstOrNull()
            )
            onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val membersListViewModel = hiltViewModel<MembersListViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            MemberRoleScreen(
                //sharedViewModel = sharedViewModel,
                memberRoleInput = NavRoutes.MemberRole.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }

        // TERRITORY Nav Graph:
        composable(route = NavRoutes.Territory.route, arguments = NavRoutes.Territory.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Territory.arguments.firstOrNull()
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoryViewModel,
                territoryInput = NavRoutes.Territory.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(
            route = NavRoutes.TerritoryDetails.route,
            arguments = NavRoutes.TerritoryDetails.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryDetailsScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryDetails.arguments.firstOrNull()
                )
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryDetailsScreen(
                territoryViewModel = territoryViewModel,
                territoryInput = NavRoutes.TerritoryDetails.fromEntry(it),
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(
            route = NavRoutes.TerritoryCategory.route,
            arguments = NavRoutes.TerritoryCategory.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryCategoryScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryCategory.arguments.firstOrNull()
                )
            TerritoryCategoryScreen(
                territoryCategoryInput = NavRoutes.TerritoryCategory.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }

        // Territory: Street, House, Entrace, Floor, Room:
        composable(
            route = NavRoutes.TerritoryStreet.route, arguments = NavRoutes.TerritoryStreet.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryStreetScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryStreet.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryStreetScreen(
                //sharedViewModel = sharedViewModel,
                territoryViewModel = territoryViewModel,
                territoryStreetInput = NavRoutes.TerritoryStreet.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(
            route = NavRoutes.TerritoryHouse.route, arguments = NavRoutes.TerritoryHouse.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryHouseScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryHouse.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryHouseScreen(
                territoryViewModel = territoryViewModel,
                territoryHouseInput = NavRoutes.TerritoryHouse.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }

        composable(
            route = NavRoutes.TerritoryRoom.route, arguments = NavRoutes.TerritoryRoom.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to TerritoryRoomScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.TerritoryRoom.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoryViewModel =
                hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            TerritoryRoomScreen(
                territoryViewModel = territoryViewModel,
                territoryRoomInput = NavRoutes.TerritoryRoom.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }

        // GEO Nav Graph:
        // GeoScreen:
        composable(route = NavRoutes.Geo.route, arguments = NavRoutes.Geo.arguments) {
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
        }

        // HOUSING Nav Graph:
        // House, Entrance, Floor, Room:
        composable(route = NavRoutes.Housing.route, arguments = NavRoutes.Housing.arguments) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HousingScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Housing.arguments
                )
            HousingScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarNavClickChange = onTopBarNavClickChange,
                onTopBarActionsChange = onTopBarActionsChange
            )
        }
        composable(
            route = NavRoutes.House.route, arguments = NavRoutes.House.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HouseScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.House.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            //val territoryViewModel = hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(appState.commonNavController))
            HouseScreen(
                //territoryViewModel = territoryViewModel,
                houseInput = NavRoutes.House.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(
            route = NavRoutes.Room.route, arguments = NavRoutes.Room.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to RoomScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.Room.arguments.firstOrNull()
                )
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            //val territoryViewModel = hiltViewModel<TerritoryViewModelImpl>(it.rememberParentEntry(appState.commonNavController))
            RoomScreen(
                //territoryViewModel = territoryViewModel,
                roomInput = NavRoutes.Room.fromEntry(it),
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }

        // APP SETTINGS Nav Graph:
        composable(
            route = NavRoutes.Settings.route, arguments = NavRoutes.Settings.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to AppSettingScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Settings.arguments.firstOrNull()
            )
            AppSettingScreen(
                sessionViewModel = sessionViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }

        /*congregationNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        territoryNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        geoNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        housingNavGraph(
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarNavClickChange = onTopBarNavClickChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        appSettingNavigationGraph(
            sessionViewModel = sessionViewModel,
            startDestination = session.startDestination,
            onActionBarChange = onActionBarChange,
            onActionBarTitleChange = onActionBarTitleChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
            onTopBarActionsChange = onTopBarActionsChange,
            onFabChange = onFabChange
        )
        composable(NavRoutes.Home.route) {
            Timber.tag(TAG).d("MainNavigationHost -> composable(Home.route) called")
            BarNavigationHost(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }*/
        // Dashboarding Screen:
        composable(NavRoutes.Dashboarding.route) {
            // dashboarding: TOTALS: Congregations: Groups, Members; Territories; Ministries: Territories, Members and etc.
            Timber.tag(TAG)
                .d("Navigation Graph: to DashboardingScreen [route = '%s']", it.destination.route)
            // https://stackoverflow.com/questions/68857820/how-to-share-a-viewmodel-between-two-or-more-jetpack-composables-inside-a-compos
            // https://proandroiddev.com/jetpack-navigation-component-manual-implementation-of-multiple-back-stacks-62b33e95795c
            //val parentEntry = remember(it) { appState.barNavController.getBackStackEntry(NavRoutes.Dashboarding.route) }
            val congregationSharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            Timber.tag(TAG).d("Navigation Graph: get sharedViewModel")
            if (appState.congregationSharedViewModel.value == null) {
                appState.congregationSharedViewModel.value = congregationSharedViewModel
            }
            Timber.tag(TAG).d("Navigation Graph: sharedViewModel saved in appState")
            DashboardingScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // Congregating Screen:
        composable(
            route = NavRoutes.Congregating.route, arguments = NavRoutes.Congregating.arguments
        ) {
            // congregating: [Congregation, Members]; [Groups (favorite congregation), Members]
            Timber.tag(TAG)
                .d("Navigation Graph: to CongregatingScreen [route = '%s']", it.destination.route)
            val memberSharedViewModel =
                hiltViewModel<CurrentMemberViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            if (appState.memberSharedViewModel.value == null) {
                appState.memberSharedViewModel.value = memberSharedViewModel
            }
            CongregatingScreen(
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // Territoring Screen:
        composable(
            route = NavRoutes.Territoring.route, arguments = NavRoutes.Territoring.arguments
        ) {
            // territoring: Territories Grid [hand_out, at_work, idle], Territory Details
            Timber.tag(TAG)
                .d("Navigation Graph: to TerritoringScreen [route = '%s']", it.destination.route)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            TerritoringScreen(
                //sharedViewModel = sharedViewModel,
                territoriesGridViewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarTitleChange = onActionBarTitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(route = NavRoutes.HandOutConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to HandOutConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            HandOutConfirmationScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        composable(route = NavRoutes.ProcessConfirmation.route) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to ProcessConfirmationScreen [route = '%s']",
                    it.destination.route
                )
            // https://developer.android.com/jetpack/compose/libraries#hilt
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            // TODO Delete shared viewModel
            val territoriesGridViewModel =
                hiltViewModel<TerritoriesGridViewModelImpl>(it.rememberParentEntry(LocalAppState.current.mainNavController))
            ProcessConfirmationScreen(
                //sharedViewModel = sharedViewModel,
                viewModel = territoriesGridViewModel,
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange
            )
        }
        // Ministring Screen:
        composable(route = NavRoutes.Ministring.route, arguments = NavRoutes.Ministring.arguments) {
            // ministring:
            Timber.tag(TAG)
                .d("Navigation Graph: to MinistringScreen [route = '%s']", it.destination.route)
            val sharedViewModel =
                hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            //MinistringScreen(appState = appState, paddingValues = paddingValues,
            //                onActionBarTitleChange = onActionBarTitleChange,
            //                onTopBarActionsChange = onTopBarActionsChange)
        }
    }
}

