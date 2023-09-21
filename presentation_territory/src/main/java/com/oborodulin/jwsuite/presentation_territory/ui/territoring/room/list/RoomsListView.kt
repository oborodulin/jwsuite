package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.HouseInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.RoomsListView"

@Composable
fun RoomsListView(
    viewModel: RoomsListViewModelImpl = hiltViewModel(),
    navController: NavController,
    houseInput: HouseInput? = null,
    territoryInput: TerritoryInput? = null
) {
    Timber.tag(TAG).d(
        "RoomsListView(...) called: territoryInput = %s", territoryInput
    )
    LaunchedEffect(houseInput?.houseId, territoryInput?.territoryId) {
        Timber.tag(TAG)
            .d("RoomsListView: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(
            RoomsListUiAction.Load(houseInput?.houseId, territoryInput?.territoryId)
        )
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (territoryInput?.territoryId) {
                null -> HouseRoomsList(
                    rooms = it,
                    viewModel = viewModel,
                    onEdit = { room ->
                        viewModel.submitAction(RoomsListUiAction.EditRoom(room.id))
                    },
                    onDelete = { room ->
                        viewModel.submitAction(RoomsListUiAction.DeleteRoom(room.id))
                    }
                ) {}

                else -> TerritoryRoomsList(
                    rooms = it,
                    viewModel = viewModel,
                    onProcess = { room ->
                        viewModel.submitAction(RoomsListUiAction.EditRoom(room.id))
                    },
                    onDelete = { room ->
                        viewModel.submitAction(RoomsListUiAction.DeleteTerritoryRoom(room.id))
                    }
                ) {}
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("RoomsListView: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is RoomsListUiSingleEvent.OpenRoomScreen -> {
                    navController.navigate(it.navRoute)
                }

                is RoomsListUiSingleEvent.OpenTerritoryRoomScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun HouseRoomsList(
    rooms: List<RoomsListItem>,
    viewModel: RoomsListViewModel,
    onEdit: (RoomsListItem) -> Unit,
    onDelete: (RoomsListItem) -> Unit,
    onClick: (RoomsListItem) -> Unit
) {
    Timber.tag(TAG).d("HouseRoomsList(...) called")
    if (rooms.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = rooms.filter { it.selected }
                .getOrNull(0)?.let { rooms.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(rooms, key = { _, item -> item.id }) { _, room ->
                ListItemComponent(
                    item = room,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(room) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(R.string.dlg_confirm_del_room, room.roomFullNum)
                        ) { onDelete(room) }),
                    selected = room.selected,
                    onClick = {
                        viewModel.singleSelectItem(room)
                        onClick(room)
                    }
                )
            }
        }
    } else {
        Text(
            text = stringResource(R.string.rooms_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TerritoryRoomsList(
    rooms: List<RoomsListItem>,
    viewModel: RoomsListViewModel,
    onProcess: (RoomsListItem) -> Unit,
    onDelete: (RoomsListItem) -> Unit,
    onClick: (RoomsListItem) -> Unit
) {
    Timber.tag(TAG).d("TerritoryRoomsList(...) called")
    if (rooms.isNotEmpty()) {
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = rooms.filter { it.selected }
                .getOrNull(0)?.let { rooms.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            itemsIndexed(rooms, key = { _, item -> item.id }) { _, room ->
                ListItemComponent(
                    item = room,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onProcess(room) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(
                                R.string.dlg_confirm_del_territory_room,
                                room.roomFullNum
                            )
                        ) { onDelete(room) }),
                    selected = room.selected,
                    onClick = {
                        viewModel.singleSelectItem(room)
                        onClick(room)
                    }
                )
            }
        }
    } else {
        Text(
            text = stringResource(R.string.territory_rooms_list_empty_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRoomsEditableList() {
    JWSuiteTheme {
        Surface {
            HouseRoomsList(
                rooms = RoomsListViewModelImpl.previewList(LocalContext.current),
                viewModel = RoomsListViewModelImpl.previewModel(LocalContext.current),
                onEdit = {},
                onDelete = {},
                onClick = {}
            )
        }
    }
}
