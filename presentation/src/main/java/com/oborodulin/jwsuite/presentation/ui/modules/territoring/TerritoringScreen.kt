package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.bar.BarListItemExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.fab.MinFabItem
import com.oborodulin.home.common.ui.components.fab.MultiFabComponent
import com.oborodulin.home.common.ui.components.fab.MultiFloatingState
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single.BarMemberComboBox
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.TerritoryDetailsView
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid.HandOutFabComponent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid.TerritoriesGridView
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid.TerritoriesGridViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid.TerritoriesInputEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoringScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoringScreen(
    appState: AppState,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoringViewModel: TerritoringViewModelImpl = hiltViewModel(),
//    territoryDetailsViewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("TerritoringScreen(...) called")
    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("TerritoringScreen: currentCongregation = %s", currentCongregation)

    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all territoring fields")
    val isPrivateSector by territoringViewModel.isPrivateSector.collectAsStateWithLifecycle()
    val location by territoringViewModel.location.collectAsStateWithLifecycle()
    val areHandOutInputsValid by territoriesGridViewModel.areHandOutInputsValid.collectAsStateWithLifecycle()
    val areTerritoriesChecked by territoriesGridViewModel.areTerritoriesChecked.collectAsStateWithLifecycle()

    // https://stackoverflow.com/questions/73034912/jetpack-compose-how-to-detect-when-tabrow-inside-horizontalpager-is-visible-and
    appState.fab.value = {
        HandOutFabComponent(
            enabled = areHandOutInputsValid,
            territoriesGridViewModel = territoriesGridViewModel,
            territoringViewModel = territoringViewModel
        )
    }
    var multiFloatingState by remember { mutableStateOf(MultiFloatingState.Collapsed) }


    Timber.tag(TAG).d("Init Focus Requesters for all territoring fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<TerritoringFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(isPrivateSector.value, currentCongregation?.id) {
        Timber.tag(TAG)
            .d("TerritoringScreen: LaunchedEffect() BEFORE collect ui state flow: submitAction")
        territoringViewModel.submitAction(
            TerritoringUiAction.LoadLocations(
                congregationId = currentCongregation?.id,
                isPrivateSector = isPrivateSector.value.toBoolean()
            )
        )
    }
    /*LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoringScreen: LaunchedEffect() BEFORE collect ui state flow: events.collect")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }*/
    territoringViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                nestedScrollConnection = nestedScrollConnection,
                topBarTitleResId = R.string.nav_item_territoring,
                actionBar = {
                    CommonScreen(state = state) { territoringUi ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.weight(2f)
                            ) {
                                SwitchComponent(
                                    componentModifier = Modifier.padding(end = 36.dp)
                                    /*.focusRequester(focusRequesters[TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR.name]!!.focusRequester)
                                    .onFocusChanged { focusState ->
                                        territoringViewModel.onTextFieldFocusChanged(
                                            focusedField = TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR,
                                            isFocused = focusState.isFocused
                                        )
                                    }*/,
                                    labelResId = R.string.private_sector_hint,
                                    inputWrapper = isPrivateSector,
                                    onCheckedChange = {
                                        territoringViewModel.onTextFieldEntered(
                                            TerritoringInputEvent.IsPrivateSector(it)
                                        )
                                    }
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(3f)
                            ) {
                                BarListItemExposedDropdownMenuBoxComponent(
                                    /*modifier = Modifier
                                        .focusRequester(focusRequesters[TerritoringFields.TERRITORY_LOCATION.name]!!.focusRequester)
                                        .onFocusChanged { focusState ->
                                            territoringViewModel.onTextFieldFocusChanged(
                                                focusedField = TerritoringFields.TERRITORY_LOCATION,
                                                isFocused = focusState.isFocused
                                            )
                                        },*/
                                    leadingIcon = {
                                        Icon(painterResource(R.drawable.ic_location_pin_24), null)
                                    },
                                    keyboardOptions = remember {
                                        KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Next
                                        )
                                    },
                                    inputWrapper = location,
                                    items = territoringUi.territoryLocations.distinctBy { it.locationShortName },
                                    onValueChange = {
                                        territoringViewModel.onTextFieldEntered(
                                            TerritoringInputEvent.Location(it)
                                        )
                                    },
                                    onImeKeyAction = territoringViewModel::moveFocusImeAction,
                                )
                            }
                        }
                    }
                },
                topBarActions = {
                    IconButton(onClick = { appState.commonNavController.navigate(NavRoutes.Territory.routeForTerritory()) }) {
                        Icon(Icons.Outlined.Add, null)
                    }
                    /*IconButton(onClick = { context.toast("Settings button clicked...") }) {
                        Icon(Icons.Outlined.Settings, null)
                    }*/
                },
                floatingActionButton = appState.fab.value,
                bottomBar = bottomBar
            ) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    CustomScrollableTabRow(
                        listOf(
                            TabRowItem(
                                title = stringResource(R.string.territory_tab_hand_out),
                                onClick = {
                                    appState.fab.value = {
                                        HandOutFabComponent(
                                            enabled = areHandOutInputsValid,
                                            territoriesGridViewModel = territoriesGridViewModel,
                                            territoringViewModel = territoringViewModel
                                        )
                                    }
                                },
                            ) {
                                location.item?.let {
                                    HandOutTerritoriesView(
                                        appState = appState,
                                        enableAction = areHandOutInputsValid,
                                        territoringViewModel = territoringViewModel,
                                        territoriesGridViewModel = territoriesGridViewModel,
                                        territoryLocationType = it.territoryLocationType,
                                        locationId = it.locationId,
                                        isPrivateSector = isPrivateSector.value.toBoolean()
                                    )
                                }
                            },
                            TabRowItem(
                                title = stringResource(R.string.territory_tab_at_work),
                                onClick = {
                                    appState.fab.value = {
                                        MultiFabComponent(
                                            multiFloatingState = multiFloatingState,
                                            onMultiFabStateChange = {
                                                multiFloatingState = it
                                            },
                                            enabled = areTerritoriesChecked,
                                            collapsedImageVector = Icons.Outlined.Done,
                                            collapsedTextResId = R.string.fab_territory_at_work_text,
                                            expandedImageVector = Icons.Default.Close,
                                            items = listOf(
                                                MinFabItem(
                                                    labelResId = R.string.fab_territory_process_room_text,
                                                    painterResId = R.drawable.ic_room_24
                                                ),
                                                MinFabItem(
                                                    labelResId = R.string.fab_territory_process_entrance_text,
                                                    painterResId = R.drawable.ic_door_sliding_24
                                                ),
                                                MinFabItem(
                                                    labelResId = R.string.fab_territory_process_house_text,
                                                    painterResId = R.drawable.ic_house_24
                                                ),
                                                MinFabItem(
                                                    labelResId = R.string.fab_territory_process_street_text,
                                                    painterResId = R.drawable.ic_street_sign_24
                                                ),
                                                MinFabItem(
                                                    labelResId = R.string.fab_territory_process_text,
                                                    painterResId = R.drawable.ic_map_marker_24
                                                )
                                            )
                                        )
                                    }
                                }
                            ) {
                                location.item?.let {
                                    AtWorkTerritoriesView(
                                        appState = appState,
                                        territoriesGridViewModel = territoriesGridViewModel,
                                        territoryLocationType = it.territoryLocationType,
                                        locationId = it.locationId,
                                        isPrivateSector = isPrivateSector.value.toBoolean()
                                    )
                                }
                            },
                            TabRowItem(
                                title = stringResource(R.string.territory_tab_idle),
                                onClick = { appState.fab.value = {} }
                            ) {
                                location.item?.let {
                                    IdleTerritoriesView(
                                        appState = appState,
                                        territoriesGridViewModel = territoriesGridViewModel,
                                        territoryLocationType = it.territoryLocationType,
                                        locationId = it.locationId,
                                        isPrivateSector = isPrivateSector.value.toBoolean()
                                    )
                                }
                            },
                            TabRowItem(
                                title = stringResource(R.string.territory_tab_all),
                                onClick = { appState.fab.value = {} }
                            ) {
                                location.item?.let {
                                    AllTerritoriesView(
                                        appState = appState,
                                        territoriesGridViewModel = territoriesGridViewModel,
                                        territoryLocationType = it.territoryLocationType,
                                        locationId = it.locationId,
                                        isPrivateSector = isPrivateSector.value.toBoolean()
                                    )
                                }
                            }
                        )
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoringScreen: LaunchedEffect() AFTER collect ui state flow")
        territoringViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoringUiSingleEvent.OpenHandOutTerritoriesConfirmationScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun HandOutTerritoriesView(
    appState: AppState,
    enableAction: Boolean,
    territoringViewModel: TerritoringViewModel,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d(
        "HandOutTerritoriesView(...) called: territoryLocationType = %s; isPrivateSector = %s; locationId = %s",
        territoryLocationType,
        isPrivateSector,
        locationId
    )
    val searchText by territoriesGridViewModel.handOutSearchText.collectAsStateWithLifecycle()
    val member by territoriesGridViewModel.member.collectAsStateWithLifecycle()
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
                .weight(7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoriesGridView(
                appState = appState,
                territoriesGridViewModel = territoriesGridViewModel,
                territoryProcessType = TerritoryProcessType.HAND_OUT,
                territoryLocationType = territoryLocationType,
                locationId = locationId,
                isPrivateSector = isPrivateSector
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoryDetailsView()
        }
        SearchComponent(
            searchText,
            onValueChange = territoriesGridViewModel::onHandOutSearchTextChange
        )
        BarMemberComboBox(
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
            sharedViewModel = appState.sharedViewModel.value,
            inputWrapper = member,
            onValueChange = {
                territoriesGridViewModel.onTextFieldEntered(TerritoriesInputEvent.Member(it))
            }
        )
        /*        Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BarMemberComboBox(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(6f),
                        sharedViewModel = appState.sharedViewModel.value,
                        inputWrapper = member,
                        onValueChange = {
                            territoriesGridViewModel.onTextFieldEntered(TerritoriesInputEvent.Member(it))
                        }
                    )
                    HandOutButtonComponent(
                        modifier = Modifier.weight(4f),
                        enabled = enableAction,
                        onClick = {
                            Timber.tag(TAG).d("TerritoringScreen: HandOut Button onClick...")
                            // checks all errors
                            territoriesGridViewModel.onContinueClick {
                                // if success, then go to Hand Out Confirmation
                                Timber.tag(TAG)
                                    .d("TerritoringScreen: submitAction TerritoringUiAction.HandOutTerritoriesConfirmation")
                                territoringViewModel.submitAction(
                                    TerritoringUiAction.HandOutTerritoriesConfirmation
                                )
                            }
                        }
                    )
                }*/
    }
}

@Composable
fun AtWorkTerritoriesView(
    appState: AppState,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("AtWorkTerritoriesView(...) called")
    val searchText by territoriesGridViewModel.atWorkSearchText.collectAsStateWithLifecycle()
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
                .weight(7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoriesGridView(
                appState = appState,
                territoriesGridViewModel = territoriesGridViewModel,
                territoryProcessType = TerritoryProcessType.AT_WORK,
                territoryLocationType = territoryLocationType,
                locationId = locationId,
                isPrivateSector = isPrivateSector
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoryDetailsView()
        }
        SearchComponent(
            searchText,
            onValueChange = territoriesGridViewModel::onAtWorkSearchTextChange
        )
    }
}

@Composable
fun IdleTerritoriesView(
    appState: AppState,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("IdleTerritoriesView(...) called")
    val searchText by territoriesGridViewModel.idleSearchText.collectAsStateWithLifecycle()
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
                .weight(7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoriesGridView(
                appState = appState,
                territoriesGridViewModel = territoriesGridViewModel,
                territoryProcessType = TerritoryProcessType.IDLE,
                territoryLocationType = territoryLocationType,
                locationId = locationId,
                isPrivateSector = isPrivateSector
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoryDetailsView()
        }
        SearchComponent(
            searchText,
            onValueChange = territoriesGridViewModel::onIdleSearchTextChange
        )
    }
}

@Composable
fun AllTerritoriesView(
    appState: AppState,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("AllTerritoriesView(...) called")
    val searchText by territoriesGridViewModel.searchText.collectAsStateWithLifecycle()
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
                .weight(7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoriesGridView(
                appState = appState,
                territoriesGridViewModel = territoriesGridViewModel,
                territoryProcessType = TerritoryProcessType.ALL,
                territoryLocationType = territoryLocationType,
                locationId = locationId,
                isPrivateSector = isPrivateSector
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                .weight(3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoryDetailsView()
        }
        SearchComponent(
            searchText,
            onValueChange = territoriesGridViewModel::onSearchTextChange
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoringScreen() {
    /*TerritoringScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
