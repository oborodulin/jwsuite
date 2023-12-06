package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "Territoring.TerritoryDetailsViewModelImpl"

@HiltViewModel
class TerritoryDetailsViewModelImpl @Inject constructor() : TerritoryDetailsViewModel,
    MviViewModel<TerritoryDetailsUi, UiState<TerritoryDetailsUi>, TerritoryDetailsUiAction, TerritoryDetailsUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryDetailsUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(TerritoryDetailsUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryDetailsUiAction.EditTerritoryStreet -> {
                submitSingleEvent(
                    TerritoryDetailsUiSingleEvent.OpenTerritoryStreetScreen(
                        NavRoutes.TerritoryStreet.routeForTerritoryStreet(
                            NavigationInput.TerritoryStreetInput(
                                action.territoryId, action.territoryStreetId
                            )
                        )
                    )
                )
            }

            is TerritoryDetailsUiAction.EditTerritoryHouse -> {
                submitSingleEvent(
                    TerritoryDetailsUiSingleEvent.OpenTerritoryHouseScreen(
                        NavRoutes.TerritoryHouse.routeForTerritoryHouse(
                            NavigationInput.TerritoryHouseInput(
                                action.territoryId, action.houseId
                            )
                        )
                    )
                )
            }

            is TerritoryDetailsUiAction.EditTerritoryEntrance -> {
                submitSingleEvent(
                    TerritoryDetailsUiSingleEvent.OpenTerritoryEntranceScreen(
                        NavRoutes.TerritoryEntrance.routeForTerritoryEntrance(
                            NavigationInput.TerritoryEntranceInput(
                                action.territoryId, action.entranceId
                            )
                        )
                    )
                )
            }

            is TerritoryDetailsUiAction.EditTerritoryFloor -> {
                submitSingleEvent(
                    TerritoryDetailsUiSingleEvent.OpenTerritoryFloorScreen(
                        NavRoutes.TerritoryFloor.routeForTerritoryFloor(
                            NavigationInput.TerritoryFloorInput(
                                action.territoryId, action.floorId
                            )
                        )
                    )
                )
            }

            is TerritoryDetailsUiAction.EditTerritoryRoom -> {
                submitSingleEvent(
                    TerritoryDetailsUiSingleEvent.OpenTerritoryRoomScreen(
                        NavRoutes.TerritoryRoom.routeForTerritoryRoom(
                            NavigationInput.TerritoryRoomInput(
                                action.territoryId, action.roomId
                            )
                        )
                    )
                )
            }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: TerritoryDetailsUi): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryDetailsViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<TerritoryDetailsUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override fun handleActionJob(action: () -> Unit, afterAction: (CoroutineScope) -> Unit) {}
                override fun submitAction(action: TerritoryDetailsUiAction): Job? = null
            }

        fun previewList(ctx: Context) = TerritoryDetailsUi()
    }
}