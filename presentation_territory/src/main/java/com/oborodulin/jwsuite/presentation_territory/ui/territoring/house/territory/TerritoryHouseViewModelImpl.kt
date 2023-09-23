package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

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
import com.oborodulin.jwsuite.domain.usecases.house.HouseUseCases
import com.oborodulin.jwsuite.domain.usecases.house.SaveTerritoryHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HouseToHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.TerritoryHouseViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryHouseViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val territoryUseCases: TerritoryUseCases,
    private val houseUseCases: HouseUseCases,
    private val converter: TerritoryConverter,
    private val houseMapper: HouseToHousesListItemMapper
) : TerritoryHouseViewModel,
    DialogSingleViewModel<TerritoryUi, UiState<TerritoryUi>, TerritoryHouseUiAction, UiSingleEvent, TerritoryHouseFields, InputWrapper>(
        state, initFocusedTextField = TerritoryHouseFields.TERRITORY_HOUSE_HOUSE
    ) {
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY.name, InputListItemWrapper()
        )
    }
    override val house: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryHouseFields.TERRITORY_HOUSE_HOUSE.name, InputListItemWrapper()
        )
    }

    override val areInputsValid = flow { emit(house.value.errorId == null) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<TerritoryUi> = UiState.Loading

    override suspend fun handleAction(action: TerritoryHouseUiAction): Job {
        Timber.tag(TAG).d("handleAction(TerritoryHouseUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryHouseUiAction.Load -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_house_new_subheader)
                loadTerritory(action.territoryId)
            }

            is TerritoryHouseUiAction.Save -> saveTerritoryHouses(action.houseIds)
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

    private fun saveTerritoryHouses(houseIds: List<UUID> = emptyList()): Job {
        Timber.tag(TAG).d(
            "saveTerritoryHouses() called: houseId = %s; territoryId = %s; houseIds.size = %d",
            house.value.item?.itemId,
            territory.value.item?.itemId,
            houseIds.size
        )
        val job = viewModelScope.launch(errorHandler) {
            houseUseCases.saveTerritoryHousesUseCase.execute(
                SaveTerritoryHousesUseCase.Request(houseIds, territory.value.item?.itemId!!)
            ).collect {
                Timber.tag(TAG).d("saveTerritoryHouse() collect: %s", it)
                if (it is Result.Success) {
                    house.value.item?.itemId?.let { houseId ->
                        setSavedListItem(
                            ListItemModel(houseId, house.value.item?.headline.orEmpty())
                        )
                    }
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryHouseFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryStreetModel) called: territoryStreetUi = %s",
                uiModel
            )
        initStateValue(
            TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY, territory,
            uiModel.toTerritoriesListItem()
        )
        initStateValue(TerritoryHouseFields.TERRITORY_HOUSE_HOUSE, house, ListItemModel())
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryHouseInputEvent.Territory -> setStateValue(
                        TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY, territory, event.input,
                        true
                    )

                    is TerritoryHouseInputEvent.House ->
                        when (TerritoryHouseInputValidator.House.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoryHouseFields.TERRITORY_HOUSE_HOUSE, house, event.input,
                                true
                            )

                            else -> setStateValue(
                                TerritoryHouseFields.TERRITORY_HOUSE_HOUSE, house, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryHouseInputEvent.Territory -> setStateValue(
                        TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY, territory, null
                    )

                    is TerritoryHouseInputEvent.House ->
                        setStateValue(
                            TerritoryHouseFields.TERRITORY_HOUSE_HOUSE, house,
                            TerritoryHouseInputValidator.House.errorIdOrNull(event.input.headline)
                        )

                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoryHouseInputValidator.House.errorIdOrNull(house.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = TerritoryHouseFields.TERRITORY_HOUSE_HOUSE.name, errorId = it
                )
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritoryHouseFields.valueOf(error.fieldName)) {
                TerritoryHouseFields.TERRITORY_HOUSE_HOUSE -> house.value.copy(
                    errorId = error.errorId
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryHouseViewModel {
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
                override val house =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())

                override val areInputsValid = MutableStateFlow(true)

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: TerritoryHouseUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryHouseFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(isPartialInputsValid: Boolean, onSuccess: () -> Unit) {}
                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context) = TerritoryViewModelImpl.previewUiModel(ctx)
    }
}