package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.extensions.toUUIDOrNull
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
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.domain.usecases.territory.GetNextTerritoryNumUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single.MicrodistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.toLocalityUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoriesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.toTerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single.TerritoryCategoryViewModelImpl
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

private const val TAG = "Territoring.TerritoryViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryConverter,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper,
    private val territoryListItemMapper: TerritoryToTerritoriesListItemMapper
) : TerritoryViewModel,
    DialogViewModel<TerritoryUi, UiState<TerritoryUi>, TerritoryUiAction, UiSingleEvent, TerritoryFields, InputWrapper>(
        state, TerritoryFields.TERRITORY_ID.name, TerritoryFields.TERRITORY_NUM
    ) {
    override val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_CONGREGATION.name, InputListItemWrapper())
    }
    override val category: StateFlow<InputListItemWrapper<TerritoryCategoriesListItem>> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_CATEGORY.name, InputListItemWrapper())
    }
    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_LOCALITY.name, InputListItemWrapper())
    }
    override val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_LOCALITY_DISTRICT.name, InputListItemWrapper())
    }
    override val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_MICRODISTRICT.name, InputListItemWrapper())
    }
    override val territoryNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_NUM.name, InputWrapper())
    }
    override val isBusiness: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_IS_BUSINESS.name, InputWrapper())
    }
    override val isGroupMinistry: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_IS_GROUP_MINISTRY.name, InputWrapper())
    }
    override val isActive: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_IS_ACTIVE.name, InputWrapper())
    }
    override val territoryDesc: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_DESC.name, InputWrapper())
    }

    override val areInputsValid =
        combine(category, locality, territoryNum)
        { category, locality, territoryNum ->
            category.errorId == null && locality.errorId == null && territoryNum.errorId == null
            /*stateFlowsArray ->
                var errorIdResult = true
                for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
                errorIdResult*/
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(TerritoryUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryUiAction.Load -> when (action.territoryId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_new_subheader)
                    submitState(UiState.Success(TerritoryUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_subheader)
                    loadTerritory(action.territoryId)
                }
            }

            is TerritoryUiAction.GetNextTerritoryNum -> getNextTerritoryNum(
                action.congregationId, action.territoryCategoryId
            )

            is TerritoryUiAction.Save -> saveTerritory()
            is TerritoryUiAction.EditTerritoryDetails -> {
                submitSingleEvent(
                    TerritoryUiSingleEvent.OpenTerritoryDetailsScreen(
                        NavRoutes.TerritoryDetails.routeForTerritoryDetails(
                            NavigationInput.TerritoryInput(action.territoryId!!)
                        )
                    )
                )
            }
        }
        return job
    }

    private fun loadTerritory(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadTerritory(UUID) called: %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryUseCase.execute(GetTerritoryUseCase.Request(territoryId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun getNextTerritoryNum(congregationId: UUID, territoryCategoryId: UUID): Job {
        Timber.tag(TAG).d(
            "loadTerritory(UUID) called: congregationId = %s; territoryCategoryId = %s",
            congregationId,
            territoryCategoryId
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getNextTerritoryNumUseCase.execute(
                GetNextTerritoryNumUseCase.Request(congregationId, territoryCategoryId)
            ).collect {
                if (it is Result.Success) {
                    onTextFieldEntered(TerritoryInputEvent.TerritoryNum(it.data.territoryNum))
                }
            }
        }
        return job
    }

    private fun saveTerritory(): Job {
        val congregationUi = CongregationUi().also { it.id = congregation.value.item?.itemId }
        val territoryCategoryUi = TerritoryCategoryUi().also { it.id = category.value.item?.itemId }
        val localityDistrictUi =
            LocalityDistrictUi().also { it.id = localityDistrict.value.item?.itemId }
        val microdistrictUi = MicrodistrictUi().also { it.id = microdistrict.value.item?.itemId }
        val territoryUi = TerritoryUi(
            congregation = congregationUi,
            territoryCategory = territoryCategoryUi,
            locality = locality.value.item.toLocalityUi(),
            localityDistrict = localityDistrictUi,
            microdistrict = microdistrictUi,
            territoryNum = territoryNum.value.value.toInt(),
            isBusiness = isBusiness.value.value.toBoolean(),
            isGroupMinistry = isGroupMinistry.value.value.toBoolean(),
            isActive = isActive.value.value.toBoolean(),
            territoryDesc = territoryDesc.value.value.ifEmpty { null }
        ).also { it.id = id.value.value.toUUIDOrNull() }
        Timber.tag(TAG).d("saveTerritory() called: UI model %s", territoryUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveTerritoryUseCase.execute(
                SaveTerritoryUseCase.Request(territoryUiMapper.map(territoryUi))
            ).collect {
                Timber.tag(TAG).d("saveTerritory() collect: %s", it)
                if (it is Result.Success) {
                    //submitState(UiState.Success(territoryMapper.map(it.data.territory)))
                    it.data.territory.id?.let { territoryId ->
                        initStateValue(TerritoryFields.TERRITORY_ID, id, territoryId.toString())
                    }
                    setSavedListItem(territoryListItemMapper.map(it.data.territory))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) Timber.tag(TAG)
            .d("initFieldStatesByUiModel(TerritoryUi) called: uiModel = %s", uiModel)
        uiModel.id?.let { initStateValue(TerritoryFields.TERRITORY_ID, id, it.toString()) }
        initStateValue(
            TerritoryFields.TERRITORY_CONGREGATION, congregation,
            uiModel.congregation.toCongregationsListItem()
        )
        initStateValue(
            TerritoryFields.TERRITORY_CATEGORY, category,
            uiModel.territoryCategory.toTerritoryCategoriesListItem()
        )
        initStateValue(
            TerritoryFields.TERRITORY_LOCALITY, locality,
            ListItemModel(uiModel.locality.id, uiModel.locality.localityName)
        )
        initStateValue(
            TerritoryFields.TERRITORY_LOCALITY_DISTRICT, localityDistrict,
            ListItemModel(
                uiModel.localityDistrict?.id, uiModel.localityDistrict?.districtName.orEmpty()
            )
        )
        initStateValue(
            TerritoryFields.TERRITORY_MICRODISTRICT, microdistrict,
            ListItemModel(
                uiModel.microdistrict?.id, uiModel.microdistrict?.microdistrictName.orEmpty()
            )
        )
        initStateValue(TerritoryFields.TERRITORY_NUM, territoryNum, uiModel.territoryNum.toString())
        initStateValue(
            TerritoryFields.TERRITORY_IS_BUSINESS, isBusiness, uiModel.isBusiness.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_IS_GROUP_MINISTRY, isGroupMinistry,
            uiModel.isGroupMinistry.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_IS_ACTIVE, isActive, uiModel.isActive.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_DESC, territoryDesc, uiModel.territoryDesc.orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryInputEvent.Congregation ->
                        setStateValue(
                            TerritoryFields.TERRITORY_CONGREGATION, congregation, event.input, true
                        )

                    is TerritoryInputEvent.Category -> setStateValue(
                        TerritoryFields.TERRITORY_CATEGORY, category, event.input,
                        TerritoryInputValidator.Category.isValid(event.input.headline)
                    )

                    is TerritoryInputEvent.Locality -> setStateValue(
                        TerritoryFields.TERRITORY_LOCALITY, locality, event.input,
                        TerritoryInputValidator.Locality.isValid(event.input.headline)
                    )

                    is TerritoryInputEvent.LocalityDistrict ->
                        setStateValue(
                            TerritoryFields.TERRITORY_LOCALITY_DISTRICT, localityDistrict,
                            event.input, true
                        )

                    is TerritoryInputEvent.Microdistrict ->
                        setStateValue(
                            TerritoryFields.TERRITORY_MICRODISTRICT, microdistrict, event.input,
                            true
                        )

                    is TerritoryInputEvent.TerritoryNum -> setStateValue(
                        TerritoryFields.TERRITORY_NUM, territoryNum, event.input.toString(),
                        TerritoryInputValidator.TerritoryNum.isValid(event.input.toString())
                    )

                    is TerritoryInputEvent.IsBusiness -> setStateValue(
                        TerritoryFields.TERRITORY_IS_BUSINESS, isBusiness,
                        event.input.toString(), true
                    )

                    is TerritoryInputEvent.IsGroupMinistry -> setStateValue(
                        TerritoryFields.TERRITORY_IS_GROUP_MINISTRY, isGroupMinistry,
                        event.input.toString(), true
                    )

                    is TerritoryInputEvent.IsActive -> setStateValue(
                        TerritoryFields.TERRITORY_IS_ACTIVE, isActive, event.input.toString(),
                        true
                    )

                    is TerritoryInputEvent.TerritoryDesc -> setStateValue(
                        TerritoryFields.TERRITORY_DESC, territoryDesc, event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryInputEvent.Category ->
                        setStateValue(
                            TerritoryFields.TERRITORY_CATEGORY, category,
                            TerritoryInputValidator.Category.errorIdOrNull(event.input.headline)
                        )

                    is TerritoryInputEvent.Locality ->
                        setStateValue(
                            TerritoryFields.TERRITORY_LOCALITY, locality,
                            TerritoryInputValidator.Locality.errorIdOrNull(event.input.headline)
                        )

                    is TerritoryInputEvent.LocalityDistrict ->
                        setStateValue(
                            TerritoryFields.TERRITORY_LOCALITY_DISTRICT, localityDistrict, null
                        )

                    is TerritoryInputEvent.Microdistrict ->
                        setStateValue(TerritoryFields.TERRITORY_MICRODISTRICT, microdistrict, null)

                    is TerritoryInputEvent.TerritoryNum ->
                        setStateValue(
                            TerritoryFields.TERRITORY_NUM, territoryNum,
                            TerritoryInputValidator.TerritoryNum.errorIdOrNull(event.input.toString())
                        )

                    is TerritoryInputEvent.IsBusiness ->
                        setStateValue(TerritoryFields.TERRITORY_IS_BUSINESS, isBusiness, null)

                    is TerritoryInputEvent.IsGroupMinistry ->
                        setStateValue(
                            TerritoryFields.TERRITORY_IS_GROUP_MINISTRY, isGroupMinistry,
                            null
                        )

                    is TerritoryInputEvent.IsActive ->
                        setStateValue(TerritoryFields.TERRITORY_IS_ACTIVE, isActive, null)

                    is TerritoryInputEvent.TerritoryDesc ->
                        setStateValue(TerritoryFields.TERRITORY_DESC, territoryDesc, null)
                }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoryInputValidator.Category.errorIdOrNull(category.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoryFields.TERRITORY_CATEGORY.name, errorId = it)
            )
        }
        TerritoryInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoryFields.TERRITORY_LOCALITY.name, errorId = it)
            )
        }
        TerritoryInputValidator.TerritoryNum.errorIdOrNull(territoryNum.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = TerritoryFields.TERRITORY_NUM.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritoryFields.valueOf(error.fieldName)) {
                TerritoryFields.TERRITORY_CATEGORY -> category.value.copy(errorId = error.errorId)
                TerritoryFields.TERRITORY_LOCALITY -> locality.value.copy(errorId = error.errorId)
                TerritoryFields.TERRITORY_NUM -> territoryNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val congregation =
                    MutableStateFlow(InputListItemWrapper<CongregationsListItem>())
                override val category =
                    MutableStateFlow(InputListItemWrapper<TerritoryCategoriesListItem>())
                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val localityDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val microdistrict = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val territoryNum = MutableStateFlow(InputWrapper())
                override val isBusiness = MutableStateFlow(InputWrapper())
                override val isGroupMinistry = MutableStateFlow(InputWrapper())
                override val isActive = MutableStateFlow(InputWrapper())
                override val territoryDesc = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: TerritoryUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onInsert(block: () -> Unit) {}
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = TerritoryUi(
            congregation = CongregationViewModelImpl.previewUiModel(ctx),
            territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
            locality = LocalityViewModelImpl.previewUiModel(ctx),
            localityDistrict = LocalityDistrictViewModelImpl.previewUiModel(ctx),
            microdistrict = MicrodistrictViewModelImpl.previewUiModel(ctx),
            territoryNum = 1,
            isBusiness = true,
            isGroupMinistry = true,
            isProcessed = false,
            isActive = true,
            territoryDesc = "Territory Desc"
        ).also { it.id = UUID.randomUUID() }
    }
}