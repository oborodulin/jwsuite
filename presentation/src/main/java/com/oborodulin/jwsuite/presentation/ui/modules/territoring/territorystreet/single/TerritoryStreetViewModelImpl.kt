package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.single

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
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.SaveTerritoryStreetUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryStreetConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.street.TerritoryStreetToTerritoryStreetsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.street.TerritoryStreetUiToTerritoryStreetMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single.TerritoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
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
    DialogSingleViewModel<TerritoryStreetUiModel, UiState<TerritoryStreetUiModel>, TerritoryStreetUiAction, UiSingleEvent, TerritoryStreetFields, InputWrapper>(
        state, TerritoryStreetFields.TERRITORY_STREET_ID.name,
        TerritoryStreetFields.TERRITORY_STREET_TERRITORY
    ) {
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryStreetFields.TERRITORY_STREET_TERRITORY.name,
            InputListItemWrapper()
        )
    }
    override val street: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryStreetFields.TERRITORY_STREET_STREET.name,
            InputListItemWrapper()
        )
    }
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR.name,
            InputWrapper()
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
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.territory_street_new_subheader)
                    submitState(UiState.Success(TerritoryStreetUiModel()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.territory_street_subheader)
                    loadTerritoryStreet(action.territoryStreetId)
                }
            }

            is TerritoryStreetUiAction.Save -> saveTerritoryStreet()
        }
        return job
    }

    private fun loadTerritoryStreet(territoryStreetId: UUID): Job {
        Timber.tag(TAG).d("loadTerritoryStreet(UUID) called: %s", territoryStreetId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryStreetUseCase.execute(
                GetTerritoryStreetUseCase.Request(territoryStreetId)
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
        val territoryUi = TerritoryUi()
        territoryUi.id = territory.value.item?.itemId
        val streetUi = StreetUi()
        streetUi.id = street.value.item?.itemId

        val territoryStreetUi = TerritoryStreetUi(
            territory = territoryUi,
            street = streetUi,
            isPrivateSector = isPrivateSector.value.value.toBoolean(),
            isEvenSide = isEvenSide.value.value.toBoolean(),
            estimatedHouses = estimatedHouses.value.value.toInt()
        )
        territoryStreetUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d(
            "saveTerritoryStreet() called: UI model %s; territoryUi.id = %s; streetUi.id = %s",
            territoryStreetUi,
            territoryUi.id,
            streetUi.id
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

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val territoryStreetUi = uiModel as TerritoryStreetUi
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryStreetModel) called: territoryStreetUi = %s",
                territoryStreetUi
            )
        territoryStreetUi.id?.let {
            initStateValue(
                TerritoryStreetFields.TERRITORY_STREET_ID, id, it.toString()
            )
        }
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_TERRITORY, territory,
            ListItemModel(
                territoryStreetUi.territory.id,
                territoryStreetUi.territory.territoryNum.toString()
            )
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_STREET, street,
            ListItemModel(territoryStreetUi.street.id, territoryStreetUi.street.streetName)
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_IS_PRIVATE_SECTOR, isPrivateSector,
            territoryStreetUi.isPrivateSector.toString()
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_IS_EVEN_SIDE, isEvenSide,
            territoryStreetUi.isEvenSide.toString()
        )
        initStateValue(
            TerritoryStreetFields.TERRITORY_STREET_EST_HOUSES, estimatedHouses,
            territoryStreetUi.estimatedHouses.toString()
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
                                TerritoryStreetFields.TERRITORY_STREET_STREET, street,
                                event.input, true
                            )

                            else -> setStateValue(
                                TerritoryStreetFields.TERRITORY_STREET_STREET, street,
                                event.input
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
                            isPrivateSector,
                            null
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
        return if (inputErrors.isEmpty()) null else inputErrors
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
                override val street =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val isPrivateSector = MutableStateFlow(InputWrapper())
                override val isEvenSide = MutableStateFlow(InputWrapper())
                override val estimatedHouses = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: TerritoryStreetUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryStreetFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): TerritoryStreetUi {
            val territoryStreetUi = TerritoryStreetUi(
                territory = TerritoryViewModelImpl.previewUiModel(ctx),
                street = StreetViewModelImpl.previewUiModel(ctx),
                isEvenSide = true,
                isPrivateSector = true,
                estimatedHouses = 38
            )
            territoryStreetUi.id = UUID.randomUUID()
            return territoryStreetUi
        }
    }
}