package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_LIST
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomsForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.room.RoomUseCases
import com.oborodulin.jwsuite.domain.usecases.room.SaveTerritoryRoomsUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryRoomsUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryRoomsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Territoring.TerritoryRoomViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryRoomViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RoomUseCases,
    private val converter: TerritoryRoomsListConverter
) : TerritoryRoomViewModel,
    DialogViewModel<TerritoryRoomsUiModel, UiState<TerritoryRoomsUiModel>, TerritoryRoomUiAction, UiSingleEvent, TerritoryRoomFields, InputWrapper>(
        state, initFocusedTextField = TerritoryRoomFields.TERRITORY_ROOM_TERRITORY
    ) {
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryRoomFields.TERRITORY_ROOM_TERRITORY.name, InputListItemWrapper()
        )
    }
    private val _checkedListItems: MutableStateFlow<List<RoomsListItem>> =
        MutableStateFlow(emptyList())
    override val checkedListItems = _checkedListItems.asStateFlow()

    override val areInputsValid = checkedListItems.map { it.isNotEmpty() }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    override fun observeCheckedListItems() {
        if (LOG_MVI_LIST) Timber.tag(TAG).d("observeCheckedListItems() called")
        uiState()?.let { uiState ->
            _checkedListItems.value = uiState.rooms.filter { it.checked }
            if (LOG_MVI_LIST) Timber.tag(TAG)
                .d("checked %d List Items", _checkedListItems.value.size)
        }
    }

    override fun initState(): UiState<TerritoryRoomsUiModel> = UiState.Loading

    override suspend fun handleAction(action: TerritoryRoomUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(TerritoryRoomUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryRoomUiAction.Load -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_room_new_subheader)
                loadRoomsForTerritory(action.territoryId)
            }

            is TerritoryRoomUiAction.Save -> saveTerritoryRooms()
        }
        return job
    }

    private fun loadRoomsForTerritory(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadRoomsForTerritory(UUID) called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRoomsForTerritoryUseCase.execute(
                GetRoomsForTerritoryUseCase.Request(territoryId)
            )
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveTerritoryRooms(): Job {
        val roomIds = _checkedListItems.value.map { it.id }
        Timber.tag(TAG).d(
            "saveTerritoryRooms() called: territoryId = %s; roomIds.size = %d",
            territory.value.item?.itemId, roomIds.size
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveTerritoryRoomsUseCase.execute(
                SaveTerritoryRoomsUseCase.Request(roomIds, territory.value.item?.itemId!!)
            ).collect {
                Timber.tag(TAG).d("saveTerritoryRoom() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryRoomFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryRoomsUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryRoomsUiModel) called: uiModel = %s",
                uiModel
            )
        initStateValue(
            TerritoryRoomFields.TERRITORY_ROOM_TERRITORY, territory,
            uiModel.territory.toTerritoriesListItem()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryRoomInputEvent.Territory -> setStateValue(
                        TerritoryRoomFields.TERRITORY_ROOM_TERRITORY, territory, event.input,
                        true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryRoomInputEvent.Territory -> setStateValue(
                        TerritoryRoomFields.TERRITORY_ROOM_TERRITORY, territory, null
                    )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull() = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryRoomViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val checkedListItems =
                    MutableStateFlow(RoomsListViewModelImpl.previewList(ctx))

                override fun observeCheckedListItems() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val territory = MutableStateFlow(InputListItemWrapper<ListItemModel>())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: TerritoryRoomUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryRoomFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(isPartialInputsValid: Boolean, onSuccess: () -> Unit) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context) = TerritoryRoomsUiModel(
            territory = TerritoryViewModelImpl.previewUiModel(ctx),
            rooms = RoomsListViewModelImpl.previewList(ctx)
        )
    }
}