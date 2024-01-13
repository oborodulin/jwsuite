package com.oborodulin.jwsuite.presentation_geo.ui.geo

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.fab.ExtFabComponent
import com.oborodulin.home.common.ui.components.search.SearchViewModelComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list.RegionsListView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list.RegionDistrictsListView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListView
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import timber.log.Timber
import java.util.UUID

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Geo.GeoScreen"

@Composable
fun GeoScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    //nestedScrollConnection: NestedScrollConnection,
    //bottomBar: @Composable () -> Unit
    regionsListViewModel: RegionsListViewModelImpl = hiltViewModel(),
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl = hiltViewModel(),
    localitiesListViewModel: LocalitiesListViewModelImpl = hiltViewModel(),
    localityDistrictsListViewModel: LocalityDistrictsListViewModelImpl = hiltViewModel(),
    microdistrictsListViewModel: MicrodistrictsListViewModelImpl = hiltViewModel(),
    streetsListViewModel: StreetsListViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("GeoScreen(...) called")
    val appState = LocalAppState.current
    // Action Bar:
    var actionBar: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onActionBarChange: (@Composable (() -> Unit)?) -> Unit = { actionBar = it }
    // Action Bar -> Actions:
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    // FAB:
    var floatingActionButton: @Composable () -> Unit by remember { mutableStateOf({}) }
    val onFabChange: (@Composable () -> Unit) -> Unit = { floatingActionButton = it }

    //var viewModel: ListViewModeled<List<ListItemModel>, UiAction, UiSingleEvent>
    //var placeholderResId: Int
    var tabType by rememberSaveable { mutableStateOf(GeoTabType.REGIONS.name) }
    val onTabChange: (GeoTabType) -> Unit = { tabType = it.name }
    val handleActionAdd = {
        appState.mainNavigate(
            when (GeoTabType.valueOf(tabType)) {
                GeoTabType.REGIONS -> NavRoutes.Region.routeForRegion()
                GeoTabType.REGION_DISTRICTS -> NavRoutes.RegionDistrict.routeForRegionDistrict()
                GeoTabType.LOCALITIES -> NavRoutes.Locality.routeForLocality()
                GeoTabType.LOCALITY_DISTRICTS -> NavRoutes.LocalityDistrict.routeForLocalityDistrict()
                GeoTabType.MICRODISTRICTS -> NavRoutes.Microdistrict.routeForMicrodistrict()
                GeoTabType.STREETS -> NavRoutes.Street.routeForStreet()
            }
        )
    }
    // Searching:
    var isShowSearchBar by rememberSaveable { mutableStateOf(false) }
    val handleActionSearch = { isShowSearchBar = true }
    when (isShowSearchBar) {
        true -> {
            onActionBarChange {
                when (GeoTabType.valueOf(tabType)) {
                    GeoTabType.REGIONS -> SearchViewModelComponent(
                        viewModel = regionsListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )

                    GeoTabType.REGION_DISTRICTS -> SearchViewModelComponent(
                        viewModel = regionDistrictsListViewModel,
                        placeholderResId = R.string.region_district_search_placeholder
                    )

                    GeoTabType.LOCALITIES -> SearchViewModelComponent(
                        viewModel = localitiesListViewModel,
                        placeholderResId = R.string.locality_search_placeholder
                    )

                    GeoTabType.LOCALITY_DISTRICTS -> SearchViewModelComponent(
                        viewModel = localityDistrictsListViewModel,
                        placeholderResId = R.string.locality_district_search_placeholder
                    )

                    GeoTabType.MICRODISTRICTS -> SearchViewModelComponent(
                        viewModel = microdistrictsListViewModel,
                        placeholderResId = R.string.microdistrict_search_placeholder
                    )

                    GeoTabType.STREETS -> SearchViewModelComponent(
                        viewModel = streetsListViewModel,
                        placeholderResId = R.string.street_search_placeholder
                    )
                }
            }
            onTopBarActionsChange {}
        }

        false -> {
            onActionBarChange(null)
            onTopBarActionsChange {
                IconButton(onClick = handleActionSearch) { Icon(Icons.Outlined.Search, null) }
                IconButton(onClick = handleActionAdd) { Icon(Icons.Outlined.Add, null) }
            }
        }
    }
    var streetTabType by rememberSaveable { mutableStateOf(GeoStreetDistrictTabType.LOCALITY_DISTRICTS.name) }
    val onStreetTabChange: (GeoStreetDistrictTabType) -> Unit = { streetTabType = it.name }
    val areSingleSelected by streetsListViewModel.areSingleSelected.collectAsStateWithLifecycle()
    Timber.tag(TAG).d("Streets List View Model: areSingleSelected = %s", areSingleSelected)
    onFabChange {
        when (GeoTabType.valueOf(tabType)) {
            GeoTabType.STREETS ->
                @Composable {
                    when (GeoStreetDistrictTabType.valueOf(streetTabType)) {
                        GeoStreetDistrictTabType.LOCALITY_DISTRICTS -> ExtFabComponent(
                            enabled = areSingleSelected,
                            imageVector = Icons.Outlined.Add,
                            textResId = R.string.fab_street_locality_district_text
                        ) {
                            val selectedStreet = streetsListViewModel.singleSelectedItem()
                            Timber.tag(TAG).d("selectedStreet = %s", selectedStreet)
                            appState.mainNavigate(
                                NavRoutes.StreetLocalityDistrict.routeForStreetLocalityDistrict(
                                    NavigationInput.StreetLocalityDistrictInput(streetId = selectedStreet?.itemId!!)
                                )
                            )
                        }

                        GeoStreetDistrictTabType.MICRODISTRICTS -> ExtFabComponent(
                            enabled = areSingleSelected,
                            imageVector = Icons.Outlined.Add,
                            textResId = R.string.fab_street_microdistrict_text
                        ) {
                            val selectedStreet = streetsListViewModel.singleSelectedItem()
                            Timber.tag(TAG).d("selectedStreet = %s", selectedStreet)
                            appState.mainNavigate(
                                NavRoutes.StreetMicrodistrict.routeForStreetMicrodistrict(
                                    NavigationInput.StreetMicrodistrictInput(streetId = selectedStreet?.itemId!!)
                                )
                            )
                        }
                    }
                }

            else -> @Composable {
            }
        }
    }
    /*
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("GeoScreen -> LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(GeoUiAction.Init)
        }
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
            if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)

     */
    // Scaffold Hoisting:
    //onActionBarTitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_geo))
    //onActionBarSubtitleChange("")
    //onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
    Timber.tag(TAG).d("GeoScreen: appState.handleTopBarNavClick assign")
    //appState.handleTopBarNavClick.value =
    val onTopBarNavClick by remember {
        mutableStateOf({
            if (isShowSearchBar) {
                isShowSearchBar = false
                when (GeoTabType.valueOf(tabType)) {
                    GeoTabType.REGIONS -> regionsListViewModel.clearSearchText()
                    GeoTabType.REGION_DISTRICTS -> regionDistrictsListViewModel.clearSearchText()
                    GeoTabType.LOCALITIES -> localitiesListViewModel.clearSearchText()
                    GeoTabType.LOCALITY_DISTRICTS -> localityDistrictsListViewModel.clearSearchText()
                    GeoTabType.MICRODISTRICTS -> microdistrictsListViewModel.clearSearchText()
                    GeoTabType.STREETS -> streetsListViewModel.clearSearchText()
                }
            } else {
                appState.mainNavigateUp()
                appState.navigateToBarRoute(NavRoutes.Dashboarding.route)
            } //backToBottomBarScreen()
        })
    }
    // https://stackoverflow.com/questions/69151521/how-to-override-the-system-onbackpress-in-jetpack-compose
    BackHandler { onTopBarNavClick.invoke() } // appState.handleTopBarNavClick.value
    /*onTopBarNavClickChange {
        Timber.tag(TAG).d("GeoScreen -> onTopBarNavClickChange()")
        appState.mainNavigateUp()// .backToBottomBarScreen()
    }*/
    //appState.actionBarTitle.value = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_geo)
    ScaffoldComponent(
        topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_geo),
        topBarSubtitle = "",
        actionBar = actionBar,
        onTopBarNavClick = onTopBarNavClick,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions,
        floatingActionButton = floatingActionButton
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CustomScrollableTabRow(
                listOf(
                    TabRowItem(
                        title = stringResource(R.string.geo_tab_regions),
                        onClick = { onTabChange(GeoTabType.REGIONS) }
                    ) {
                        RegionRegionDistrictsLocalitiesView(
                            appState = appState,
                            regionsListViewModel = regionsListViewModel
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.geo_tab_region_districts),
                        onClick = { onTabChange(GeoTabType.REGION_DISTRICTS) }
                    ) {
                        RegionDistrictsLocalitiesView(
                            appState = appState,
                            regionDistrictsListViewModel = regionDistrictsListViewModel
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.geo_tab_localities),
                        onClick = { onTabChange(GeoTabType.LOCALITIES) }
                    ) {
                        LocalitiesLocalitiesDistrictsMicrodistrictsView(
                            appState = appState,
                            localitiesListViewModel = localitiesListViewModel
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.geo_tab_locality_districts),
                        onClick = { onTabChange(GeoTabType.LOCALITY_DISTRICTS) }
                    ) {
                        LocalitiesDistrictsMicrodistrictsStreetsView(
                            appState = appState,
                            //sharedViewModel = sharedViewModel,
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.geo_tab_microdistricts),
                        onClick = { onTabChange(GeoTabType.MICRODISTRICTS) }
                    ) {
                        MicrodistrictsStreetsView(
                            appState = appState,
                            //sharedViewModel = sharedViewModel,
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.geo_tab_streets),
                        onClick = { onTabChange(GeoTabType.STREETS) }
                    ) {
                        StreetsView(
                            appState = appState,
                            onStreetTabChange = onStreetTabChange
                            //sharedViewModel = sharedViewModel,
                        )
                    }
                )
            )
        }
    }
