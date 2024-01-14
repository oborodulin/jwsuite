package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.oborodulin.home.common.ui.components.bar.action.BarActionItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Dashboarding.ui.DashboardingScreen"

@Composable
fun DashboardingScreen(
    viewModel: DashboardingViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {}
) {
    Timber.tag(TAG).d("DashboardingScreen(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DashboardingScreen -> LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(DashboardingUiAction.Init)
    }
    ScaffoldComponent(
        topBarTitle = stringResource(R.string.nav_item_dashboarding),
        topBarNavImageVector = null,
        defTopBarActions = defTopBarActions,
        // context.toast("Settings button clicked...")
        barActionItems = listOf(
            BarActionItem(
                iconPainterResId = R.drawable.ic_geo_24,
                cntDescResId = NavRoutes.Geo.titleResId,
                userRoles = listOf(MemberRoleType.TERRITORIES.name)
            ) { appState.mainNavigate(NavRoutes.Geo.route) },
            BarActionItem(
                iconImageVector = Icons.Outlined.Settings,
                cntDescResId = NavRoutes.Settings.titleResId
            ) { appState.mainNavigate(NavRoutes.Settings.route) }
        ),
        bottomBar = bottomBar,
        userRoles = session.userRoles
    ) { innerPadding ->
        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
            if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
            /*onActionBarChange(null)
            onActionBarTitleChange(stringResource(R.string.nav_item_dashboarding))
            onTopBarActionsChange(true) {
                IconButton(onClick = { appState.mainNavigate(NavRoutes.Geo.route) }) {
                    Icon(
                        painterResource(R.drawable.ic_geo_24), stringResource(NavRoutes.Geo.titleResId)
                    )
                }
                // context.toast("Settings button clicked...")
                IconButton(onClick = { appState.mainNavigate(NavRoutes.Settings.route) }) {
                    Icon(Icons.Outlined.Settings, stringResource(NavRoutes.Settings.titleResId))
                }
            }*/
            //onFabChange {}
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        CommonScreen(state = state) { dashboardingUi ->
                            dashboardingUi.favoriteCongregation?.let { favorite ->
                                appState.congregationSharedViewModel.value?.submitData(favorite.toCongregationsListItem())
                                appState.actionBarSubtitle.value = favorite.congregationName
                                //onActionBarSubtitleChange(favorite.congregationName)
                                val currentCongregation =
                                    appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
                                Timber.tag(TAG).d(
                                    "DashboardingScreen: appState.sharedViewModel.value = %s; currentCongregation = %s",
                                    appState.congregationSharedViewModel.value, currentCongregation
                                )
                            }
                            when {
                                session.containsRole(MemberRoleType.REPORTS) ->
                                    CongregationSection(
                                        navController = appState.mainNavController,
                                        congregation = dashboardingUi.favoriteCongregation
                                    )

                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("DashboardingScreen -> LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is DashboardingUiSingleEvent.OpenCongregationScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun CongregationSection(
    navController: NavHostController,
    congregation: CongregationUi?
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            if (congregation != null) {
                Text(text = congregation.congregationName)
            } else {
                //Text(text = stringResource(R.string.congregations_list_empty_text))
            }
        }

    }
}