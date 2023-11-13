package com.oborodulin.jwsuite.presentation_geo.ui.geo

import android.content.res.Configuration
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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.fab.FabComponent
import com.oborodulin.home.common.ui.components.search.SearchBarComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
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
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("GeoScreen(...) called")
    val appState = LocalAppState.current
    val context = LocalContext.current
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
    var isShowSearchBar by rememberSaveable { mutableStateOf(false) }
    when (isShowSearchBar) {
        true ->
            onActionBarChange {
                when (GeoTabType.valueOf(tabType)) {
                    GeoTabType.REGIONS -> SearchBarComponent(
                        viewModel = regionsListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )

                    GeoTabType.REGION_DISTRICTS -> SearchBarComponent(
                        viewModel = regionDistrictsListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )

                    GeoTabType.LOCALITIES -> SearchBarComponent(
                        viewModel = localitiesListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )

                    GeoTabType.LOCALITY_DISTRICTS -> SearchBarComponent(
                        viewModel = localityDistrictsListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )

                    GeoTabType.MICRODISTRICTS -> SearchBarComponent(
                        viewModel = microdistrictsListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )

                    GeoTabType.STREETS -> SearchBarComponent(
                        viewModel = streetsListViewModel,
                        placeholderResId = R.string.region_search_placeholder
                    )
                }
            }

        false -> onActionBarChange(null)
    }

    val handleActionSearch = { isShowSearchBar = true }
    var streetTabType by rememberSaveable { mutableStateOf(GeoStreetDistrictTabType.LOCALITY_DISTRICTS.name) }
    val onStreetTabChange: (GeoStreetDistrictTabType) -> Unit = { streetTabType = it.name }
    onFabChange {
        when (GeoTabType.valueOf(tabType)) {
            GeoTabType.STREETS ->
                @Composable {
                    when (GeoStreetDistrictTabType.valueOf(streetTabType)) {
                        GeoStreetDistrictTabType.LOCALITY_DISTRICTS -> FabComponent(
                            enabled = true,
                            imageVector = Icons.Outlined.Add,
                            textResId = R.string.fab_street_locality_district_text
                        ) {
                            appState.mainNavigate(
                                NavRoutes.StreetLocalityDistrict.routeForStreetLocalityDistrict()
                            )
                        }

                        GeoStreetDistrictTabType.MICRODISTRICTS -> FabComponent(
                            enabled = true,
                            imageVector = Icons.Outlined.Add,
                            textResId = R.string.fab_street_microdistrict_text
                        ) {
                            appState.mainNavigate(
                                NavRoutes.StreetMicrodistrict.routeForStreetMicrodistrict()
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
            Timber.tag(TAG).d("Collect ui state flow: %s", state)

     */
    // Scaffold Hoisting:
    onActionBarTitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_geo))
    onActionBarSubtitleChange("")
    onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
    appState.handleTopBarNavClick.value =
        { if (isShowSearchBar) isShowSearchBar = false else appState.backToBottomBarScreen() }
    onTopBarNavClickChange {
        Timber.tag(TAG).d("GeoScreen -> onTopBarNavClickChange()")
        appState.mainNavigateUp()// .backToBottomBarScreen()
    }
    onTopBarActionsChange {
        IconButton(onClick = handleActionSearch) { Icon(Icons.Outlined.Search, null) }
        IconButton(onClick = handleActionAdd) { Icon(Icons.Outlined.Add, null) }
        /*IconButton(onClick = { context.toast("Settings action button clicked...") }) {
            Icon(Icons.Outlined.Settings, null)
        }*/
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CustomScrollableTabRow(
            listOf(
                TabRowItem(
                    title = stringResource(R.string.geo_tab_regions),
                    onClick = { onTabChange(GeoTabType.REGIONS) }
                ) { RegionRegionDistrictsLocalitiesView(appState = appState) },
                TabRowItem(
                    title = stringResource(R.string.geo_tab_region_districts),
                    onClick = { onTabChange(GeoTabType.REGION_DISTRICTS) }
                ) { RegionDistrictsLocalitiesView(appState = appState) },
                TabRowItem(
                    title = stringResource(R.string.geo_tab_localities),
                    onClick = { onTabChange(GeoTabType.LOCALITIES) }
                ) {},
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
fun RegionRegionDistrictsLocalitiesView(appState: AppState) {
    Timber.tag(TAG).d("RegionRegionDistrictsLocalitiesView(...) called")
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
                        TabRowItem(title = stringResource(R.string.geo_tab_region_districts)) {
                            RegionDistrictsListView(navController = appState.mainNavController)
                        },
                        TabRowItem(title = stringResource(R.string.geo_tab_localities)) {
                            LocalitiesListView(navController = appState.mainNavController)
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun RegionDistrictsLocalitiesView(appState: AppState) {
    Timber.tag(TAG).d("RegionDistrictsLocalitiesView(...) called")
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
            LocalitiesListView(navController = appState.mainNavController)
        }
    }
}

@Composable
fun LocalitiesLocalitiesDistrictsMicrodistrictsView(appState: AppState) {
    Timber.tag(TAG).d("LocalitiesLocalitiesDistrictsMicrodistrictsView(...) called")
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
                        TabRowItem(title = stringResource(R.string.geo_tab_locality_districts)) {
                            LocalityDistrictsListView(navController = appState.mainNavController)
                        },
                        TabRowItem(title = stringResource(R.string.geo_tab_microdistricts)) {
                            MicrodistrictsListView(navController = appState.mainNavController)
                        },
                        TabRowItem(title = stringResource(R.string.geo_tab_streets)) {
                            StreetsListView(navController = appState.mainNavController)
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
                            MicrodistrictsListView(navController = appState.mainNavController)
                        },
                        TabRowItem(title = stringResource(R.string.geo_tab_streets)) {
                            StreetsListView(navController = appState.mainNavController)
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
            StreetsListView(navController = appState.mainNavController)
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
                            LocalityDistrictsListView(navController = appState.mainNavController)
                        },
                        TabRowItem(
                            title = stringResource(R.string.geo_tab_microdistricts),
                            onClick = { onStreetTabChange(GeoStreetDistrictTabType.MICRODISTRICTS) }) {
                            MicrodistrictsListView(navController = appState.mainNavController)
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