// https://stackoverflow.com/questions/73034912/jetpack-compose-how-to-detect-when-tabrow-inside-horizontalpager-is-visible-and
// Page change callback
    /*    LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                when (page) {
                    0 -> viewModel.getAllNotes() // First page
                    1 -> // Second page
                    else -> // Other pages
                }
            }
        }*/
}

@Composable
fun RegionRegionDistrictsLocalitiesView(
    appState: AppState,
    regionsListViewModel: RegionsListViewModelImpl
) {
    Timber.tag(TAG).d("RegionRegionDistrictsLocalitiesView(...) called")
    var selectedRegionId: UUID? by remember { mutableStateOf(regionsListViewModel.singleSelectedItem()?.itemId) }
    val onClickTab = { selectedRegionId = regionsListViewModel.singleSelectedItem()?.itemId }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            RegionsListView(navController = appState.mainNavController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CustomScrollableTabRow(
                    listOf(
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_region_districts),
                            onClick = onClickTab
                        ) {
                            RegionDistrictsListView(
                                navController = appState.mainNavController,
                                regionInput = selectedRegionId?.let { NavigationInput.RegionInput(it) },
                                isEditableList = false
                            )
                        },
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_localities),
                            onClick = onClickTab
                        ) {
                            LocalitiesListView(
                                navController = appState.mainNavController,
                                regionInput = selectedRegionId?.let { NavigationInput.RegionInput(it) },
                                isEditableList = false
                            )
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun RegionDistrictsLocalitiesView(
    appState: AppState,
    regionDistrictsListViewModel: RegionDistrictsListViewModelImpl
) {
    Timber.tag(TAG).d("RegionDistrictsLocalitiesView(...) called")
    val selectedRegionDistrictId = regionDistrictsListViewModel.singleSelectedItem()?.itemId
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            RegionDistrictsListView(navController = appState.mainNavController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            LocalitiesListView(
                navController = appState.mainNavController,
                regionDistrictInput = selectedRegionDistrictId?.let {
                    NavigationInput.RegionDistrictInput(it)
                },
                isEditableList = false
            )
        }
    }
}

