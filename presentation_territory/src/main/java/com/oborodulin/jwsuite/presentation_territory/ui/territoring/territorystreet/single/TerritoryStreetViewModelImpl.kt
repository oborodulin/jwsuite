package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

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
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.SaveTerritoryStreetUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryStreetConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetToTerritoryStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetUiToTerritoryStreetMapper
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Territoring.TerritoryStreetViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryStreetViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryStreetConverter,
    private val territoryStreetUiMapper: TerritoryStreetUiToTerritoryStreetMapper,
    private val territoryStreetMapper: TerritoryStreetToTerritoryStreetsListItemMapper
) : TerritoryStreetViewModel,
    DialogViewModel<TerritoryStreetUiModel, UiState<TerritoryStreetUiModel>, TerritoryStreetUiAction, UiSingleEvent, TerritoryStreetFields, InputWrapper>(
        state, TerritoryStreetFields.TERRITORY_STREET_ID.name,
        TerritoryStreetFields.TERRITORY_STREET_TERRITORY
    ) {
    val localityId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE.name, InputWrapper())
    }

    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryStreetFields.TERRITORY_STREET_TERRITORY.name, InputListItemWrapper()
        )
    }
    override val street: StateFlow<InputListItemWrapper<StreetsListItem>> by lazy {
        state.getStateFlow(
            TerritoryStreetFields.TERRITORY_STREET_STREET.name, InputListItemWrapper()
        )
    }
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR.name, InputWrapper()
        )
    }
    override val isEvenSide: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE.name, InputWrapper())
    }
    override val estimatedHouses: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES.name, InputWrapper())
    }

    override val areInputsValid = flow { emit(street.value.errorId == null) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    override fun initState(): UiState<TerritoryStreetUiModel> = UiState.Loading

    override suspend fun handleAction(action: TerritoryStreetUiAction): Job {
        Timber.tag(TAG).d("handleAction(TerritoryStreetUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryStreetUiAction.Load -> when (action.territoryStreetId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_street_new_subheader)
                    loadTerritoryStreetUiModel(action.territoryId!!)
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_street_subheader)
                    loadTerritoryStreetUiModel(action.territoryId!!, action.territoryStreetId)
                }
            }

            is TerritoryStreetUiAction.Save -> saveTerritoryStreet()
        }
        return job
    }

    private fun loadTerritoryStreetUiModel(
        territoryId: UUID, territoryStreetId: UUID? = null
    ): Job {
        Timber.tag(TAG).d(
            "loadTerritoryStreetUiModel(...) called: territoryId = %s; territoryStreetId = %s",
            territoryId, territoryStreetId
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryStreetUseCase.execute(
                GetTerritoryStreetUseCase.Request(territoryId, territoryStreetId)
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

    private fun saveTerritoryStreet(): Job {
        val streetUi = StreetUi()
        streetUi.id = street.value.item?.itemId

        val territoryStreetUi = TerritoryStreetUi(
            territoryId = territory.value.item?.itemId!!,
            street = streetUi,
            isPrivateSector = isPrivateSector.value.value.toBooleanStrictOrNull(),
            isEvenSide = isEvenSide.value.value.toBooleanStrictOrNull(),
            estimatedHouses = estimatedHouses.value.value.toIntOrNull()
        )
        territoryStreetUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d(
            "saveTerritoryStreet() called: UI territoryStreetUi = %s; streetUi.id = %s",
            territoryStreetUi, streetUi.id
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveTerritoryStreetUseCase.execute(
                SaveTerritoryStreetUseCase.Request(territoryStreetUiMapper.map(territoryStreetUi))
            ).collect {
                Timber.tag(TAG).d("saveTerritoryStreet() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(territoryStreetMapper.map(it.data.territoryStreet))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryStreetFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryStreetUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryStreetModel) called: territoryStreetUi = %s",
                uiModel
            )
        uiModel.id?.let {
            initStateValue(TerritoryStreetFields.TERRITORY_STREET_ID, id, it.toString())
        }
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_TERRITORY, territory,
            ListItemModel(uiModel.territory.id, uiModel.territory.fullCardNum)
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_STREET, street,
            uiModel.territoryStreet.street.toStreetsListItem()
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR, isPrivateSector,
            uiModel.territoryStreet.isPrivateSector.toString()
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE, isEvenSide,
            uiModel.territoryStreet.isEvenSide.toString()
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES, estimatedHouses,
            uiModel.territoryStreet.estimatedHouses?.toString().orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryStreetInputEvent.Street ->
                        when (TerritoryStreetInputValidator.Street.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoryStreetFields.TERRITORY_STREET_STREET, street, event.input,
                                true
                            )

                            else -> setStateValue(
                                TerritoryStreetFields.TERRITORY_STREET_STREET, street, event.input
                            )
                        }

                    is TerritoryStreetInputEvent.IsPrivateSector ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR,
                            isPrivateSector, event.input.toString(), true
                        )

                    is TerritoryStreetInputEvent.IsEvenSide ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE, isEvenSide,
                            event.input.toString(), true
                        )

                    is TerritoryStreetInputEvent.EstHouses ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES, estimatedHouses,
                            event.input.toString(), true
                        )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryStreetInputEvent.Street ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_STREET, street,
                            TerritoryStreetInputValidator.Street.errorIdOrNull(event.input.headline)
                        )

                    is TerritoryStreetInputEvent.IsPrivateSector ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR,
                            isPrivateSector, null
                        )

                    is TerritoryStreetInputEvent.IsEvenSide ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE, isEvenSide, null
                        )

                    is TerritoryStreetInputEvent.EstHouses ->
                        setStateValue(
                            TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES, estimatedHouses, null
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoryStreetInputValidator.Street.errorIdOrNull(street.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = TerritoryStreetFields.TERRITORY_STREET_STREET.name, errorId = it
                )
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritoryStreetFields.valueOf(error.fieldName)) {
                TerritoryStreetFields.TERRITORY_STREET_STREET -> street.value.copy(
                    errorId = error.errorId
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryStreetViewModel {
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
                override val territory = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val street =
                    MutableStateFlow(InputListItemWrapper<StreetsListItem>())
                override val isPrivateSector = MutableStateFlow(InputWrapper())
                override val isEvenSide = MutableStateFlow(InputWrapper())
                override val estimatedHouses = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: TerritoryStreetUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryStreetFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean, onSuccess: () -> Unit
                ) {
                }

                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): TerritoryStreetUiModel {
            val territoryStreetUi = TerritoryStreetUi(
                territoryId = UUID.randomUUID(),
                street = StreetViewModelImpl.previewUiModel(ctx),
                isEvenSide = true,
                isPrivateSector = true,
                estimatedHouses = 38
            )
            territoryStreetUi.id = UUID.randomUUID()
            val territoryStreetUiModel = TerritoryStreetUiModel(
                territoryStreet = territoryStreetUi,
                territory = TerritoryViewModelImpl.previewUiModel(ctx),
                streets = StreetsListViewModelImpl.previewList(ctx)
            )
            territoryStreetUiModel.id = UUID.randomUUID()
            return territoryStreetUiModel
        }
    }
}