package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.components.ServiceSwitchComponent
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list.CongregationsListView
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list.CongregationsListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list.GroupsListView
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list.GroupsListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListView
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list.MemberRolesListView
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Congregating.CongregatingScreen"

@Composable
fun CongregatingScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    congregatingViewModel: CongregatingViewModelImpl = hiltViewModel(),
    congregationsListViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    groupsListViewModel: GroupsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("CongregatingScreen(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current

    // Action Bar:
    var actionBar: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onActionBarChange: (@Composable (() -> Unit)?) -> Unit = { actionBar = it }
    // Action Bar -> Actions:
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    // FAB:
    var floatingActionButton: @Composable () -> Unit by remember { mutableStateOf({}) }
    val onFabChange: (@Composable () -> Unit) -> Unit = { floatingActionButton = it }

    Timber.tag(TAG).d("Territoring: CollectAsStateWithLifecycle for all fields")
    val isService by congregatingViewModel.isService.collectAsStateWithLifecycle()

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
            Timber.tag(TAG).d("CongregatingScreen -> LaunchedEffect()")
            viewModel.submitAction(CongregatingUiAction.Init)
        }
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
            if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)

     */
    //onActionBarChange(null)
    //onActionBarTitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_congregating))
    // Searching:
    var isShowSearchBar by rememberSaveable { mutableStateOf(false) }
    val handleActionSearch = { isShowSearchBar = true }
    when (isShowSearchBar) {
        true -> {
            onActionBarChange {
                when (CongregatingTabType.valueOf(tabType)) {
                    CongregatingTabType.CONGREGATIONS -> SearchViewModelComponent(
                        viewModel = congregationsListViewModel,
                        placeholderResId = R.string.congregation_search_placeholder
                    )

                    CongregatingTabType.GROUPS -> SearchViewModelComponent(
                        viewModel = groupsListViewModel,
                        placeholderResId = R.string.group_search_placeholder
                    )

                    CongregatingTabType.MEMBERS -> SearchViewModelComponent(
                        viewModel = membersListViewModel,
                        placeholderResId = R.string.member_search_placeholder
                    )
                }
            }
            onTopBarActionsChange {}
        }

        false -> {
            when {
                session.containsRole(MemberRoleType.ADMIN) -> onActionBarChange {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ServiceSwitchComponent(
                            viewModel = congregatingViewModel, inputWrapper = isService
                        )
                    }
                }

                else -> onActionBarChange(null)
            }

            onTopBarActionsChange {
                IconButton(onClick = handleActionSearch) { Icon(Icons.Outlined.Search, null) }
                IconButton(onClick = handleActionAdd) { Icon(Icons.Outlined.Add, null) }
            }
        }
    }
    val areSingleSelected by membersListViewModel.areSingleSelected.collectAsStateWithLifecycle()
    onFabChange {
        /*when (CongregatingTabType.valueOf(tabType)) {
            CongregatingTabType.CONGREGATIONS -> AddFabComponent(
                modifier = Modifier.padding(bottom = 44.dp, end = 24.dp)
            ) { appState.mainNavigate(NavRoutes.Congregation.routeForCongregation()) }

            CongregatingTabType.GROUPS -> AddFabComponent(
                modifier = Modifier.padding(bottom = 44.dp, end = 24.dp)
            ) { appState.mainNavigate(NavRoutes.Group.routeForGroup()) }*/

        if (CongregatingTabType.valueOf(tabType) == CongregatingTabType.MEMBERS) {
            /*CongregatingTabType.MEMBERS ->
                when {
                    session.containsRole(MemberRoleType.ADMIN) -> ExtFabComponent(*/
            if (session.containsRole(MemberRoleType.ADMIN)) ExtFabComponent(
                modifier = Modifier.padding(bottom = 44.dp, end = 24.dp),
                enabled = true,//areSingleSelected,
                imageVector = Icons.Outlined.Add,
                textResId = R.string.fab_add_member_role_text
            ) { appState.mainNavigate(NavRoutes.MemberRole.routeForMemberRole()) }

            /*else -> AddFabComponent(
                modifier = Modifier.padding(bottom = 44.dp, end = 24.dp)
            ) { appState.mainNavigate(NavRoutes.Member.routeForMember()) }*/
        }
        //}
    }
    //onTopBarNavImageVectorChange(if (isShowSearchBar) Icons.Outlined.ArrowBack else null)
    val handleCloseAndClearSearch = {
        isShowSearchBar = false
        when (CongregatingTabType.valueOf(tabType)) {
            CongregatingTabType.CONGREGATIONS -> congregationsListViewModel.clearSearchText()
            CongregatingTabType.GROUPS -> groupsListViewModel.clearSearchText()
            CongregatingTabType.MEMBERS -> membersListViewModel.clearSearchText()
        }
    }
    appState.handleTopBarNavClick.value =
        {
            if (isShowSearchBar) {
                handleCloseAndClearSearch.invoke()
            }
        }
    // https://stackoverflow.com/questions/69151521/how-to-override-the-system-onbackpress-in-jetpack-compose
    BackHandler {
        if (isShowSearchBar) {
            handleCloseAndClearSearch.invoke()
        } else appState.mainNavigateUp()
    }
    ScaffoldComponent(
        topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_congregating),
        actionBar = actionBar,
        topBarNavImageVector = if (isShowSearchBar) Icons.Outlined.ArrowBack else null,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions,
        bottomBar = bottomBar,
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
                        title = stringResource(R.string.congregation_tab_congregations),
                        onClick = { onTabChange(CongregatingTabType.CONGREGATIONS) }
                    ) {
                        CongregationMembersView(
                            //appState = appState,
                            //sharedViewModel = sharedViewModel,
                            membersListViewModel = membersListViewModel,
                            isService = isService.value.toBoolean()//,
                            //onActionBarSubtitleChange = onActionBarSubtitleChange
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.congregation_tab_groups),
                        onClick = { onTabChange(CongregatingTabType.GROUPS) }
                    ) {
                        GroupMembersView(
                            appState = appState,
                            //sharedViewModel = sharedViewModel,
                            membersListViewModel = membersListViewModel,
                            isService = isService.value.toBoolean()
                        )
                    },
                    TabRowItem(
                        title = stringResource(R.string.congregation_tab_members),
                        onClick = { onTabChange(CongregatingTabType.MEMBERS) }
                    ) {
                        when {
                            session.containsRole(MemberRoleType.ADMIN) -> MemberRolesView(
                                appState = appState, membersListViewModel = membersListViewModel,
                                isService = isService.value.toBoolean()
                            )

                            else -> MembersView(
                                //appState = appState,
                                //sharedViewModel = sharedViewModel,
                                membersListViewModel = membersListViewModel
                            )
                        }
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
fun CongregationMembersView(
    //appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    congregationsListViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModel,
    isService: Boolean = false//,
    //onActionBarSubtitleChange: (String) -> Unit
) {
    Timber.tag(TAG).d("CongregationMembersView(...) called")
    val selectedCongregationId = congregationsListViewModel.singleSelectedItem()?.itemId
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
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            CongregationsListView(
                //appState = appState, onActionBarSubtitleChange = onActionBarSubtitleChange
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
            MembersListView(
                membersListViewModel = membersListViewModel,
                congregationInput = selectedCongregationId?.let {
                    NavigationInput.CongregationInput(it)
                },
                isService = isService,
                isEditableList = false
            )//, sharedViewModel = sharedViewModel)
        }
        //SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Composable
fun GroupMembersView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    groupsListViewModel: GroupsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersListViewModel,
    isService: Boolean = false
) {
    Timber.tag(TAG).d("GroupMembersView(...) called")
    val selectedGroupId = groupsListViewModel.singleSelectedItem()?.itemId
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
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            GroupsListView()
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
            MembersListView(
                membersListViewModel = membersListViewModel,
                groupInput = selectedGroupId?.let { NavigationInput.GroupInput(it) },
                isService = isService,
                isEditableList = false
            )//, sharedViewModel = sharedViewModel)
        }
        //SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
    }
}

@Composable
fun MemberRolesView(
    appState: AppState,
    membersListViewModel: MembersListViewModel,
    isService: Boolean = false
) {
    Timber.tag(TAG).d("MemberRolesView(...) called")
    val selectedMemberId = membersListViewModel.singleSelectedItem()?.itemId
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
            MembersListView(membersListViewModel = membersListViewModel, isService = isService)
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
            MemberRolesListView(
                appState = appState,
                memberInput = selectedMemberId?.let { NavigationInput.MemberInput(it) }
            )
        }
    }
}

@Composable
fun MembersView(
    //appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    membersListViewModel: MembersListViewModel
) {
    Timber.tag(TAG).d("MembersView(...) called")
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
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(6.18f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MembersListView(membersListViewModel = membersListViewModel, isService = false)
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
        //SearchComponent(searchText, onValueChange = membersListViewModel::onSearchTextChange)
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
