package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.reporting.list.MemberReportsListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single.BarTerritoryStreetComboBox
import timber.log.Timber
import java.util.EnumMap

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Reporting.ReportHousesScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReportHousesScreen(
    territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    reportHousesViewModel: ReportHousesViewModelImpl = hiltViewModel(),
    territoryInput: TerritoryInput,
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("ReportHousesScreen(...) called")
    // Action Bar:
    var actionBar: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onActionBarChange: (@Composable (() -> Unit)?) -> Unit = { actionBar = it }

    val appState = LocalAppState.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(reportHousesViewModel.events, lifecycleOwner) {
        reportHousesViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    Timber.tag(TAG).d("PartialHouses: CollectAsStateWithLifecycle for all fields")
    val territoryStreet by reportHousesViewModel.territoryStreet.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("PartialHouses: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<ReportHousesFields, InputFocusRequester>(
        ReportHousesFields::class.java
    )
    enumValues<ReportHousesFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("ReportHousesScreen -> LaunchedEffect(): events.collect")
        territoryViewModel.submitAction(TerritoryUiAction.Load(territoryInput?.territoryId))
        events.collect { event ->
            if (LOG_FLOW_INPUT) Timber.tag(TAG)
                .d("IF# Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    appState.handleTopBarNavClick.value = {
        appState.mainNavigateUp()
        appState.navigateToBarRoute(NavRoutes.Territoring.route)
    }
    territoryViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        CommonScreen(state = state) { territory ->
            ScaffoldComponent(
                topBarTitleResId = NavRoutes.Territoring.titleResId,
                navRoute = NavRoutes.ReportHouses,
                topBarSubtitle = stringResource(
                    R.string.territory_houses_process,
                    territory.cardNum
                ),
                actionBar = actionBar,
                defTopBarActions = defTopBarActions
            ) { innerPadding ->
                onActionBarChange {
                    BarTerritoryStreetComboBox(
                        modifier = Modifier
                            .focusRequester(focusRequesters[ReportHousesFields.REPORT_HOUSES_TERRITORY_STREET]!!.focusRequester)
                            .onFocusChanged { focusState ->
                                reportHousesViewModel.onTextFieldFocusChanged(
                                    focusedField = ReportHousesFields.REPORT_HOUSES_TERRITORY_STREET,
                                    isFocused = focusState.isFocused
                                )
                            },
                        territoryId = territory.id!!,
                        sharedViewModel = appState.congregationSharedViewModel.value,
                        territoryViewModel = territoryViewModel,
                        inputWrapper = territoryStreet,
                        onValueChange = {
                            reportHousesViewModel.onTextFieldEntered(
                                ReportHousesInputEvent.Street(it)
                            )
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    HousesMemberReportView(
                        appState = appState,
                        territoryStreetInput = TerritoryStreetInput(
                            territoryInput.territoryId,
                            territoryStreet.item?.itemId
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun HousesMemberReportView(
    appState: AppState,
    reportHousesViewModel: ReportHousesViewModelImpl = hiltViewModel(),
    territoryStreetInput: TerritoryStreetInput
) {
    Timber.tag(TAG).d("HousesMemberReportView(...) called")
    val selectedHouseId = reportHousesViewModel.singleSelectedItem()?.itemId
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
                .weight(3.82f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            ReportHousesGridView(
                navController = appState.mainNavController,
                territoryStreetInput = territoryStreetInput
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            MemberReportsListView(
                navController = appState.mainNavController,
                houseId = selectedHouseId
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewReportHousesScreen() {
    /*ReportHousesScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
