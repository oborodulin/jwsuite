package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

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
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.single.MicrodistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoryUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryUi
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.toTerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single.TerritoryCategoryViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Congregating.TerritoryViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryConverter,
    private val territoryUiMapper: TerritoryUiToTerritoryMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper
) : TerritoryViewModel,
    DialogSingleViewModel<TerritoryUi, UiState<TerritoryUi>, TerritoryUiAction, UiSingleEvent, TerritoryFields, InputWrapper>(
        state,
        TerritoryFields.TERRITORY_NUM
    ) {
    private val territoryId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_ID.name, InputWrapper())
    }
    override val congregation: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_CONGREGATION.name, InputListItemWrapper())
    }
    override val category: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
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
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryFields.TERRITORY_IS_PRIVATE_SECTOR.name, InputWrapper())
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
        { stateFlowsArray ->
            var errorIdResult = true
            for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
            errorIdResult
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<TerritoryUi> = UiState.Loading

    override suspend fun handleAction(action: TerritoryUiAction): Job {
        Timber.tag(TAG).d("handleAction(TerritoryUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryUiAction.Load -> when (action.territoryId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.territory_new_subheader)
                    submitState(UiState.Success(TerritoryUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.territory_subheader)
                    loadTerritory(action.territoryId)
                }
            }

            is TerritoryUiAction.Save -> saveTerritory()
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

    private fun saveTerritory(): Job {
        val congregationUi = CongregationUi()
        congregationUi.id = congregation.value.item?.itemId
        val territoryCategoryUi = TerritoryCategoryUi()
        territoryCategoryUi.id = category.value.item?.itemId
        val localityUi = LocalityUi()
        localityUi.id = locality.value.item?.itemId
        val localityDistrictUi = LocalityDistrictUi()
        localityDistrictUi.id = localityDistrict.value.item?.itemId
        val microdistrictUi = MicrodistrictUi()
        microdistrictUi.id = microdistrict.value.item?.itemId
        val territoryUi = TerritoryUi(
            congregation = congregationUi,
            territoryCategory = territoryCategoryUi,
            locality = localityUi,
            localityDistrict = localityDistrictUi,
            microdistrict = microdistrictUi,
            territoryNum = territoryNum.value.value.toInt(),
            isPrivateSector = isPrivateSector.value.value.toBoolean(),
            isBusiness = isBusiness.value.value.toBoolean(),
            isGroupMinistry = isGroupMinistry.value.value.toBoolean(),
            isActive = isActive.value.value.toBoolean(),
            territoryDesc = territoryDesc.value.value
        )
        territoryUi.id = if (territoryId.value.value.isNotEmpty()) {
            UUID.fromString(territoryId.value.value)
        } else null
        Timber.tag(TAG).d("saveTerritory() called: UI model %s", territoryUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveTerritoryUseCase.execute(
                SaveTerritoryUseCase.Request(territoryUiMapper.map(territoryUi))
            ).collect {
                Timber.tag(TAG).d("saveTerritory() collect: %s", it)
                if (it is Result.Success) setSavedListItem(
                    territoryMapper.map(it.data.territory).toTerritoriesListItem()
                )
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val territoryUi = uiModel as TerritoryUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(TerritoryModel) called: territoryUi = %s", territoryUi)
        territoryUi.id?.let {
            initStateValue(TerritoryFields.TERRITORY_ID, territoryId, it.toString())
        }
        initStateValue(
            TerritoryFields.TERRITORY_CONGREGATION, congregation,
            ListItemModel(territoryUi.congregation.id, territoryUi.congregation.congregationName)
        )
        initStateValue(
            TerritoryFields.TERRITORY_CATEGORY, category,
            ListItemModel(
                territoryUi.territoryCategory.id,
                territoryUi.territoryCategory.territoryCategoryName
            )
        )
        initStateValue(
            TerritoryFields.TERRITORY_LOCALITY, locality,
            ListItemModel(territoryUi.locality.id, territoryUi.locality.localityName)
        )
        initStateValue(
            TerritoryFields.TERRITORY_LOCALITY_DISTRICT, localityDistrict,
            ListItemModel(
                territoryUi.localityDistrict?.id,
                territoryUi.localityDistrict?.districtName.orEmpty()
            )
        )
        initStateValue(
            TerritoryFields.TERRITORY_MICRODISTRICT, microdistrict,
            ListItemModel(
                territoryUi.microdistrict?.id,
                territoryUi.microdistrict?.microdistrictName.orEmpty()
            )
        )
        initStateValue(
            TerritoryFields.TERRITORY_NUM, territoryNum, territoryUi.territoryNum.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_IS_PRIVATE_SECTOR, isPrivateSector,
            territoryUi.isPrivateSector.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_IS_BUSINESS, isBusiness, territoryUi.isBusiness.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_IS_GROUP_MINISTRY, isGroupMinistry,
            territoryUi.isGroupMinistry.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_IS_ACTIVE, isActive, territoryUi.isActive.toString()
        )
        initStateValue(
            TerritoryFields.TERRITORY_DESC, territoryDesc, territoryUi.territoryDesc.orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryInputEvent.Congregation ->
                        setStateValue(
                            TerritoryFields.TERRITORY_CONGREGATION, congregation, event.input, true
                        )

                    is TerritoryInputEvent.Category ->
                        when (TerritoryInputValidator.Category.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoryFields.TERRITORY_CATEGORY, category, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryFields.TERRITORY_CATEGORY, category, event.input
                            )
                        }

                    is TerritoryInputEvent.Locality ->
                        when (TerritoryInputValidator.Locality.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                TerritoryFields.TERRITORY_LOCALITY, locality, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryFields.TERRITORY_LOCALITY, locality, event.input
                            )
                        }

                    is TerritoryInputEvent.LocalityDistrict ->
                        setStateValue(
                            TerritoryFields.TERRITORY_LOCALITY_DISTRICT, localityDistrict,
                            event.input, true
                        )

                    is TerritoryInputEvent.Microdistrict ->
                        setStateValue(
                            TerritoryFields.TERRITORY_MICRODISTRICT, microdistrict,
                            event.input, true
                        )

                    is TerritoryInputEvent.TerritoryNum ->
                        when (TerritoryInputValidator.TerritoryNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                TerritoryFields.TERRITORY_NUM, territoryNum, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryFields.TERRITORY_NUM, territoryNum, event.input
                            )
                        }

                    is TerritoryInputEvent.IsPrivateSector ->
                        setStateValue(
                            TerritoryFields.TERRITORY_IS_PRIVATE_SECTOR, isPrivateSector,
                            event.input.toString(), true
                        )

                    is TerritoryInputEvent.IsBusiness ->
                        setStateValue(
                            TerritoryFields.TERRITORY_IS_BUSINESS, isBusiness,
                            event.input.toString(), true
                        )

                    is TerritoryInputEvent.IsGroupMinistry ->
                        setStateValue(
                            TerritoryFields.TERRITORY_IS_GROUP_MINISTRY, isGroupMinistry,
                            event.input.toString(), true
                        )

                    is TerritoryInputEvent.IsActive ->
                        setStateValue(
                            TerritoryFields.TERRITORY_IS_ACTIVE, isActive, event.input.toString(),
                            true
                        )

                    is TerritoryInputEvent.TerritoryDesc ->
                        setStateValue(
                            TerritoryFields.TERRITORY_DESC, territoryDesc, event.input, true
                        )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryInputEvent.Congregation ->
                        setStateValue(TerritoryFields.TERRITORY_CONGREGATION, congregation, null)

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
                            TerritoryInputValidator.TerritoryNum.errorIdOrNull(event.input)
                        )

                    is TerritoryInputEvent.IsPrivateSector ->
                        setStateValue(
                            TerritoryFields.TERRITORY_IS_PRIVATE_SECTOR, isPrivateSector, null
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
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
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
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                TerritoryFields.TERRITORY_CATEGORY.name -> category.value.copy(errorId = error.errorId)
                TerritoryFields.TERRITORY_LOCALITY.name -> locality.value.copy(errorId = error.errorId)
                TerritoryFields.TERRITORY_NUM.name -> territoryNum.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val congregation = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val category = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val localityDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val microdistrict = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val territoryNum = MutableStateFlow(InputWrapper())
                override val isPrivateSector = MutableStateFlow(InputWrapper())
                override val isBusiness = MutableStateFlow(InputWrapper())
                override val isGroupMinistry = MutableStateFlow(InputWrapper())
                override val isActive = MutableStateFlow(InputWrapper())
                override val territoryDesc = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: TerritoryUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): TerritoryUi {
            val territoryUi = TerritoryUi(
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                localityDistrict = LocalityDistrictViewModelImpl.previewUiModel(ctx),
                microdistrict = MicrodistrictViewModelImpl.previewUiModel(ctx),
                territoryNum = 1,
                isPrivateSector = true,
                isBusiness = true,
                isGroupMinistry = true,
                isProcessed = false,
                isActive = true,
                territoryDesc = "Territory Desc"
            )
            territoryUi.id = UUID.randomUUID()
            return territoryUi
        }
    }
}