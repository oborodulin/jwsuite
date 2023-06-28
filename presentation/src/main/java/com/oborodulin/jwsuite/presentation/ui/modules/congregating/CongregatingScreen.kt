package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.oborodulin.home.common.ui.components.TabRowItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.congregating.congregation.list.CongregationsListView
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListView
import kotlinx.coroutines.flow.collectLatest
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
            title = "Tab 1",
            view = {    CongregationsListView(
                appState = appState,
                navController = appState.commonNavController,
                congregationInput = CongregationInput(payerId)
            )
            },
        ),
        TabRowItem(
            title = "Tab 3",
            view = { TabScreen(text = "Tab 3") },
        )
    )
    HomeComposableTheme { //(darkTheme = true)
        ScaffoldComponent(
            appState = appState,
            nestedScrollConnection = nestedScrollConnection,
            topBarTitleId = com.oborodulin.jwsuite.presentation.R.string.nav_item_congregating,
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
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions, ),
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
