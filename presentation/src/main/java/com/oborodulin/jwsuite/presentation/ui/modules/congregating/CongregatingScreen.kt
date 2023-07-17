package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.TabRowItem
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list.GroupsListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListView
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Congregating.CongregatingScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CongregatingScreen(
    //viewModel: CongregatingViewModelImpl = hiltViewModel(),
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("CongregatingScreen(...) called")
    val context = LocalContext.current
    /*
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("CongregatingScreen: LaunchedEffect() BEFORE collect ui state flow")
            viewModel.submitAction(CongregatingUiAction.Init)
        }
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
            Timber.tag(TAG).d("Collect ui state flow: %s", state)

     */
    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(R.string.congregation_tab_members),
            view = { CongregationMembersView(appState = appState) },
        ),
        TabRowItem(
            title = stringResource(R.string.congregation_tab_groups),
            view = { GroupMembersView(appState = appState) },
        )
    )
    JWSuiteTheme { //(darkTheme = true)
        ScaffoldComponent(
            appState = appState,
            nestedScrollConnection = nestedScrollConnection,
            topBarTitleId = R.string.nav_item_congregating,
            topBarActions = {
                IconButton(onClick = { appState.commonNavController.navigate(NavRoutes.Congregation.routeForCongregation()) }) {
                    Icon(Icons.Outlined.Add, null)
                }
                IconButton(onClick = { context.toast("Settings button clicked...") }) {
                    Icon(Icons.Outlined.Settings, null)
                }
            },
            bottomBar = bottomBar
        ) {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(50.dp)
                    /*indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            //https://github.com/google/accompanist/issues/1267
                            //Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },*/
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            modifier = Modifier.height(50.dp),
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            icon = {
                                item.icon?.let { icon ->
                                    Icon(imageVector = icon, contentDescription = "")
                                }
                            },
                            text = {
                                Text(
                                    text = item.title,
                                    style = if (pagerState.currentPage == index) Typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ) else Typography.bodyLarge,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    pageCount = tabRowItems.size,
                    state = pagerState,
                ) {
                    tabRowItems[pagerState.currentPage].view()
                }
            }
        }
    }
    /*        }
            LaunchedEffect(Unit) {
                Timber.tag(TAG).d("CongregatingScreen: LaunchedEffect() AFTER collect ui state flow")
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
fun CongregationMembersView(appState: AppState) {
    Timber.tag(TAG).d("CongregationMembersView(...) called")
    val searchMemberState = remember { mutableStateOf(TextFieldValue("")) }
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
            CongregationsListView(
                appState = appState,
                navController = appState.commonNavController
            )
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
            MembersListView(
                navController = appState.commonNavController,
                searchState = searchMemberState
            )
        }
        SearchComponent(searchMemberState)
    }
}

@Composable
fun GroupMembersView(appState: AppState) {
    Timber.tag(TAG).d("GroupMembersView(...) called")
    val searchMemberState = remember { mutableStateOf(TextFieldValue("")) }
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
            GroupsListView(navController = appState.commonNavController)
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
            MembersListView(
                navController = appState.commonNavController,
                searchState = searchMemberState
            )
        }
        SearchComponent(searchMemberState)
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
