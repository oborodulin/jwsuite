package com.oborodulin.jwsuite.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oborodulin.jwsuite.presentation.extensions.rememberParentEntry
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
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
import com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement.DataManagementScreen
import com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting.DashboardSettingScreen
import com.oborodulin.jwsuite.presentation_dashboard.ui.database.send.DatabaseSendScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.GeoScreen
import com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single.CountryScreen
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
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses.ReportHousesScreen
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms.ReportRoomsScreen
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.single.MemberReportScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.TerritoringScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.TerritoryHouseScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.TerritoryRoomScreen
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting.TerritorySettingScreen
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
    defTopBarActions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    Timber.tag(TAG).d("MainNavigationHost(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    //val context = LocalContext.current
    Timber.tag(TAG).d(
        "MainNavigationHost: session.startDestination = %s",
        session.startDestination
    ) //session.mainRoute = %s", session.mainRoute)
    NavHost(
        navController = appState.mainNavController,// .rootNavController,
        route = Graph.MAIN,
        startDestination = session.startDestination//, NavRoutes.Login.route,//session.mainRoute, //NavRoutes.Home.route,
        //modifier = Modifier.padding(innerPadding)
    ) {
        Timber.tag(TAG).d("MainNavigationHost -> NavHost(...)")
        // AUTH Nav Graph:
        composable(route = NavRoutes.Login.route) {
            Timber.tag(TAG)
                .d("Navigation Graph: to LoginScreen [route = '%s']", it.destination.route)
            LoginScreen(sessionViewModel)
        }
        composable(route = NavRoutes.Signup.route) {
            Timber.tag(TAG)
                .d("Navigation Graph: to SignupScreen [route = '%s']", it.destination.route)
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
                defTopBarActions = defTopBarActions
            )
        }
        composable(route = NavRoutes.Group.route, arguments = NavRoutes.Group.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to GroupScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Group.arguments.firstOrNull()
            )
            //onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            GroupScreen(
                //sharedViewModel = sharedViewModel,
                groupInput = NavRoutes.Group.fromEntry(it),
                defTopBarActions = defTopBarActions
            )
        }
        composable(route = NavRoutes.Member.route, arguments = NavRoutes.Member.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to MemberScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.Member.arguments.firstOrNull()
            )
            //onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val sharedViewModel = hiltViewModel<FavoriteCongregationViewModelImpl>(it.rememberParentEntry(appState.navBarNavController))
            MemberScreen(
                //sharedViewModel = sharedViewModel,
                memberInput = NavRoutes.Member.fromEntry(it),
                defTopBarActions = defTopBarActions
            )
        }
        composable(route = NavRoutes.MemberRole.route, arguments = NavRoutes.MemberRole.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to MemberRoleScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.MemberRole.arguments.firstOrNull()
            )
            //onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            //val membersListViewModel = hiltViewModel<MembersListViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            MemberRoleScreen(
                //sharedViewModel = sharedViewModel,
                memberRoleInput = NavRoutes.MemberRole.fromEntry(it),
                defTopBarActions = defTopBarActions
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
                //viewModel = territoryViewModel,
                territoryInput = NavRoutes.Territory.fromEntry(it),
                defTopBarActions = defTopBarActions
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
                //territoryViewModel = territoryViewModel,
                territoryInput = NavRoutes.TerritoryDetails.fromEntry(it),
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
            )
        }

        // GEO Nav Graph:
        // GeoScreen:
        composable(route = NavRoutes.Geo.route, arguments = NavRoutes.Geo.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to GeoScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.Geo.arguments
            )
            //onActionBarSubtitleChange("")
            GeoScreen(defTopBarActions = defTopBarActions)
        }
        // CountryScreen:
        composable(route = NavRoutes.Country.route, arguments = NavRoutes.Country.arguments) {
            Timber.tag(TAG).d(
                "Navigation Graph: to CountryScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.Country.arguments
            )
            CountryScreen(
                countryInput = NavRoutes.Country.fromEntry(it),
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions
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
            HousingScreen(defTopBarActions = defTopBarActions)
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
                defTopBarActions = defTopBarActions
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
                defTopBarActions = defTopBarActions/*,
                onActionBarChange = onActionBarChange,
                onActionBarSubtitleChange = onActionBarSubtitleChange,
                onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
                onTopBarActionsChange = onTopBarActionsChange,
                onFabChange = onFabChange*/
            )
        }

        // REPORTING Nav Graph:
        composable(
            route = NavRoutes.ReportHouses.route, arguments = NavRoutes.ReportHouses.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to ReportHousesScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.ReportHouses.arguments.firstOrNull()
                )
            ReportHousesScreen(
                territoryInput = NavRoutes.ReportHouses.fromEntry(it),
                defTopBarActions = defTopBarActions
            )
        }
        composable(
            route = NavRoutes.ReportRooms.route, arguments = NavRoutes.ReportRooms.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to ReportRoomsScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.ReportRooms.arguments.firstOrNull()
                )
            ReportRoomsScreen(
                territoryInput = NavRoutes.ReportRooms.fromEntry(it),
                defTopBarActions = defTopBarActions
            )
        }
        composable(
            route = NavRoutes.MemberReport.route, arguments = NavRoutes.MemberReport.arguments
        ) {
            Timber.tag(TAG)
                .d(
                    "Navigation Graph: to MemberReportScreen [route = '%s', arguments = '%s']",
                    it.destination.route, NavRoutes.MemberReport.arguments.firstOrNull()
                )
            MemberReportScreen(
                memberReportInput = NavRoutes.MemberReport.fromEntry(it),
                defTopBarActions = defTopBarActions
            )
        }

        // APP SETTINGS Nav Graph:
        composable(
            route = NavRoutes.DashboardSettings.route,
            arguments = NavRoutes.DashboardSettings.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to DashboardSettingScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.DashboardSettings.arguments.firstOrNull()
            )
            DashboardSettingScreen(
                sessionViewModel = sessionViewModel,
                defTopBarActions = defTopBarActions
            )
        }

        composable(
            route = NavRoutes.TerritorySettings.route,
            arguments = NavRoutes.TerritorySettings.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to TerritorySettingScreen [route = '%s', arguments = '%s']",
                it.destination.route,
                NavRoutes.TerritorySettings.arguments.firstOrNull()
            )
            TerritorySettingScreen(defTopBarActions = defTopBarActions)
        }

        // Data Management Nav Graph:
        composable(
            route = NavRoutes.DataManagement.route, arguments = NavRoutes.DataManagement.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to DataManagementScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.DataManagement.arguments.firstOrNull()
            )
            DataManagementScreen(defTopBarActions = defTopBarActions)
        }

        // DATABASE Nav Graph:
        composable(
            route = NavRoutes.DatabaseSend.route, arguments = NavRoutes.DatabaseSend.arguments
        ) {
            Timber.tag(TAG).d(
                "Navigation Graph: to DatabaseSendScreen [route = '%s', arguments = '%s']",
                it.destination.route, NavRoutes.DatabaseSend.arguments.firstOrNull()
            )
            DatabaseSendScreen(defTopBarActions = defTopBarActions)
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
            //onActionBarSubtitleChange(appState.actionBarSubtitle.value)
            DashboardingScreen(defTopBarActions = defTopBarActions, bottomBar = bottomBar)
        }
        // Congregating Screen:
        composable(
            route = NavRoutes.Congregating.route, arguments = NavRoutes.Congregating.arguments
        ) {
            // congregating: [Congregation, Members]; [Groups (favorite congregation), Members [Admin: Roles]]
            Timber.tag(TAG)
                .d("Navigation Graph: to CongregatingScreen [route = '%s']", it.destination.route)
            val memberSharedViewModel =
                hiltViewModel<CurrentMemberViewModelImpl>(it.rememberParentEntry(appState.barNavController))
            if (appState.memberSharedViewModel.value == null) {
                appState.memberSharedViewModel.value = memberSharedViewModel
            }
            CongregatingScreen(defTopBarActions = defTopBarActions, bottomBar = bottomBar)
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
                defTopBarActions = defTopBarActions,
                bottomBar = bottomBar
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
