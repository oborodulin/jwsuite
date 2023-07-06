package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.oborodulin.home.common.ui.components.TabRowItem
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsList
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListView
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Congregating.ui.CongregatingScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CongregatingScreen(
    viewModel: CongregatingViewModelImpl = hiltViewModel(),
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
        viewModel.uiStateFlow.collectAsState().value.let { state ->
            Timber.tag(TAG).d("Collect ui state flow: %s", state)

     */
    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(R.string.congregation_tab_members),
            view = {
                CongregationMembersView(appState = appState)
            },
        ),
        TabRowItem(
            title = stringResource(R.string.congregation_tab_groups),
            view = { TabScreen(text = "Tab 3") },
        )
    )
    HomeComposableTheme { //(darkTheme = true)
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
            val pagerState = rememberPagerState(initialPage = 0)
            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            //https://github.com/google/accompanist/issues/1267
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            icon = {
                                Icon(imageVector = item.icon, contentDescription = "")
                            },
                            text = {
                                Text(
                                    text = item.title,
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
                viewModel = meterValuesListViewModel,
                navController = navController,
                payerInput = congregationInput
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregationsCongregating() {
    CongregationsList(
        congregations = CongregationsListViewModelImpl.previewList(LocalContext.current),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onFavorite = {},
        onClick = {},
        onEdit = {},
        onDelete = {})
}