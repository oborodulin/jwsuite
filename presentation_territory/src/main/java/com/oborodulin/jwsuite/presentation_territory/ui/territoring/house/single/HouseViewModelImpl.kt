package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

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
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryStreetConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetToTerritoryStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetUiToTerritoryStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
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
class HouseViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryStreetConverter,
    private val territoryStreetUiMapper: TerritoryStreetUiToTerritoryStreetMapper,
    private val territoryStreetMapper: TerritoryStreetToTerritoryStreetsListItemMapper
) : HouseViewModel,
    DialogSingleViewModel<TerritoryStreetUiModel, UiState<TerritoryStreetUiModel>, HouseUiAction, UiSingleEvent, HouseFields, InputWrapper>(
        state, HouseFields.HOUSE_ID.name,
        HouseFields.HOUSE_TERRITORY
    ) {
    val localityId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_BUSINESS.name, InputWrapper())
    }

    override val territory: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            HouseFields.HOUSE_TERRITORY.name, InputListItemWrapper()
        )
    }
    override val street: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            HouseFields.HOUSE_STREET.name, InputListItemWrapper()
        )
    }
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            HouseFields.HOUSE_IS_PRIVATE_SECTOR.name, InputWrapper()
        )
    }
    override val isEvenSide: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_IS_BUSINESS.name, InputWrapper())
    }
    override val estimatedHouses: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(HouseFields.HOUSE_ZIP_CODE.name, InputWrapper())
    }

    override val areInputsValid = flow { emit(street.value.errorId == null) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    override fun initState(): UiState<TerritoryStreetUiModel> = UiState.Loading

    override suspend fun handleAction(action: HouseUiAction): Job {
        Timber.tag(TAG).d("handleAction(TerritoryStreetUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is HouseUiAction.Load -> when (action.territoryStreetId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_street_new_subheader)
                    loadTerritoryStreetUiModel(action.territoryId!!)
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_street_subheader)
                    loadTerritoryStreetUiModel(action.territoryId!!, action.territoryStreetId)
                }
            }

            is HouseUiAction.Save -> saveTerritoryStreet()
        }
        return job
    }

    private fun loadTerritoryStreetUiModel(
        territoryId: UUID,
        territoryStreetId: UUID? = null
    ): Job {
        Timber.tag(TAG).d("loadTerritoryStreet(UUID) called: %s", territoryStreetId)
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
            isPrivateSector = isPrivateSector.value.value.toBoolean(),
            isEvenSide = isEvenSide.value.value.toBoolean(),
            estimatedHouses = estimatedHouses.value.value.toInt()
        )
        territoryStreetUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d(
            "saveTerritoryStreet() called: UI model %s; streetUi.id = %s",
            territoryStreetUi,
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

    override fun stateInputFields() = enumValues<HouseFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryStreetUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryStreetModel) called: territoryStreetUi = %s",
                uiModel
            )
        uiModel.id?.let {
            initStateValue(
                HouseFields.HOUSE_ID, id, it.toString()
            )
        }
        initStateValue(
            HouseFields.HOUSE_TERRITORY, territory,
            ListItemModel(
                uiModel.territory.id,
                uiModel.territory.territoryNum.toString()
            )
        )
        initStateValue(
            HouseFields.HOUSE_STREET, street,
            ListItemModel(
                uiModel.territoryStreet.street.id,
                uiModel.territoryStreet.street.streetName
            )
        )
        initStateValue(
            HouseFields.HOUSE_IS_PRIVATE_SECTOR, isPrivateSector,
            uiModel.territoryStreet.isPrivateSector.toString()
        )
        initStateValue(
            HouseFields.HOUSE_IS_BUSINESS, isEvenSide,
            uiModel.territoryStreet.isEvenSide.toString()
        )
        initStateValue(
            HouseFields.HOUSE_ZIP_CODE, estimatedHouses,
            uiModel.territoryStreet.estimatedHouses.toString()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is HouseInputEvent.Street ->
                        when (HouseInputValidator.Street.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                HouseFields.HOUSE_STREET, street, event.input,
                                true
                            )

                            else -> setStateValue(
                                HouseFields.HOUSE_STREET, street, event.input
                            )
                        }

                    is HouseInputEvent.IsPrivateSector ->
                        setStateValue(
                            HouseFields.HOUSE_IS_PRIVATE_SECTOR,
                            isPrivateSector, event.input.toString(), true
                        )

                    is HouseInputEvent.IsEvenSide ->
                        setStateValue(
                            HouseFields.HOUSE_IS_BUSINESS, isEvenSide,
                            event.input.toString(), true
                        )

                    is HouseInputEvent.EstHouses ->
                        setStateValue(
                            HouseFields.HOUSE_ZIP_CODE, estimatedHouses,
                            event.input.toString(), true
                        )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is HouseInputEvent.Street ->
                        setStateValue(
                            HouseFields.HOUSE_STREET, street,
                            HouseInputValidator.Street.errorIdOrNull(event.input.headline)
                        )

                    is HouseInputEvent.IsPrivateSector ->
                        setStateValue(
                            HouseFields.HOUSE_IS_PRIVATE_SECTOR,
                            isPrivateSector, null
                        )

                    is HouseInputEvent.IsEvenSide ->
                        setStateValue(
                            HouseFields.HOUSE_IS_BUSINESS, isEvenSide, null
                        )

                    is HouseInputEvent.EstHouses ->
                        setStateValue(
                            HouseFields.HOUSE_ZIP_CODE, estimatedHouses, null
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        HouseInputValidator.Street.errorIdOrNull(street.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = HouseFields.HOUSE_STREET.name, errorId = it
                )
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (HouseFields.valueOf(error.fieldName)) {
                HouseFields.HOUSE_STREET -> street.value.copy(
                    errorId = error.errorId
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : HouseViewModel {
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
                override fun submitAction(action: HouseUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: HouseFields, isFocused: Boolean
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