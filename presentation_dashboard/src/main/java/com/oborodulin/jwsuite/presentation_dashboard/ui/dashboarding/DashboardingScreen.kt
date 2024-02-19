package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.oborodulin.home.common.extensions.toShortFormatString
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
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.TerritoryTotalsUi
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.time.OffsetDateTime

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
    //Timber.tag(TAG).d("DashboardingScreen: session = %s", session)
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DashboardingScreen -> LaunchedEffect()")
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
                userRoles = listOf(MemberRoleType.ADMIN.name, MemberRoleType.TERRITORIES.name)
            ) { appState.mainNavigate(NavRoutes.Geo.route) },
            BarActionItem(
                iconImageVector = Icons.Outlined.Settings,
                cntDescResId = NavRoutes.DashboardSettings.titleResId
            ) { appState.mainNavigate(NavRoutes.DashboardSettings.route) }
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
                            dashboardingUi.favoriteCongregation?.let { favoriteCongregation ->
                                if (session.containsRole(MemberRoleType.REPORTS)) {
                                    CongregationSection(
                                        navController = appState.mainNavController,
                                        congregation = favoriteCongregation,
                                        congregationTotals = dashboardingUi.congregationTotals
                                    )
                                }
                                if (session.containsAnyRoles(
                                        listOf(MemberRoleType.REPORTS, MemberRoleType.TERRITORIES)
                                    )
                                ) {
                                    TerritorySection(
                                        navController = appState.mainNavController,
                                        congregation = favoriteCongregation,
                                        territoryTotals = dashboardingUi.territoryTotals
                                    )
                                }

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
fun TotalTitle(
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    title: String,
    sinceDate: OffsetDateTime?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            painterResId != null -> Image(
                painter = painterResource(painterResId),
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            )

            imageVector != null -> Image(
                imageVector = imageVector,
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            )
        }
        Text(
            text = title,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        sinceDate.toShortFormatString()?.let { lastVisitDate ->
            Text(
                buildAnnotatedString {
                    append(stringResource(com.oborodulin.jwsuite.domain.R.string.since_expr))
                    withStyle(style = SpanStyle(fontSize = 14.sp)) {
                        append(" $lastVisitDate")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalsRow(
    imageVector: ImageVector? = null,
    @DrawableRes painterResId: Int? = null,
    @StringRes contentDescriptionResId: Int? = null,
    @StringRes subheadResId: Int,
    totalValue: Int,
    diffTotal: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            painterResId != null -> Image(
                painter = painterResource(painterResId),
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                colorFilter = ColorFilter.tint(colorResource(com.oborodulin.home.common.R.color.white)),
                modifier = Modifier
                    .size(48.dp)
                    .background(colorResource(com.oborodulin.home.common.R.color.black))
                    .padding(12.dp)
            )

            imageVector != null -> Image(
                imageVector = imageVector,
                contentDescription = contentDescriptionResId?.let { stringResource(it) },
                colorFilter = ColorFilter.tint(colorResource(com.oborodulin.home.common.R.color.white)),
                modifier = Modifier
                    .size(48.dp)
                    .background(colorResource(com.oborodulin.home.common.R.color.black))
                    .padding(12.dp)
            )
        }
        Text(
            text = stringResource(subheadResId),
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        BadgedBox(badge = {
            diffTotal.withSign()?.let {
                // https://stackoverflow.com/questions/71694942/change-background-color-of-badge-compose
                Badge(containerColor = if (diffTotal > 0) Color.Green else Color.Red) {
                    Text(it)
                }
            }
        }) {
            Text(
                text = totalValue.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

// https://www.youtube.com/watch?v=ZotJ6eZ3TE8
@Composable
fun CongregationSection(
    navController: NavHostController,
    congregation: CongregationUi,
    congregationTotals: CongregationTotalsUi?
) {
    /*    val boxBackgroundGradient = Brush.verticalGradient(
            colors = listOf(
                colorResource(com.oborodulin.home.common.R.color.purple_700),
                colorResource(com.oborodulin.home.common.R.color.purple_200)
            )
        )
     */
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            TotalTitle(
                painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_congregation_24,
                title = congregation.congregationName,
                sinceDate = congregation.lastVisitDate
            )
            /*Box(
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
                    }*/
            congregationTotals?.let { totals ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    TotalsRow(
                        painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_group_24,
                        subheadResId = R.string.total_groups_hint,
                        totalValue = totals.totalGroups,
                        diffTotal = totals.diffGroups
                    )
                    TotalsRow(
                        imageVector = Icons.Outlined.Person,
                        subheadResId = R.string.total_members_hint,
                        totalValue = totals.totalMembers,
                        diffTotal = totals.diffMembers
                    )
                    TotalsRow(
                        imageVector = Icons.Outlined.DateRange,
                        subheadResId = R.string.total_fulltime_members_hint,
                        totalValue = totals.totalFulltimeMembers,
                        diffTotal = totals.diffFulltimeMembers
                    )
                }
            }
        }
    }
}

@Composable
fun TerritorySection(
    navController: NavHostController,
    congregation: CongregationUi,
    territoryTotals: TerritoryTotalsUi?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        TotalTitle(
            painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_territory_map_24,
            title = stringResource(R.string.territories_title),
            sinceDate = congregation.lastVisitDate
        )
        territoryTotals?.let { totals ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                TotalsRow(
                    painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_territory_map_24,
                    subheadResId = R.string.total_territories_hint,
                    totalValue = totals.totalTerritories,
                    diffTotal = totals.diffTerritories
                )
                TotalsRow(
                    painterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_hand_map_24,
                    subheadResId = R.string.total_territory_issued_hint,
                    totalValue = totals.totalTerritoryIssued,
                    diffTotal = totals.diffTerritoryIssued
                )
                TotalsRow(
                    imageVector = Icons.Outlined.Done,
                    subheadResId = R.string.total_territory_processed_hint,
                    totalValue = totals.totalTerritoryProcessed,
                    diffTotal = totals.diffTerritoryProcessed
                )
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
            Column {
                CongregationSection(
                    navController = rememberNavController(),
                    congregation = CongregationViewModelImpl.previewUiModel(ctx),
                    congregationTotals = DashboardingViewModelImpl.previewCongregationTotalsModel(
                        ctx
                    )
                )
                TerritorySection(
                    navController = rememberNavController(),
                    congregation = CongregationViewModelImpl.previewUiModel(ctx),
                    territoryTotals = DashboardingViewModelImpl.previewTerritoryTotalsModel(ctx)
                )
            }
        }
    }
}
