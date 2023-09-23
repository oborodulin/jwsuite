package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogSingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.room.RoomUseCases
import com.oborodulin.jwsuite.domain.usecases.room.SaveTerritoryRoomsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.TerritoryRoomViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryRoomViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val territoryUseCases: TerritoryUseCases,
    private val houseUseCases: RoomUseCases,
    private val converter: TerritoryConverter,
    private val houseMapper: RoomToRoomsListItemMapper
) : TerritoryRoomViewModel,
    DialogSingleViewModel<TerritoryUi, UiState<TerritoryUi>, TerritoryRoomUiAction, UiSingleEvent, TerritoryRoomFields, InputWrapper>(
        state, initFocusedTextField = TerritoryRoomFields.TERRITORY_ROOM_ROOM
    ) {
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryRoomFields.TERRITORY_ROOM_TERRITORY.name, InputListItemWrapper()
        )
    }
    override val room: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryRoomFields.TERRITORY_ROOM_ROOM.name, InputListItemWrapper()
        )
    }

    override val areInputsValid = flow { emit(room.value.errorId == null) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<TerritoryUi> = UiState.Loading

    override suspend fun handleAction(action: TerritoryRoomUiAction): Job {
        Timber.tag(TAG).d("handleAction(TerritoryRoomUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryRoomUiAction.Load -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_room_new_subheader)
                loadTerritory(action.territoryId)
            }

            is TerritoryRoomUiAction.Save -> saveTerritoryRooms(action.roomIds)
        }
        return job
    }

    private fun loadTerritory(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadTerritory(UUID) called: %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            territoryUseCases.getTerritoryUseCase.execute(GetTerritoryUseCase.Request(territoryId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveTerritoryRooms(roomIds: List<UUID> = emptyList()): Job {
        Timber.tag(TAG).d(
            "saveTerritoryRooms() called: houseId = %s; territoryId = %s; roomIds.size = %d",
            room.value.item?.itemId,
            territory.value.item?.itemId,
            roomIds.size
        )
        val job = viewModelScope.launch(errorHandler) {
            houseUseCases.saveTerritoryRoomsUseCase.execute(
                SaveTerritoryRoomsUseCase.Request(roomIds, territory.value.item?.itemId!!)
            ).collect {
                Timber.tag(TAG).d("saveTerritoryRoom() collect: %s", it)
                if (it is Result.Success) {
                    room.value.item?.itemId?.let { roomId ->
                        setSavedListItem(ListItemModel(roomId, room.value.item?.headline.orEmpty()))
                    }
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryRoomFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryStreetModel) called: territoryStreetUi = %s",
                uiModel
            )
        initStateValue(
            TerritoryRoomFields.TERRITORY_ROOM_TERRITORY, territory,
            uiModel.toTerritoriesListItem()
        )
        initStateValue(TerritoryRoomFields.TERRITORY_ROOM_ROOM, room, ListItemModel())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryRoomInputEvent.Territory -> setStateValue(
                        TerritoryRoomFields.TERRITORY_ROOM_TERRITORY, territory, event.input,
                        true
                    )

                    is TerritoryRoomInputEvent.Room ->
                        when (TerritoryRoomInputValidator.Room.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoryRoomFields.TERRITORY_ROOM_ROOM, room, event.input,
                                true
                            )

                            else -> setStateValue(
                                TerritoryRoomFields.TERRITORY_ROOM_ROOM, room, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryRoomInputEvent.Territory -> setStateValue(
                        TerritoryRoomFields.TERRITORY_ROOM_TERRITORY, territory, null
                    )

                    is TerritoryRoomInputEvent.Room ->
                        setStateValue(
                            TerritoryRoomFields.TERRITORY_ROOM_ROOM, room,
                            TerritoryRoomInputValidator.Room.errorIdOrNull(event.input.headline)
                        )

                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoryRoomInputValidator.Room.errorIdOrNull(room.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = TerritoryRoomFields.TERRITORY_ROOM_ROOM.name, errorId = it
                )
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritoryRoomFields.valueOf(error.fieldName)) {
                TerritoryRoomFields.TERRITORY_ROOM_ROOM -> room.value.copy(
                    errorId = error.errorId
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryRoomViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val territory = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val room =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())

                override val areInputsValid = MutableStateFlow(true)

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: TerritoryRoomUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryRoomFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context) = TerritoryViewModelImpl.previewUiModel(ctx)
    }
}