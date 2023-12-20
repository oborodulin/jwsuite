package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.house.HouseUseCases
import com.oborodulin.jwsuite.domain.usecases.house.SaveTerritoryHousesUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHousesUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryHousesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list.HousesListViewModelImpl
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
    private val useCases: HouseUseCases,
    private val converter: TerritoryHousesListConverter
) : TerritoryHouseViewModel,
    DialogViewModel<TerritoryHousesUiModel, UiState<TerritoryHousesUiModel>, TerritoryHouseUiAction, UiSingleEvent, TerritoryHouseFields, InputWrapper>(
        state, initFocusedTextField = TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY
    ) {
    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY.name, InputListItemWrapper()
        )
    }

    private val _checkedListItems: MutableStateFlow<List<HousesListItem>> =
        MutableStateFlow(emptyList())
    override val checkedListItems = _checkedListItems.asStateFlow()

    override val areInputsValid = flow { emit(checkedListItems.value.isNotEmpty()) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    override fun observeCheckedListItems() {
        Timber.tag(TAG).d("observeCheckedListItems() called")
        uiState()?.let { uiState ->
            _checkedListItems.value = uiState.houses.filter { it.checked }
            Timber.tag(TAG).d("checked %d List Items", _checkedListItems.value.size)
        }
    }

    override fun initState(): UiState<TerritoryHousesUiModel> = UiState.Loading

    override suspend fun handleAction(action: TerritoryHouseUiAction): Job {
        Timber.tag(TAG).d("handleAction(TerritoryHouseUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryHouseUiAction.Load -> {
                setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_house_new_subheader)
                loadHousesForTerritory(action.territoryId)
            }

            is TerritoryHouseUiAction.Save -> saveTerritoryHouses()
        }
        return job
    }

    private fun loadHousesForTerritory(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadHousesForTerritory(UUID) called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getHousesForTerritoryUseCase.execute(
                GetHousesForTerritoryUseCase.Request(territoryId)
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun saveTerritoryHouses(): Job {
        val houseIds = _checkedListItems.value.mapNotNull { it.itemId }
        Timber.tag(TAG).d(
            "saveTerritoryHouses() called: territoryId = %s; houseIds.size = %d",
            territory.value.item?.itemId,
            houseIds.size
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveTerritoryHousesUseCase.execute(
                SaveTerritoryHousesUseCase.Request(houseIds, territory.value.item?.itemId!!)
            ).collect {
                Timber.tag(TAG).d("saveTerritoryHouse() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryHouseFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryHousesUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG).d(
            "initFieldStatesByUiModel(TerritoryHousesUiModel) called: uiModel = %s", uiModel
        )
        initStateValue(
            TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY,
            territory,
            uiModel.territory.toTerritoriesListItem()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow().onEach { event ->
            when (event) {
                is TerritoryHouseInputEvent.Territory -> setStateValue(
                    TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY, territory, event.input, true
                )
            }
        }.debounce(350).collect { event ->
            when (event) {
                is TerritoryHouseInputEvent.Territory -> setStateValue(
                    TerritoryHouseFields.TERRITORY_HOUSE_TERRITORY, territory, null
                )
            }
        }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull() = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) = object : TerritoryHouseViewModel {
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
                MutableStateFlow(HousesListViewModelImpl.previewList(ctx))

            override fun observeCheckedListItems() {}

            override val id = MutableStateFlow(InputWrapper())
            override fun id() = null
            override val territory = MutableStateFlow(InputListItemWrapper<ListItemModel>())
            override val areInputsValid = MutableStateFlow(true)

            override fun submitAction(action: TerritoryHouseUiAction): Job? = null
            override fun handleActionJob(action: () -> Unit, afterAction: (CoroutineScope) -> Unit) {}
            override fun onTextFieldEntered(inputEvent: Inputable) {}
            override fun onTextFieldFocusChanged(
                focusedField: TerritoryHouseFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = TerritoryHousesUiModel(
            territory = TerritoryViewModelImpl.previewUiModel(ctx),
            houses = HousesListViewModelImpl.previewList(ctx)
        )
    }
}