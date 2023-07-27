package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.toCongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Dashboarding.ui.DashboardingScreen"

@Composable
fun DashboardingScreen(
    sharedViewModel: FavoriteCongregationViewModelImpl = hiltViewModel(),
    viewModel: DashboardingViewModelImpl = hiltViewModel(),
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("DashboardingScreen(...) called")
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DashboardingScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(DashboardingUiAction.Init)
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                nestedScrollConnection = nestedScrollConnection,
                topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_dashboarding,
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            CommonScreen(state = state) { dashboardingUi ->
                                dashboardingUi.favoriteCongregation?.let { favorite ->
                                    sharedViewModel.submitData(favorite.toCongregationsListItem())
                                    appState.actionBarSubtitle.value = favorite.congregationName
                                }
                                CongregationSection(
                                    navController = appState.commonNavController,
                                    congregation = dashboardingUi.favoriteCongregation
                                )
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("DashboardingScreen: LaunchedEffect() AFTER collect ui state flow")
            viewModel.singleEventFlow.collectLatest {
                Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
                when (it) {
                    is DashboardingUiSingleEvent.OpenCongregationScreen -> {
                        appState.commonNavController.navigate(it.navRoute)
                    }
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
                Text(text = stringResource(R.string.congregations_list_empty_text))
            }
        }

    }
}