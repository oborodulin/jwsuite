package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list.RoomsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list.RoomsListView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list.RoomsListViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.TerritoryRoomView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoryRoomView(
    appState: AppState,
    territoryUi: TerritoryUi? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModel,
    roomsListViewModel: RoomsListViewModel,
    viewModel: TerritoryRoomViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("TerritoryRoomView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    territoryUi?.let {
        viewModel.onTextFieldEntered(TerritoryRoomInputEvent.Territory(it.toTerritoriesListItem()))
    }
    Timber.tag(TAG).d("Territory Room: CollectAsStateWithLifecycle for all fields")
    val territory by viewModel.territory.collectAsStateWithLifecycle()
    val room by viewModel.room.collectAsStateWithLifecycle()
    val searchText by roomsListViewModel.searchText.collectAsStateWithLifecycle()

    Timber.tag(TAG).d("Territory Room: Init Focus Requesters for all fields")
    val focusRequesters =
        EnumMap<TerritoryRoomFields, InputFocusRequester>(TerritoryRoomFields::class.java)
    enumValues<TerritoryRoomFields>().forEach {
        focusRequesters[it] = InputFocusRequester(it, remember { FocusRequester() })
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("TerritoryRoomView(...): LaunchedEffect()")
        events.collect { event ->
            Timber.tag(TAG).d("Collect input events flow: %s", event.javaClass.name)
            inputProcess(context, focusManager, keyboardController, event, focusRequesters)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TerritoryComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryRoomFields.TERRITORY_ROOM_TERRITORY]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryRoomFields.TERRITORY_ROOM_TERRITORY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        TerritoryRoomComboBox(
            modifier = Modifier
                .focusRequester(focusRequesters[TerritoryRoomFields.TERRITORY_ROOM_ROOM]!!.focusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryRoomFields.TERRITORY_ROOM_ROOM,
                        isFocused = focusState.isFocused
                    )
                },
            territoryId = territory.item?.itemId!!,
            sharedViewModel = sharedViewModel,
            inputWrapper = room,
            onValueChange = {
                viewModel.onTextFieldEntered(TerritoryRoomInputEvent.Room(it))
                territoryUi?.id?.let { territoryId ->
                    roomsListViewModel.submitAction(RoomsListUiAction.LoadForTerritory(territoryId))
                }
            },
            onImeKeyAction = viewModel::moveFocusImeAction
        )
        territoryUi?.id?.let { territoryId ->
            SearchComponent(searchText, onValueChange = roomsListViewModel::onSearchTextChange)
            RoomsListView(
                navController = appState.commonNavController,
                territoryInput = NavigationInput.TerritoryInput(territoryId),
                isForTerritory = true
            )
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryRoomView() {
    JWSuiteTheme {
        Surface {
            /*TerritoryRoomView(
                localityViewModel = TerritoryRoomViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current)
            )*/
        }
    }
}