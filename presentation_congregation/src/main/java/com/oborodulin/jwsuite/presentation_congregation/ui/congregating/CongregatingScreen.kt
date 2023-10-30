package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

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
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list.CongregationsListView
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list.GroupsListView
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListView
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModelImpl
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Congregating.CongregatingScreen"

@Composable
fun CongregatingScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("CongregatingScreen(...) called")
    val appState = LocalAppState.current
    val context = LocalContext.current
    var tabType by rememberSaveable { mutableStateOf(CongregatingTabType.CONGREGATIONS.name) }
    val onTabChange: (CongregatingTabType) -> Unit = { tabType = it.name }
    val handleActionAdd = {
        appState.mainNavigate(
            when (CongregatingTabType.valueOf(tabType)) {
                CongregatingTabType.CONGREGATIONS -> NavRoutes.Congregation.routeForCongregation()
                CongregatingTabType.GROUPS -> NavRoutes.Group.routeForGroup()
                CongregatingTabType.MEMBERS -> NavRoutes.Member.routeForMember()
            }
        )
    }
    /*
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("CongregatingScreen: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(CongregatingUiAction.Init)
        }
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
            Timber.tag(TAG).d("Collect ui state flow: %s", state)

     */
    onActionBarTitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_congregating))
    onTopBarActionsChange {
        IconButton(onClick = handleActionAdd) { Icon(Icons.Outlined.Add, null) }
        IconButton(onClick = { context.toast("Settings button clicked...") }) {
            Icon(Icons.Outlined.Settings, null)
        }
    }
    CustomScrollableTabRow(
        listOf(
            TabRowItem(
                title = stringResource(R.string.congregation_tab_congregations),
                onClick = { onTabChange(CongregatingTabType.CONGREGATIONS) }
            ) {
                CongregationMembersView(
                    appState = appState,
                    //sharedViewModel = sharedViewModel,
                    membersListViewModel = membersListViewModel,
                    onActionBarSubtitleChange = onActionBarSubtitleChange
                )
            },
            TabRowItem(
                title = stringResource(R.string.congregation_tab_groups),
                onClick = { onTabChange(CongregatingTabType.GROUPS) }
            ) {
                GroupMembersView(
                    appState = appState,
                    //sharedViewModel = sharedViewModel,
                    membersListViewModel = membersListViewModel
                )
            },
            TabRowItem(
                title = stringResource(R.string.congregation_tab_members),
                onClick = { onTabChange(CongregatingTabType.MEMBERS) }
            ) {
                MembersView(
                    appState = appState,
                    //sharedViewModel = sharedViewModel,
                    membersListViewModel = membersListViewModel
                )
            }
        )
    )
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
fun CongregationMembersView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    membersListViewModel: MembersListViewModel,
    onActionBarSubtitleChange: (String) -> Unit
) {
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
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            CongregationsListView(
                appState = appState,
                onActionBarSubtitleChange = onActionBarSubtitleChange
            )//, sharedViewModel = sharedViewModel)
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
            MembersListView(appState = appState)//, sharedViewModel = sharedViewModel)
        }
        SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Composable
fun GroupMembersView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    membersListViewModel: MembersListViewModel
) {
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
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            GroupsListView(appState = appState)//, sharedViewModel = sharedViewModel)
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
            MembersListView(appState = appState)//, sharedViewModel = sharedViewModel)
        }
        SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Composable
fun MembersView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    membersListViewModel: MembersListViewModel
) {
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
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MembersListView(appState = appState)//, sharedViewModel = sharedViewModel)
        }
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

        }
        SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregatingScreen() {
    /*CongregatingScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
