package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.jwsuite.domain.util.TerritoryCategoryType
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list.GroupsListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single.TerritoryViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list.TerritoryStreetsListView
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoryDetailsScreen"

@Composable
fun TerritoryDetailsScreen(
    appState: AppState,
    territoryViewModel: TerritoryViewModel,
    territoryInput: TerritoryInput
) {
    Timber.tag(TAG).d("TerritoryDetailsScreen(...) called")
    val category by territoryViewModel.category.collectAsStateWithLifecycle()
    val tabStreets = TabRowItem(
        title = stringResource(R.string.territory_details_tab_streets),
        view = {
            TerritoryStreetsListView(
                navController = appState.commonNavController,
                territoryInput = territoryInput
            )
        }
    )
    val tabHouses = TabRowItem(
        title = stringResource(R.string.territory_details_tab_houses),
        view = {}
    )
    val tabEntraces = TabRowItem(
        title = stringResource(R.string.territory_details_tab_entrances),
        view = {}
    )
    val tabFloors = TabRowItem(
        title = stringResource(R.string.territory_details_tab_floors),
        view = {}
    )
    val tabRooms = TabRowItem(
        title = stringResource(R.string.territory_details_tab_rooms),
        view = {}
    )
    val tabs = when (category.item?.territoryCategoryCode) {
        TerritoryCategoryType.HOUSES -> listOf(tabStreets, tabHouses, tabEntraces)
        TerritoryCategoryType.FLOORS -> listOf(tabFloors)
        TerritoryCategoryType.ROOMS -> listOf(tabRooms)
        else -> emptyList()
    }
    JWSuiteTheme { //(darkTheme = true)
        ScaffoldComponent(
            appState = appState,
            topBarNavigationIcon = {
                IconButton(onClick = { appState.commonNavPopBackStack() }) {
                    Icon(Icons.Outlined.ArrowBack, null)
                }
            },
            topBarActions = {
                IconButton(onClick = { appState.backToBottomBarScreen() }) {
                    Icon(Icons.Outlined.Done, null)
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                CustomScrollableTabRow(tabs)
            }
        }
    }
    /*        }
            LaunchedEffect(Unit) {
                Timber.tag(TAG).d("TerritoryDetailsScreen: LaunchedEffect() AFTER collect ui state flow")
                viewModel.singleEventFlow.collectLatest {
                    Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
                    when (it) {
                        is CongregatingUiSingleEvent.OpenPayerScreen -> {
                            appState.commonNavController.navigate(it.navRoute)
                        }
                    }
                }
            }

     */
}

@Composable
fun CongregationMembersView(appState: AppState, membersListViewModel: MembersListViewModel) {
    Timber.tag(TAG).d("CongregationMembersView(...) called")
    val searchText by membersListViewModel.searchText.collectAsStateWithLifecycle()
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
                .weight(3.3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            CongregationsListView(appState = appState)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MembersListView(appState = appState)
        }
        SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Composable
fun GroupMembersView(appState: AppState, membersListViewModel: MembersListViewModel) {
    Timber.tag(TAG).d("GroupMembersView(...) called")
    val searchText by membersListViewModel.searchText.collectAsStateWithLifecycle()
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
                .weight(3.3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            GroupsListView(appState = appState)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(6.7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MembersListView(appState = appState)
        }
        SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Composable
fun MembersView(appState: AppState, membersListViewModel: MembersListViewModel) {
    Timber.tag(TAG).d("MembersView(...) called")
    val searchText by membersListViewModel.searchText.collectAsStateWithLifecycle()
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
                .weight(6.7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MembersListView(appState = appState)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3.3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {

        }
        SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryDetailsScreen() {
    /*TerritoryDetailsScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
