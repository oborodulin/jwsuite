package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.BarLocalityComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.BarStreetComboBox
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list.HousesListView
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListView
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.EnumMap

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Reporting.HousingScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HousingScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    housesViewModel: PartialRoomsViewModelImpl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit*/
) {
    Timber.tag(TAG).d("HousingScreen(...) called")
    // Action Bar:
    var actionBar: @Composable (() -> Unit)? by remember { mutableStateOf(null) }
    val onActionBarChange: (@Composable (() -> Unit)?) -> Unit = { actionBar = it }

    val appState = LocalAppState.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(housesViewModel.events, lifecycleOwner) {
        housesViewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val currentCongregation =
        appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("HousingScreen: currentCongregation = %s", currentCongregation)

    Timber.tag(TAG).d("Houses: CollectAsStateWithLifecycle for all fields")
    val locality by housesViewModel.locality.collectAsStateWithLifecycle()
    val street by housesViewModel.street.collectAsStateWithLifecycle()

    // https://stackoverflow.com/questions/73034912/jetpack-compose-how-to-detect-when-tabrow-inside-horizontalpager-is-visible-and
    var tabType by rememberSaveable { mutableStateOf(HousingTabType.HOUSES.name) }
    val onTabChange: (HousingTabType) -> Unit = { tabType = it.name }
    val handleActionAdd = {
        appState.mainNavigate(
            when (HousingTabType.valueOf(tabType)) {
                HousingTabType.HOUSES -> NavRoutes.House.routeForHouse()
                HousingTabType.ENTRANCES -> NavRoutes.Entrance.routeForEntrance()
                HousingTabType.FLOORS -> NavRoutes.Floor.routeForFloor()
                HousingTabType.ROOMS -> NavRoutes.Room.routeForRoom()
            }
        )
    }
    Timber.tag(TAG).d("Houses: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<PartialRoomsFields, InputFocusRequester>(PartialRoomsFields::class.java)
    enumValues<PartialRoomsFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HousingScreen -> LaunchedEffect() BEFORE collect ui state flow: events.collect")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    housesViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        ScaffoldComponent(
            topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_housing),
            topBarSubtitle = "",
            actionBar = actionBar,
            defTopBarActions = defTopBarActions,
            topBarActions = {
                IconButton(onClick = handleActionAdd) { Icon(Icons.Outlined.Add, null) }
            }
        ) { innerPadding ->
            // Scaffold Hoisting:
            /*onActionBarTitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_housing))
            onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
            onTopBarNavClickChange { appState.mainNavigateUp() } //backToBottomBarScreen() }
            onTopBarActionsChange(true) {
                IconButton(onClick = handleActionAdd) { Icon(Icons.Outlined.Add, null) }
            }*/
            appState.handleTopBarNavClick.value = {
                appState.mainNavigateUp()
                appState.navigateToBarRoute(NavRoutes.Territoring.route)
            }
            onActionBarChange {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(2.8f)
                    ) {
                        BarLocalityComboBox(
                            modifier = Modifier
                                .focusRequester(focusRequesters[PartialRoomsFields.HOUSES_LOCALITY]!!.focusRequester)
                                .onFocusChanged { focusState ->
                                    housesViewModel.onTextFieldFocusChanged(
                                        focusedField = PartialRoomsFields.HOUSES_LOCALITY,
                                        isFocused = focusState.isFocused
                                    )
                                },
                            inputWrapper = locality,
                            onValueChange = {
                                housesViewModel.onTextFieldEntered(
                                    PartialRoomsInputEvent.Locality(it)
                                )
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3f)
                    ) {
                        BarStreetComboBox(
                            modifier = Modifier
                                .focusRequester(focusRequesters[PartialRoomsFields.PARTIAL_ROOM_HOUSES]!!.focusRequester)
                                .onFocusChanged { focusState ->
                                    housesViewModel.onTextFieldFocusChanged(
                                        focusedField = PartialRoomsFields.PARTIAL_ROOM_HOUSES,
                                        isFocused = focusState.isFocused
                                    )
                                },
                            localityId = locality.item?.itemId,
                            inputWrapper = street,
                            onValueChange = {
                                housesViewModel.onTextFieldEntered(PartialRoomsInputEvent.Street(it))
                            }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CustomScrollableTabRow(
                    listOf(
                        TabRowItem(
                            title = stringResource(R.string.houses_tab_houses),
                            onClick = { onTabChange(HousingTabType.HOUSES) }
                        ) {
                            street.item?.itemId?.let {
                                HousesEntrancesFloorsRoomsView(
                                    appState = appState,
                                    streetInput = NavigationInput.StreetInput(it)
                                )
                            }
                        },
                        TabRowItem(
                            title = stringResource(R.string.houses_tab_entrances),
                            onClick = { onTabChange(HousingTabType.ENTRANCES) }
                        ) {},
                        TabRowItem(
                            title = stringResource(R.string.houses_tab_floors),
                            onClick = { onTabChange(HousingTabType.FLOORS) }
                        ) {},
                        TabRowItem(
                            title = stringResource(R.string.houses_tab_rooms),
                            onClick = { onTabChange(HousingTabType.ROOMS) }
                        ) { street.item?.itemId?.let { RoomsView(appState = appState) } }
                    )
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("HousingScreen -> LaunchedEffect() AFTER collect single Event Flow")
        housesViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is PartialRoomsUiSingleEvent.OpenHandOutTerritoriesConfirmationScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun HousesEntrancesFloorsRoomsView(
    appState: AppState,
    streetInput: NavigationInput.StreetInput
) {
    Timber.tag(TAG).d("HousesEntrancesFloorsRoomsView(...) called")
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
            HousesListView(navController = appState.mainNavController, streetInput = streetInput)
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
            Column(modifier = Modifier.fillMaxSize()) {
                CustomScrollableTabRow(
                    listOf(
                        TabRowItem(title = stringResource(R.string.houses_tab_entrances)) {

                        },
                        TabRowItem(title = stringResource(R.string.houses_tab_floors)) {
                        },
                        TabRowItem(title = stringResource(R.string.houses_tab_rooms)) {
                            RoomsListView(navController = appState.mainNavController)
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun RoomsView(appState: AppState) {
    Timber.tag(TAG).d("RoomsView(...) called")
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
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            RoomsListView(navController = appState.mainNavController)
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHousingScreen() {
    /*HousingScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
