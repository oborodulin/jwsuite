package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.oborodulin.home.common.ui.components.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.buttons.AddIconButtonComponent
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.util.InputFocusRequester
import com.oborodulin.home.common.ui.components.field.util.inputProcess
import com.oborodulin.home.common.ui.components.search.SearchComponent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryRoomsUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single.RoomUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single.RoomView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single.RoomViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryComboBox
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber
import java.util.EnumMap

private const val TAG = "Territoring.TerritoryRoomView"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TerritoryRoomView(
    territoryRoomsUiModel: TerritoryRoomsUiModel? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModel,
    territoryRoomViewModel: TerritoryRoomViewModel,
    roomViewModel: RoomViewModelImpl = hiltViewModel()
) {
    Timber.tag(TAG).d("TerritoryRoomView(...) called")
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(territoryRoomViewModel.events, lifecycleOwner) {
        territoryRoomViewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle, Lifecycle.State.STARTED
        )
    }

    territoryRoomsUiModel?.let {
        territoryRoomViewModel.onTextFieldEntered(TerritoryRoomInputEvent.Territory(it.territory.toTerritoriesListItem()))
    }
    Timber.tag(TAG).d("Territory Room: CollectAsStateWithLifecycle for all fields")
    val territory by territoryRoomViewModel.territory.collectAsStateWithLifecycle()
    val searchText by territoryRoomViewModel.searchText.collectAsStateWithLifecycle()

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
    val isShowNewSingleDialog by roomViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = roomViewModel,
        loadUiAction = RoomUiAction.Load(),
        confirmUiAction = RoomUiAction.Save,
        dialogView = {
            RoomView(
                territoryUiModel = territoryRoomsUiModel?.territory,
                sharedViewModel = sharedViewModel
            )
        },
        onValueChange = {
            territoryRoomsUiModel?.let {
                territoryRoomViewModel.submitAction(TerritoryRoomUiAction.Load(it.territory.id!!))
            }
        },
        //onShowListDialog = onShowListDialog
    )
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
                    territoryRoomViewModel.onTextFieldFocusChanged(
                        focusedField = TerritoryRoomFields.TERRITORY_ROOM_TERRITORY,
                        isFocused = focusState.isFocused
                    )
                },
            enabled = false,
            sharedViewModel = sharedViewModel,
            singleViewModel = territoryViewModel,
            inputWrapper = territory,
            onImeKeyAction = territoryRoomViewModel::moveFocusImeAction
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchComponent(
                searchText,
                modifier = Modifier.weight(2.8f),
                onValueChange = territoryRoomViewModel::onSearchTextChange
            )
            AddIconButtonComponent { roomViewModel.onOpenDialogClicked() }
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        territoryRoomsUiModel?.let {
            ForTerritoryRoomsList(
                searchedText = searchText.text,
                rooms = it.rooms,
                onChecked = { territoryRoomViewModel.observeCheckedListItems() }
            )
        }
    }
}

@Composable
fun ForTerritoryRoomsList(
    searchedText: String = "",
    rooms: List<RoomsListItem>,
    onChecked: (Boolean) -> Unit,
    onClick: (RoomsListItem) -> Unit = {}
) {
    Timber.tag(TAG).d("ForTerritoryRoomsList(...) called: size = %d", rooms.size)
    if (rooms.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = rooms.filter { it.selected }
                .getOrNull(0)?.let { rooms.indexOf(it) } ?: 0)
        var filteredItems: List<RoomsListItem>
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            filteredItems = if (searchedText.isEmpty()) {
                rooms
            } else {
                rooms.filter { it.doesMatchSearchQuery(searchedText) }
            }
            itemsIndexed(filteredItems, key = { _, item -> item.id }) { _, room ->
                ForTerritoryRoomsListItemComponent(
                    item = room,
                    //selected = room.selected,
                    onChecked = onChecked,
                    onClick = { onClick(room) }
                )
            }
        }
    } else {
        EmptyListTextComponent(R.string.for_territory_rooms_list_empty_text)
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
