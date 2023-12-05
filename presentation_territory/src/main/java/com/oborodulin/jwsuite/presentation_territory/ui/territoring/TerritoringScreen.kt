package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.fab.MultiFloatingState
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.components.tab.CustomScrollableTabRow
import com.oborodulin.home.common.ui.components.tab.TabRowItem
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single.BarMemberComboBox
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.components.AtWorkProcessMultiFabComponent
import com.oborodulin.jwsuite.presentation_territory.ui.components.HandOutFabComponent
import com.oborodulin.jwsuite.presentation_territory.ui.components.LocationDropdownMenuBoxComponent
import com.oborodulin.jwsuite.presentation_territory.ui.components.PrivateSectorSwitchComponent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list.TerritoryDetailsListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesInputEvent
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.EnumMap
import java.util.UUID

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoringScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoringScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoringViewModel: TerritoringViewModelImpl = hiltViewModel(),
//    territoryDetailsViewModel: TerritoryDetailsViewModelImpl = hiltViewModel(),
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("TerritoringScreen(...) called")
    val appState = LocalAppState.current
    val currentCongregation =
        appState.congregationViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("TerritoringScreen: currentCongregation = %s", currentCongregation)

    val emptySearchText = TextFieldValue("")

    Timber.tag(TAG).d("Territoring: CollectAsStateWithLifecycle for all fields")
    val isPrivateSector by territoringViewModel.isPrivateSector.collectAsStateWithLifecycle()
    val location by territoringViewModel.location.collectAsStateWithLifecycle()

    val checkedTerritories by territoriesGridViewModel.checkedListItems.collectAsStateWithLifecycle()

    val areHandOutInputsValid by territoriesGridViewModel.areHandOutInputsValid.collectAsStateWithLifecycle()
    val areTerritoriesChecked by territoriesGridViewModel.areInputsValid.collectAsStateWithLifecycle()

    var multiFloatingProcessState by remember { mutableStateOf(MultiFloatingState.Collapsed) }
    var multiFloatingAddState by remember { mutableStateOf(MultiFloatingState.Collapsed) }
    // https://stackoverflow.com/questions/73034912/jetpack-compose-how-to-detect-when-tabrow-inside-horizontalpager-is-visible-and
    var tabType by rememberSaveable { mutableStateOf(TerritoringTabType.HAND_OUT.name) }
    val onTabChange: (TerritoringTabType) -> Unit = { tabType = it.name }
    onFabChange {
        when (TerritoringTabType.valueOf(tabType)) {
            TerritoringTabType.HAND_OUT -> HandOutFabComponent(
                enabled = areHandOutInputsValid,
                territoriesGridViewModel = territoriesGridViewModel,
                territoringViewModel = territoringViewModel
            )

            TerritoringTabType.AT_WORK -> AtWorkProcessMultiFabComponent(
                enabled = areTerritoriesChecked,
                multiFloatingState = multiFloatingProcessState,
                onMultiFabStateChange = { multiFloatingProcessState = it },
                checkedTerritories = checkedTerritories,
                territoriesGridViewModel = territoriesGridViewModel
            )

            TerritoringTabType.IDLE -> {}
            TerritoringTabType.ALL -> {}
        }
    }

    Timber.tag(TAG).d("Territoring: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<TerritoringFields, InputFocusRequester>(TerritoringFields::class.java)
    enumValues<TerritoringFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(isPrivateSector.value, currentCongregation?.itemId) {
        Timber.tag(TAG)
            .d("TerritoringScreen -> LaunchedEffect() BEFORE collect ui state flow: submitAction")
        territoringViewModel.submitAction(
            TerritoringUiAction.LoadLocations(
                congregationId = currentCongregation?.itemId,
                isPrivateSector = isPrivateSector.value.toBoolean()
            )
        )
    }

    /*LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoringScreen -> LaunchedEffect() BEFORE collect ui state flow: events.collect")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }*/
    territoringViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        onActionBarTitleChange(stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_territoring))
        // Searching:
        var isShowSearchBar by rememberSaveable { mutableStateOf(false) }
        val handOutSearchText by territoriesGridViewModel.handOutSearchText.collectAsStateWithLifecycle()
        val atWorkSearchText by territoriesGridViewModel.atWorkSearchText.collectAsStateWithLifecycle()
        val idleSearchText by territoriesGridViewModel.idleSearchText.collectAsStateWithLifecycle()
        val allSearchText by territoriesGridViewModel.searchText.collectAsStateWithLifecycle()
        val handleActionSearch = { isShowSearchBar = true }
        when (isShowSearchBar) {
            true -> {
                onActionBarChange {
                    when (TerritoringTabType.valueOf(tabType)) {
                        TerritoringTabType.HAND_OUT -> SearchComponent(
                            fieldValue = handOutSearchText,
                            placeholderResId = R.string.territory_search_placeholder,
                            isFocused = true,
                            onValueChange = territoriesGridViewModel::onHandOutSearchTextChange
                        )

                        TerritoringTabType.AT_WORK -> SearchComponent(
                            fieldValue = atWorkSearchText,
                            placeholderResId = R.string.territory_search_placeholder,
                            isFocused = true,
                            onValueChange = territoriesGridViewModel::onAtWorkSearchTextChange
                        )

                        TerritoringTabType.IDLE -> SearchComponent(
                            fieldValue = idleSearchText,
                            placeholderResId = R.string.territory_search_placeholder,
                            isFocused = true,
                            onValueChange = territoriesGridViewModel::onIdleSearchTextChange
                        )

                        TerritoringTabType.ALL -> SearchComponent(
                            fieldValue = allSearchText,
                            placeholderResId = R.string.territory_search_placeholder,
                            isFocused = true,
                            onValueChange = territoriesGridViewModel::onSearchTextChange
                        )
                    }
                }
                onTopBarActionsChange(true) {}
            }

            false -> {
                onActionBarChange {
                    CommonScreen(state = state) { territoringUi ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PrivateSectorSwitchComponent(
                                viewModel = territoringViewModel, inputWrapper = isPrivateSector
                            )
                            LocationDropdownMenuBoxComponent(
                                viewModel = territoringViewModel, inputWrapper = location,
                                items = territoringUi.territoryLocations.distinctBy { it.locationShortName }
                            )
                        }
                    }
                }
                onTopBarActionsChange(false) {
                    var expanded by rememberSaveable { mutableStateOf(false) }
                    IconButton(onClick = { expanded = expanded.not() }) {
                        Icon(
                            Icons.Outlined.MoreVert,
                            stringResource(R.string.territory_menu_cnt_desc)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(com.oborodulin.home.common.R.string.mi_search_lbl)) },
                            onClick = handleActionSearch
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(com.oborodulin.home.common.R.string.mi_add_lbl)) },
                            onClick = { appState.mainNavigate(NavRoutes.Territory.routeForTerritory()) })
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.mi_housing_lbl)) },
                            onClick = { appState.mainNavigate(NavRoutes.Housing.route) })
                    }
                }
            }
        }
        onTopBarNavImageVectorChange(if (isShowSearchBar) Icons.Outlined.ArrowBack else null)
        val handleCloseAndClearSearch = {
            isShowSearchBar = false
            when (TerritoringTabType.valueOf(tabType)) {
                TerritoringTabType.HAND_OUT -> territoriesGridViewModel.onHandOutSearchTextChange(
                    emptySearchText
                )

                TerritoringTabType.AT_WORK -> territoriesGridViewModel.onAtWorkSearchTextChange(
                    emptySearchText
                )

                TerritoringTabType.IDLE -> territoriesGridViewModel.onIdleSearchTextChange(
                    emptySearchText
                )

                TerritoringTabType.ALL -> territoriesGridViewModel.clearSearchText()
            }
        }
        appState.handleTopBarNavClick.value =
            {
                if (isShowSearchBar) {
                    handleCloseAndClearSearch.invoke()
                }
            }
        // https://stackoverflow.com/questions/69151521/how-to-override-the-system-onbackpress-in-jetpack-compose
        BackHandler {
            if (isShowSearchBar) {
                handleCloseAndClearSearch.invoke()
            } else appState.mainNavigateUp()
        }
        Column(modifier = Modifier.fillMaxSize()) {
            CustomScrollableTabRow(
                listOf(
                    TabRowItem(
                        title = stringResource(R.string.territory_tab_hand_out),
                        onClick = { onTabChange(TerritoringTabType.HAND_OUT) }
                    ) {
                        location.item?.let {
                            HandOutTerritoriesView(
                                appState = appState,
                                //sharedViewModel = sharedViewModel,
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
                        onClick = { onTabChange(TerritoringTabType.AT_WORK) }
                    ) {
                        location.item?.let {
                            AtWorkTerritoriesView(
                                appState = appState,
                                //sharedViewModel = sharedViewModel,
                                territoriesGridViewModel = territoriesGridViewModel,
                                territoryLocationType = it.territoryLocationType,
                                locationId = it.locationId,
                                isPrivateSector = isPrivateSector.value.toBoolean()
                            )
                        }
                    },
                    TabRowItem(
                        title = stringResource(R.string.territory_tab_idle),
                        onClick = { onTabChange(TerritoringTabType.IDLE) }
                    ) {
                        location.item?.let {
                            IdleTerritoriesView(
                                appState = appState,
                                //sharedViewModel = sharedViewModel,
                                territoriesGridViewModel = territoriesGridViewModel,
                                territoryLocationType = it.territoryLocationType,
                                locationId = it.locationId,
                                isPrivateSector = isPrivateSector.value.toBoolean()
                            )
                        }
                    },
                    TabRowItem(
                        title = stringResource(R.string.territory_tab_all),
                        onClick = { onTabChange(TerritoringTabType.ALL) }
                    ) {
                        location.item?.let {
                            AllTerritoriesView(
                                appState = appState,
                                //sharedViewModel = sharedViewModel,
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
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoringScreen -> LaunchedEffect() AFTER collect single Event Flow")
        territoringViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoringUiSingleEvent.OpenHandOutConfirmationScreen -> {
                    Timber.tag(TAG).d("navRoute = %s", it.navRoute)
                    appState.barNavController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun HandOutTerritoriesView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
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
                //sharedViewModel = sharedViewModel,
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
            TerritoryDetailsListView()
        }
        BarMemberComboBox(
            modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
            sharedViewModel = appState.congregationViewModel.value,
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
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("AtWorkTerritoriesView(...) called")
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
                //sharedViewModel = sharedViewModel,
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
            TerritoryDetailsListView()
        }
    }
}

@Composable
fun IdleTerritoriesView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("IdleTerritoriesView(...) called")
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
                //sharedViewModel = sharedViewModel,
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
            TerritoryDetailsListView()
        }
    }
}

@Composable
fun AllTerritoriesView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoriesGridViewModel: TerritoriesGridViewModel,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("AllTerritoriesView(...) called")
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
                //sharedViewModel = sharedViewModel,
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
            TerritoryDetailsListView()
        }
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
