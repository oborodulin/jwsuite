package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
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
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.domain.usecases.room.GetRoomUseCase
import com.oborodulin.jwsuite.domain.usecases.room.RoomUseCases
import com.oborodulin.jwsuite.domain.usecases.room.SaveRoomUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single.HouseViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.EntranceUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.FloorUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.RoomConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomToRoomsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomUiToRoomMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.toHousesListItem
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Housing.RoomViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class RoomViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RoomUseCases,
    private val converter: RoomConverter,
    private val roomUiMapper: RoomUiToRoomMapper,
    private val roomMapper: RoomToRoomsListItemMapper
) : RoomViewModel,
    DialogViewModel<RoomUi, UiState<RoomUi>, RoomUiAction, UiSingleEvent, RoomFields, InputWrapper>(
        state, RoomFields.ROOM_ID.name, RoomFields.ROOM_TERRITORY
    ) {
    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RoomFields.ROOM_LOCALITY.name, InputListItemWrapper())
    }
    override val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RoomFields.ROOM_LOCALITY_DISTRICT.name, InputListItemWrapper())
    }
    override val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RoomFields.ROOM_MICRODISTRICT.name, InputListItemWrapper())
    }
    override val street: StateFlow<InputListItemWrapper<StreetsListItem>> by lazy {
        state.getStateFlow(RoomFields.ROOM_STREET.name, InputListItemWrapper())
    }
    override val house: StateFlow<InputListItemWrapper<HousesListItem>> by lazy {
        state.getStateFlow(RoomFields.ROOM_HOUSE.name, InputListItemWrapper())
    }
    override val entrance: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RoomFields.ROOM_ENTRANCE.name, InputListItemWrapper())
    }
    override val floor: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RoomFields.ROOM_FLOOR.name, InputListItemWrapper())
    }
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(RoomFields.ROOM_TERRITORY.name, InputListItemWrapper())
    }
    override val roomNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RoomFields.ROOM_NUM.name, InputWrapper())
    }
    override val isIntercom: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RoomFields.ROOM_IS_INTERCOM.name, InputWrapper())
    }
    override val isResidential: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RoomFields.ROOM_IS_RESIDENTIAL.name, InputWrapper())
    }
    override val isForeignLanguage: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RoomFields.ROOM_IS_FOREIGN_LANGUAGE.name, InputWrapper())
    }
    override val roomDesc: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(RoomFields.ROOM_DESC.name, InputWrapper())
    }

    override val areInputsValid = combine(locality, street, house, roomNum)
    { locality, street, house, roomNum ->
        locality.errorId == null && street.errorId == null && house.errorId == null && roomNum.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RoomUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(RoomUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RoomUiAction.Load -> when (action.roomId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.room_new_subheader)
                    submitState(UiState.Success(RoomUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.room_subheader)
                    loadRoom(action.roomId)
                }
            }

            is RoomUiAction.Save -> saveRoom()
        }
        return job
    }

    private fun loadRoom(roomId: UUID): Job {
        Timber.tag(TAG).d("loadRoom(UUID) called: roomId = %s", roomId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRoomUseCase.execute(GetRoomUseCase.Request(roomId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun saveRoom(): Job {
        val houseUi = HouseUi(houseNum = house.value.item?.houseNum)
        houseUi.id = house.value.item?.itemId
        val entranceUi = EntranceUi()
        entranceUi.id = entrance.value.item?.itemId
        val floorUi = FloorUi()
        floorUi.id = floor.value.item?.itemId
        val territoryUi = TerritoryUi()
        territoryUi.id = territory.value.item?.itemId

        val roomUi = RoomUi(
            house = houseUi,
            entrance = entranceUi,
            floor = floorUi,
            territory = territoryUi,
            roomNum = roomNum.value.value.toInt(),
            isIntercom = isIntercom.value.value.toBooleanStrictOrNull(),
            isResidential = isResidential.value.value.toBoolean(),
            isForeignLanguage = isForeignLanguage.value.value.toBoolean(),
            roomDesc = roomDesc.value.value.ifEmpty { null }
        )
        roomUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d(
            "saveRoom() called: UI model %s; houseUi.id = %s; entranceUi.id = %s; floorUi.id = %s",
            roomUi, houseUi.id, entranceUi.id, floorUi.id
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveRoomUseCase.execute(SaveRoomUseCase.Request(roomUiMapper.map(roomUi)))
                .collect {
                    Timber.tag(TAG).d("saveRoom() collect: %s", it)
                    if (it is Result.Success) {
                        setStateValue(RoomFields.ROOM_ID, id, it.data.room.id.toString(), true)
                        setSavedListItem(roomMapper.map(it.data.room))
                    }
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<RoomFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: RoomUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG).d("initFieldStatesByUiModel(RoomUi) called: uiModel = %s", uiModel)
        uiModel.id?.let { initStateValue(RoomFields.ROOM_ID, id, it.toString()) }
        initStateValue(
            RoomFields.ROOM_LOCALITY, locality,
            ListItemModel(uiModel.locality.id, uiModel.locality.localityFullName)
        )
        initStateValue(
            RoomFields.ROOM_LOCALITY_DISTRICT, localityDistrict,
            ListItemModel(
                uiModel.localityDistrict?.id, uiModel.localityDistrict?.districtName.orEmpty()
            )
        )
        initStateValue(
            RoomFields.ROOM_MICRODISTRICT, microdistrict,
            ListItemModel(
                uiModel.microdistrict?.id, uiModel.microdistrict?.microdistrictFullName.orEmpty()
            )
        )
        initStateValue(RoomFields.ROOM_STREET, street, uiModel.street.toStreetsListItem())
        initStateValue(RoomFields.ROOM_HOUSE, house, uiModel.house.toHousesListItem())
        initStateValue(
            RoomFields.ROOM_ENTRANCE, entrance,
            ListItemModel(uiModel.entrance?.id, uiModel.entrance?.entranceNum?.toString().orEmpty())
        )
        initStateValue(
            RoomFields.ROOM_FLOOR, floor,
            ListItemModel(uiModel.floor?.id, uiModel.floor?.floorNum?.toString().orEmpty())
        )
        initStateValue(
            RoomFields.ROOM_TERRITORY, territory,
            ListItemModel(uiModel.territory?.id, uiModel.territory?.fullCardNum.orEmpty())
        )
        initStateValue(RoomFields.ROOM_NUM, roomNum, uiModel.roomNum?.toString().orEmpty())
        initStateValue(
            RoomFields.ROOM_IS_INTERCOM, isIntercom, uiModel.isIntercom?.toString().orEmpty()
        )
        initStateValue(
            RoomFields.ROOM_IS_RESIDENTIAL, isResidential, uiModel.isResidential.toString()
        )
        initStateValue(
            RoomFields.ROOM_IS_FOREIGN_LANGUAGE, isForeignLanguage,
            uiModel.isForeignLanguage.toString()
        )
        initStateValue(RoomFields.ROOM_DESC, roomDesc, uiModel.roomDesc.orEmpty())
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RoomInputEvent.Locality -> setStateValue(
                        RoomFields.ROOM_LOCALITY, locality, event.input,
                        RoomInputValidator.Locality.isValid(event.input.headline)
                    )

                    is RoomInputEvent.LocalityDistrict -> setStateValue(
                        RoomFields.ROOM_LOCALITY_DISTRICT, localityDistrict, event.input,
                        true
                    )

                    is RoomInputEvent.Microdistrict -> setStateValue(
                        RoomFields.ROOM_MICRODISTRICT, microdistrict, event.input, true
                    )

                    is RoomInputEvent.Street -> setStateValue(
                        RoomFields.ROOM_STREET, street, event.input,
                        RoomInputValidator.Street.isValid(event.input.headline)
                    )

                    is RoomInputEvent.House -> setStateValue(
                        RoomFields.ROOM_HOUSE, house, event.input,
                        RoomInputValidator.House.isValid(event.input.headline)
                    )

                    is RoomInputEvent.Entrance ->
                        setStateValue(RoomFields.ROOM_ENTRANCE, entrance, event.input, true)

                    is RoomInputEvent.Floor ->
                        setStateValue(RoomFields.ROOM_FLOOR, floor, event.input, true)

                    is RoomInputEvent.Territory ->
                        setStateValue(RoomFields.ROOM_TERRITORY, territory, event.input, true)

                    is RoomInputEvent.RoomNum -> setStateValue(
                        RoomFields.ROOM_NUM, roomNum, event.input.toString(),
                        RoomInputValidator.RoomNum.isValid(event.input.toString())
                    )

                    is RoomInputEvent.IsIntercom -> setStateValue(
                        RoomFields.ROOM_IS_INTERCOM, isIntercom,
                        event.input?.toString().orEmpty(), true
                    )

                    is RoomInputEvent.IsResidential -> setStateValue(
                        RoomFields.ROOM_IS_RESIDENTIAL, isResidential, event.input.toString(),
                        true
                    )

                    is RoomInputEvent.IsForeignLanguage -> setStateValue(
                        RoomFields.ROOM_IS_FOREIGN_LANGUAGE, isForeignLanguage,
                        event.input.toString(), true
                    )

                    is RoomInputEvent.RoomDesc -> setStateValue(
                        RoomFields.ROOM_DESC, roomDesc, event.input.orEmpty(), true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RoomInputEvent.Locality -> setStateValue(
                        RoomFields.ROOM_LOCALITY, locality,
                        RoomInputValidator.Locality.errorIdOrNull(event.input.headline)
                    )

                    is RoomInputEvent.LocalityDistrict ->
                        setStateValue(RoomFields.ROOM_LOCALITY_DISTRICT, localityDistrict, null)

                    is RoomInputEvent.Microdistrict ->
                        setStateValue(RoomFields.ROOM_MICRODISTRICT, microdistrict, null)

                    is RoomInputEvent.Street -> setStateValue(
                        RoomFields.ROOM_STREET, street,
                        RoomInputValidator.Street.errorIdOrNull(event.input.headline)
                    )

                    is RoomInputEvent.House -> setStateValue(
                        RoomFields.ROOM_HOUSE, house,
                        RoomInputValidator.House.errorIdOrNull(event.input.headline)
                    )

                    is RoomInputEvent.Entrance ->
                        setStateValue(RoomFields.ROOM_ENTRANCE, entrance, null)

                    is RoomInputEvent.Floor ->
                        setStateValue(RoomFields.ROOM_FLOOR, floor, null)

                    is RoomInputEvent.Territory ->
                        setStateValue(RoomFields.ROOM_TERRITORY, territory, null)

                    is RoomInputEvent.RoomNum -> setStateValue(
                        RoomFields.ROOM_NUM, roomNum,
                        RoomInputValidator.RoomNum.errorIdOrNull(event.input?.toString().orEmpty())
                    )

                    is RoomInputEvent.IsIntercom ->
                        setStateValue(RoomFields.ROOM_IS_INTERCOM, isIntercom, null)

                    is RoomInputEvent.IsResidential ->
                        setStateValue(RoomFields.ROOM_IS_RESIDENTIAL, isResidential, null)

                    is RoomInputEvent.IsForeignLanguage -> setStateValue(
                        RoomFields.ROOM_IS_FOREIGN_LANGUAGE, isForeignLanguage, null
                    )

                    is RoomInputEvent.RoomDesc ->
                        setStateValue(RoomFields.ROOM_DESC, roomDesc, null)
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RoomInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = RoomFields.ROOM_LOCALITY.name, errorId = it))
        }
        RoomInputValidator.Street.errorIdOrNull(street.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = RoomFields.ROOM_STREET.name, errorId = it))
        }
        RoomInputValidator.House.errorIdOrNull(house.value.item?.headline)?.let {
            inputErrors.add(InputError(fieldName = RoomFields.ROOM_HOUSE.name, errorId = it))
        }
        RoomInputValidator.RoomNum.errorIdOrNull(roomNum.value.value)?.let {
            inputErrors.add(InputError(fieldName = RoomFields.ROOM_NUM.name, errorId = it))
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (RoomFields.valueOf(error.fieldName)) {
                RoomFields.ROOM_LOCALITY -> locality.value.copy(errorId = error.errorId)
                RoomFields.ROOM_STREET -> street.value.copy(errorId = error.errorId)
                RoomFields.ROOM_HOUSE -> house.value.copy(errorId = error.errorId)
                RoomFields.ROOM_NUM -> roomNum.value.copy(errorId = error.errorId)

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : RoomViewModel {
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

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val localityDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val microdistrict = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val street =
                    MutableStateFlow(InputListItemWrapper<StreetsListItem>())
                override val house = MutableStateFlow(InputListItemWrapper<HousesListItem>())
                override val entrance = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val floor = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val territory = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val roomNum = MutableStateFlow(InputWrapper())
                override val isIntercom = MutableStateFlow(InputWrapper())
                override val isResidential = MutableStateFlow(InputWrapper())
                override val isForeignLanguage = MutableStateFlow(InputWrapper())
                override val roomDesc = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: RoomUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RoomFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): RoomUi {
            val roomUi = RoomUi(
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                localityDistrict = LocalityDistrictViewModelImpl.previewUiModel(ctx),
                microdistrict = MicrodistrictViewModelImpl.previewUiModel(ctx),
                street = StreetViewModelImpl.previewUiModel(ctx),
                house = HouseViewModelImpl.previewUiModel(ctx),
                entrance = null,
                floor = null,
                territory = TerritoryViewModelImpl.previewUiModel(ctx),
                roomNum = 1,
                isIntercom = null,
                isResidential = true,
                isForeignLanguage = false,
                roomDesc = ""
            )
            roomUi.id = UUID.randomUUID()
            return roomUi
        }
    }
}