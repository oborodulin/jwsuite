package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.oborodulin.home.common.extensions.withSign
import com.oborodulin.home.common.ui.components.bar.action.BarActionItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import com.oborodulin.jwsuite.presentation_dashboard.R
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.CongregationTotalsUi
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
        topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_dashboarding),
        topBarNavImageVector = null,
        defTopBarActions = defTopBarActions,
        // context.toast("Settings button clicked...")
        barActionItems = listOf(
            BarActionItem(
                iconPainterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_geo_24,
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
                                        congregation = dashboardingUi.favoriteCongregation,
                                        congregationTotals = dashboardingUi.congregationTotals
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

// https://www.youtube.com/watch?v=ZotJ6eZ3TE8
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CongregationSection(
    navController: NavHostController,
    congregation: CongregationUi?,
    congregationTotals: CongregationTotalsUi?
) {
    val boxBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(com.oborodulin.home.common.R.color.purple_700),
            colorResource(com.oborodulin.home.common.R.color.purple_200)
        )
    )
    Column {
        congregation?.let {
            Text(
                text = it.congregationName,
                fontSize = 26.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 24.dp)
                .border(1.dp, colorResource(com.oborodulin.home.common.R.color.black))
                .weight(1f)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .background(boxBackgroundGradient)
                        .fillMaxHeight()
                        .width(12.dp)
                        .border(1.dp, colorResource(com.oborodulin.home.common.R.color.black))
                )
                congregationTotals?.let { totals ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        // totalGroups:
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(com.oborodulin.jwsuite.presentation.R.drawable.ic_group_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(12.dp)
                            )
                            Text(
                                text = stringResource(R.string.total_groups_hint),
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp)
                            )
                            BadgedBox(badge = {
                                totals.diffGroups.withSign()?.let { Badge { Text(it) } }
                            }) {
                                Text(
                                    text = totals.totalGroups.toString(),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                        // totalMembers:
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(12.dp)
                            )
                            Text(
                                text = stringResource(R.string.total_members_hint),
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp)
                            )
                            BadgedBox(badge = {
                                totals.diffMembers.withSign()?.let { Badge { Text(it) } }
                            }) {
                                Text(
                                    text = totals.totalMembers.toString(),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                        // totalFulltimeMembers:
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(12.dp)
                            )
                            Text(
                                text = stringResource(R.string.total_fulltime_members_hint),
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp)
                            )
                            BadgedBox(badge = {
                                totals.diffFulltimeMembers.withSign()?.let { Badge { Text(it) } }
                            }) {
                                Text(
                                    text = totals.totalFulltimeMembers.toString(),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregationSection() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            CongregationSection(
                navController = rememberNavController(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                congregationTotals = DashboardingViewModelImpl.previewCongregationTotalsModel(ctx)
            )
        }
    }
}