@Composable
fun LocalitiesLocalitiesDistrictsMicrodistrictsView(
    appState: AppState,
    localitiesListViewModel: LocalitiesListViewModelImpl
) {
    Timber.tag(TAG).d("LocalitiesLocalitiesDistrictsMicrodistrictsView(...) called")
    var selectedLocalityId: UUID? by remember { mutableStateOf(localitiesListViewModel.singleSelectedItem()?.itemId) }
    val onClickTab = { selectedLocalityId = localitiesListViewModel.singleSelectedItem()?.itemId }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            LocalitiesListView(navController = appState.mainNavController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CustomScrollableTabRow(
                    listOf(
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_locality_districts),
                            onClick = onClickTab
                        ) {
                            LocalityDistrictsListView(
                                navController = appState.mainNavController,
                                localityInput = selectedLocalityId?.let {
                                    NavigationInput.LocalityInput(it)
                                },
                                isEditableList = false
                            )
                        },
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_microdistricts),
                            onClick = onClickTab
                        ) {
                            MicrodistrictsListView(
                                navController = appState.mainNavController,
                                localityInput = selectedLocalityId?.let {
                                    NavigationInput.LocalityInput(it)
                                },
                                isEditableList = false
                            )
                        },
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_streets),
                            onClick = onClickTab
                        ) {
                            StreetsListView(
                                navController = appState.mainNavController,
                                localityInput = selectedLocalityId?.let {
                                    NavigationInput.LocalityInput(it)
                                },
                                isEditableList = false
                            )
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun LocalitiesDistrictsMicrodistrictsStreetsView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //membersListViewModel: MembersListViewModel
) {
    Timber.tag(TAG).d("LocalitiesDistrictsMicrodistrictsStreetsView(...) called")
    //val searchText by membersListViewModel.searchText.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            LocalityDistrictsListView(navController = appState.mainNavController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CustomScrollableTabRow(
                    listOf(
                        TabRowItem(title = stringResource(R.string.geo_tab_microdistricts)) {
                            MicrodistrictsListView(
                                navController = appState.mainNavController,
                                isEditableList = false
                            )
                        },
                        TabRowItem(title = stringResource(R.string.geo_tab_streets)) {
                            StreetsListView(
                                navController = appState.mainNavController,
                                isEditableList = false
                            )
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun MicrodistrictsStreetsView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //membersListViewModel: MembersListViewModel
) {
    Timber.tag(TAG).d("MicrodistrictsStreetsView(...) called")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MicrodistrictsListView(navController = appState.mainNavController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            StreetsListView(
                navController = appState.mainNavController,
                isEditableList = false
            )
        }
    }
}

@Composable
fun StreetsView(
    appState: AppState,
    onStreetTabChange: (GeoStreetDistrictTabType) -> Unit
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //membersListViewModel: MembersListViewModel
) {
    Timber.tag(TAG).d("StreetsView(...) called")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            StreetsListView(navController = appState.mainNavController)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CustomScrollableTabRow(
                    listOf(
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_locality_districts),
                            onClick = { onStreetTabChange(GeoStreetDistrictTabType.LOCALITY_DISTRICTS) }) {
                            LocalityDistrictsListView(
                                navController = appState.mainNavController,
                                isEditableList = false
                            )
                        },
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_microdistricts),
                            onClick = { onStreetTabChange(GeoStreetDistrictTabType.MICRODISTRICTS) }) {
                            MicrodistrictsListView(
                                navController = appState.mainNavController,
                                isEditableList = false
                            )
                        }
                    )
                )
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGeoScreen() {
    /*GeoScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
