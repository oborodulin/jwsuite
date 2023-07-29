package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.TabRowItem
import com.oborodulin.home.common.ui.components.bar.BarListItemExposedDropdownMenuBoxComponent
import com.oborodulin.home.common.ui.components.field.SwitchComponent
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.domain.util.TerritoryProcessType
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid.TerritoriesView
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Territoring.TerritoringScreen"

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TerritoringScreen(
    appState: AppState,
    sharedViewModel: FavoriteCongregationViewModelImpl = hiltViewModel(),
    viewModel: TerritoringViewModelImpl = hiltViewModel(),
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) {
    Timber.tag(TAG).d("TerritoringScreen(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    Timber.tag(TAG).d("CollectAsStateWithLifecycle for all territoring fields")
    val currentCongregation by sharedViewModel.sharedFlow.collectAsStateWithLifecycle(null)

    val location by viewModel.location.collectAsStateWithLifecycle()
    val isPrivateSector by viewModel.isPrivateSector.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Init Focus Requesters for all territoring fields")
    val focusRequesters: MutableMap<String, InputFocusRequester> = HashMap()
    enumValues<TerritoringFields>().forEach {
        focusRequesters[it.name] = InputFocusRequester(it, remember { FocusRequester() })
    }

    LaunchedEffect(Unit) {// currentCongregation?.id
        Timber.tag(TAG).d("TerritoringScreen: LaunchedEffect() BEFORE collect ui state flow")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
        viewModel.submitAction(
            TerritoringUiAction.LoadLocations(congregationId = currentCongregation?.id)
        )
    }
    val tabRowItems = listOf(
        TabRowItem(
            title = stringResource(R.string.territory_tab_hand_out),
            view = {
                location.item?.let {
                    HandOutTerritoriesView(
                        appState = appState,
                        territoryLocationType = it.territoryLocationType,
                        locationId = it.locationId,
                        isPrivateSector = isPrivateSector.value.toBoolean()
                    )
                }
            },
        ),
        TabRowItem(
            title = stringResource(R.string.territory_tab_at_work),
            view = {
                location.item?.let {
                    AtWorkTerritoriesView(
                        appState = appState,
                        territoryLocationType = it.territoryLocationType,
                        locationId = it.locationId,
                        isPrivateSector = isPrivateSector.value.toBoolean()
                    )
                }
            },
        ),
        TabRowItem(
            title = stringResource(R.string.territory_tab_idle),
            view = {
                location.item?.let {
                    IdleTerritoriesView(
                        appState = appState,
                        territoryLocationType = it.territoryLocationType,
                        locationId = it.locationId,
                        isPrivateSector = isPrivateSector.value.toBoolean()
                    )
                }
            },
        ),
        TabRowItem(
            title = stringResource(R.string.territory_tab_all),
            view = {
                location.item?.let {
                    AllTerritoriesView(
                        appState = appState,
                        territoryLocationType = it.territoryLocationType,
                        locationId = it.locationId,
                        isPrivateSector = isPrivateSector.value.toBoolean()
                    )
                }
            },
        )
    )
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        JWSuiteTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                nestedScrollConnection = nestedScrollConnection,
                topBarTitleResId = R.string.nav_item_territoring,
                actionBar = {
                    CommonScreen(state = state) { territoringUi ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SwitchComponent(
                                modifier = Modifier
                                    .height(90.dp)
                                    .focusRequester(focusRequesters[TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR.name]!!.focusRequester)
                                    .onFocusChanged { focusState ->
                                        viewModel.onTextFieldFocusChanged(
                                            focusedField = TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR,
                                            isFocused = focusState.isFocused
                                        )
                                    },
                                labelResId = R.string.private_sector_hint,
                                inputWrapper = isPrivateSector,
                                onCheckedChange = {
                                    viewModel.onTextFieldEntered(
                                        TerritoringInputEvent.IsPrivateSector(it)
                                    )
                                }
                            )
                            BarListItemExposedDropdownMenuBoxComponent(
                                modifier = Modifier
                                    .focusRequester(focusRequesters[TerritoringFields.TERRITORY_LOCATION.name]!!.focusRequester)
                                    .onFocusChanged { focusState ->
                                        viewModel.onTextFieldFocusChanged(
                                            focusedField = TerritoringFields.TERRITORY_LOCATION,
                                            isFocused = focusState.isFocused
                                        )
                                    },
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
                                items = territoringUi.territoryLocations,
                                onValueChange = {
                                    viewModel.onTextFieldEntered(TerritoringInputEvent.Location(it))
                                },
                                onImeKeyAction = viewModel::moveFocusImeAction,
                            )
                        }
                    }
                },
                topBarActions = {
                    IconButton(onClick = { appState.commonNavController.navigate(NavRoutes.Territory.routeForTerritory()) }) {
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
                Column(modifier = Modifier.padding(it)) {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier
                            .padding(8.dp)
                            .height(50.dp)
                        /*indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                //https://github.com/google/accompanist/issues/1267
                                //Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },*/
                    ) {
                        tabRowItems.forEachIndexed { index, item ->
                            Tab(
                                modifier = Modifier.height(50.dp),
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            index
                                        )
                                    }
                                },
                                icon = {
                                    item.icon?.let { icon ->
                                        Icon(imageVector = icon, contentDescription = "")
                                    }
                                },
                                text = {
                                    Text(
                                        text = item.title,
                                        style = if (pagerState.currentPage == index) Typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        ) else Typography.bodyLarge,
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
    }
    /*        }
            LaunchedEffect(Unit) {
                Timber.tag(TAG).d("TerritoringScreen: LaunchedEffect() AFTER collect ui state flow")
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

@Composable
fun HandOutTerritoriesView(
    appState: AppState,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("HandOutTerritoriesView(...) called")
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
            TerritoriesView(
                navController = appState.commonNavController,
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
        }
    }
}

@Composable
fun AtWorkTerritoriesView(
    appState: AppState,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("AtWorkTerritoriesView(...) called")
    var searchTerritoryState by rememberSaveable { mutableStateOf(TextFieldValue("")) }
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
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoriesView(
                navController = appState.commonNavController,
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
                .weight(7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
        }
        SearchComponent(searchTerritoryState) { searchTerritoryState = it }
    }
}

@Composable
fun IdleTerritoriesView(
    appState: AppState,
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
            TerritoriesView(
                navController = appState.commonNavController,
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
        }
    }
}

@Composable
fun AllTerritoriesView(
    appState: AppState,
    territoryLocationType: TerritoryLocationType,
    locationId: UUID? = null,
    isPrivateSector: Boolean = false
) {
    Timber.tag(TAG).d("AllTerritoriesView(...) called")
    var searchTerritoryState by rememberSaveable { mutableStateOf(TextFieldValue("")) }
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
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(16.dp))
                //.background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(20.dp))
                .weight(3f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            TerritoriesView(
                navController = appState.commonNavController,
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
                .weight(7f)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
        }
        SearchComponent(searchTerritoryState) { searchTerritoryState = it }
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
