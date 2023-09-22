package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
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
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.BarLocalityComboBox
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.BarStreetComboBox
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list.RoomsListView
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.EnumMap

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.HousesScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HousesScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    housesViewModel: HousesViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("HousesScreen(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(housesViewModel.events, lifecycleOwner) {
        housesViewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("HousesScreen: currentCongregation = %s", currentCongregation)

    Timber.tag(TAG).d("Houses: CollectAsStateWithLifecycle for all fields")
    val locality by housesViewModel.locality.collectAsStateWithLifecycle()
    val street by housesViewModel.street.collectAsStateWithLifecycle()

    // https://stackoverflow.com/questions/73034912/jetpack-compose-how-to-detect-when-tabrow-inside-horizontalpager-is-visible-and
    var tabType by remember { mutableStateOf(HousesTabType.HOUSES) }
    val onChangeTab: (HousesTabType) -> Unit = { tabType = it }
    val addActionOnClick = {
        appState.commonNavController.navigate(
            when (tabType) {
                HousesTabType.HOUSES -> NavRoutes.House.routeForHouse()
                HousesTabType.ENTRANCES -> NavRoutes.Entrance.routeForEntrance()
                HousesTabType.FLOORS -> NavRoutes.Floor.routeForFloor()
                HousesTabType.ROOMS -> NavRoutes.Room.routeForRoom()
            }
        )
    }
    Timber.tag(TAG).d("Houses: Init Focus Requesters for all fields")
    val focusRequesters = EnumMap<HousesFields, InputFocusRequester>(HousesFields::class.java)
    enumValues<HousesFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HousesScreen: LaunchedEffect() BEFORE collect ui state flow: events.collect")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    housesViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_houses,
                topBarNavigationIcon = {
                    IconButton(onClick = { appState.commonNavigateUp() }) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                actionBar = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.weight(2.8f)
                        ) {
                            BarLocalityComboBox(
                                modifier = Modifier
                                    .focusRequester(focusRequesters[HousesFields.HOUSES_LOCALITY]!!.focusRequester)
                                    .onFocusChanged { focusState ->
                                        housesViewModel.onTextFieldFocusChanged(
                                            focusedField = HousesFields.HOUSES_LOCALITY,
                                            isFocused = focusState.isFocused
                                        )
                                    },
                                inputWrapper = locality,
                                onValueChange = {
                                    housesViewModel.onTextFieldEntered(
                                        HousesInputEvent.Locality(it)
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
                                    .focusRequester(focusRequesters[HousesFields.HOUSES_STREET]!!.focusRequester)
                                    .onFocusChanged { focusState ->
                                        housesViewModel.onTextFieldFocusChanged(
                                            focusedField = HousesFields.HOUSES_STREET,
                                            isFocused = focusState.isFocused
                                        )
                                    },
                                localityId = locality.item?.itemId,
                                inputWrapper = street,
                                onValueChange = {
                                    housesViewModel.onTextFieldEntered(HousesInputEvent.Street(it))
                                }
                            )
                        }
                    }
                },
                topBarActions = {
                    IconButton(onClick = addActionOnClick) { Icon(Icons.Outlined.Add, null) }
                    /*IconButton(onClick = { context.toast("Settings button clicked...") }) {
                        Icon(Icons.Outlined.Settings, null)
                    }*/
                }
            ) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    CustomScrollableTabRow(
                        listOf(
                            TabRowItem(
                                title = stringResource(R.string.houses_tab_houses),
                                onClick = { onChangeTab(HousesTabType.HOUSES) }
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
                                onClick = { onChangeTab(HousesTabType.ENTRANCES) }
                            ) {},
                            TabRowItem(
                                title = stringResource(R.string.houses_tab_floors),
                                onClick = { onChangeTab(HousesTabType.FLOORS) }
                            ) {},
                            TabRowItem(
                                title = stringResource(R.string.houses_tab_rooms),
                                onClick = { onChangeTab(HousesTabType.ROOMS) }
                            ) { street.item?.itemId?.let { RoomsView(appState = appState) } }
                        )
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("HousesScreen: LaunchedEffect() AFTER collect single Event Flow")
        housesViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is HousesUiSingleEvent.OpenHandOutTerritoriesConfirmationScreen -> {
                    appState.commonNavController.navigate(it.navRoute)
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
            HousesListView(navController = appState.commonNavController, streetInput = streetInput)
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
            CustomScrollableTabRow(
                listOf(
                    TabRowItem(title = stringResource(R.string.houses_tab_entrances)) {

                    },
                    TabRowItem(title = stringResource(R.string.houses_tab_floors)) {
                    },
                    TabRowItem(title = stringResource(R.string.houses_tab_rooms)) {
                        RoomsListView(navController = appState.commonNavController)
                    }
                )
            )
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
            RoomsListView(navController = appState.commonNavController)
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHousesScreen() {
    /*HousesScreen(
        appState = rememberAppState(),
        congregationInput = CongregationInput(UUID.randomUUID()),
        onClick = {},
        onEdit = {},
        onDelete = {})

     */
}
