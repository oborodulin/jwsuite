package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.room.DeleteRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.DeleteTerritoryRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.room.RoomUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.RoomsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Territoring.RoomsListViewModelImpl"

@HiltViewModel
class RoomsListViewModelImpl @Inject constructor(
    private val useCases: RoomUseCases,
    private val converter: RoomsListConverter
) : RoomsListViewModel,
    ListViewModel<List<RoomsListItem>, UiState<List<RoomsListItem>>, RoomsListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RoomsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(RoomsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RoomsListUiAction.Load -> loadRooms(action.houseId, action.territoryId)
            is RoomsListUiAction.EditRoom -> {
                submitSingleEvent(
                    RoomsListUiSingleEvent.OpenRoomScreen(
                        NavRoutes.Room.routeForRoom(NavigationInput.RoomInput(action.roomId))
                    )
                )
            }

            is RoomsListUiAction.DeleteRoom -> deleteRoom(action.roomId)
            is RoomsListUiAction.DeleteTerritoryRoom -> deleteTerritoryRoom(action.roomId)
        }
        return job
    }

    private fun loadRooms(houseId: UUID? = null, territoryId: UUID? = null): Job {
        Timber.tag(TAG)
            .d("loadRooms(...) called: houseId = %s; territoryId = %s", houseId, territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRoomsUseCase.execute(GetRoomsUseCase.Request(houseId, territoryId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteRoom(roomId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteRoom(...) called: roomId = %s", roomId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteRoomUseCase.execute(DeleteRoomUseCase.Request(roomId)).collect {}
        }
        return job
    }

    private fun deleteTerritoryRoom(roomId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteTerritoryRoom(...) called: roomId = %s", roomId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryRoomUseCase.execute(DeleteTerritoryRoomUseCase.Request(roomId))
                .collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<RoomsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : RoomsListViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = null
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: RoomsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            RoomsListItem(
                id = UUID.randomUUID(),
                roomNum = 1,
                roomFullNum = "ул. Независимости, д. 1Б, кв. 1",
                houseFullNum = "1Б",
                isIntercom = true,
                isResidential = true,
                isForeignLanguage = false,
                territoryFullCardNum = "ХК-1",
                info = listOf("семья из 3 чнловек")
            ),
            RoomsListItem(
                id = UUID.randomUUID(),
                roomNum = 1,
                roomFullNum = "ул. Независимости, д. 145, кв. 1",
                houseFullNum = "145",
                isIntercom = true,
                isResidential = true,
                isForeignLanguage = true,
                territoryFullCardNum = "ХК-1",
                info = listOf("язык: Турецкий")
            )
        )
    }
}