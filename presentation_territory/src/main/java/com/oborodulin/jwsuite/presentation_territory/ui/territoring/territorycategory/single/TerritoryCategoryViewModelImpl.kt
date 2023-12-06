package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_territory.R
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.SaveTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.TerritoryCategoryUseCases
import com.oborodulin.jwsuite.domain.util.TerritoryCategoryType
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryCategoryConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoriesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryUiToTerritoryCategoryMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.TerritoryCategoryViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoryCategoryViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoryCategoryUseCases,
    private val converter: TerritoryCategoryConverter,
    private val territoryCategoryUiMapper: TerritoryCategoryUiToTerritoryCategoryMapper,
    private val territoryCategoryMapper: TerritoryCategoryToTerritoryCategoriesListItemMapper
) : TerritoryCategoryViewModel,
    DialogViewModel<TerritoryCategoryUi, UiState<TerritoryCategoryUi>, TerritoryCategoryUiAction, UiSingleEvent, TerritoryCategoryFields, InputWrapper>(
        state, TerritoryCategoryFields.TERRITORY_CATEGORY_ID.name,
        TerritoryCategoryFields.TERRITORY_CATEGORY_CODE
    ) {
    override val territoryCategoryCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryCategoryFields.TERRITORY_CATEGORY_CODE.name, InputWrapper())
    }
    override val territoryCategoryMark: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryCategoryFields.TERRITORY_CATEGORY_MARK.name, InputWrapper())
    }
    override val territoryCategoryName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoryCategoryFields.TERRITORY_CATEGORY_NAME.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            territoryCategoryCode,
            territoryCategoryMark,
            territoryCategoryName
        ) { territoryCategoryCode, territoryCategoryMark, territoryCategoryName ->
            territoryCategoryCode.errorId == null && territoryCategoryMark.errorId == null && territoryCategoryName.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<TerritoryCategoryUi> = UiState.Loading

    override suspend fun handleAction(action: TerritoryCategoryUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(TerritoryCategoryUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryCategoryUiAction.Load -> when (action.territoryCategoryId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_category_new_subheader)
                    submitState(UiState.Success(TerritoryCategoryUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_territory.R.string.territory_category_subheader)
                    loadTerritoryCategory(action.territoryCategoryId)
                }
            }

            is TerritoryCategoryUiAction.Save -> saveTerritoryCategory()
        }
        return job
    }

    private fun loadTerritoryCategory(territoryCategoryId: UUID): Job {
        Timber.tag(TAG).d("loadTerritoryCategory(UUID) called: %s", territoryCategoryId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryCategoryUseCase.execute(
                GetTerritoryCategoryUseCase.Request(territoryCategoryId)
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

    private fun saveTerritoryCategory(): Job {
        val territoryCategoryUi = TerritoryCategoryUi(
            territoryCategoryCode = TerritoryCategoryType.valueOf(territoryCategoryCode.value.value),
            territoryCategoryMark = territoryCategoryMark.value.value,
            territoryCategoryName = territoryCategoryName.value.value
        )
        territoryCategoryUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d("saveTerritoryCategory() called: UI model %s", territoryCategoryUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveTerritoryCategoryUseCase.execute(
                SaveTerritoryCategoryUseCase.Request(
                    territoryCategoryUiMapper.map(territoryCategoryUi)
                )
            ).collect {
                Timber.tag(TAG).d("saveTerritoryCategory() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(territoryCategoryMapper.map(it.data.territoryCategory))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<TerritoryCategoryFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoryCategoryUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoryCategoryModel) called: territoryCategoryUi = %s",
                uiModel
            )
        uiModel.id?.let {
            initStateValue(TerritoryCategoryFields.TERRITORY_CATEGORY_ID, id, it.toString())
        }
        initStateValue(
            TerritoryCategoryFields.TERRITORY_CATEGORY_CODE, territoryCategoryCode,
            uiModel.territoryCategoryCode.name
        )
        initStateValue(
            TerritoryCategoryFields.TERRITORY_CATEGORY_MARK, territoryCategoryMark,
            uiModel.territoryCategoryMark
        )
        initStateValue(
            TerritoryCategoryFields.TERRITORY_CATEGORY_NAME, territoryCategoryName,
            uiModel.territoryCategoryName
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoryCategoryInputEvent.TerritoryCategoryCode ->
                        when (TerritoryCategoryInputValidator.TerritoryCategoryCode.errorIdOrNull(
                            event.input
                        )) {
                            null -> setStateValue(
                                TerritoryCategoryFields.TERRITORY_CATEGORY_CODE,
                                territoryCategoryCode, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryCategoryFields.TERRITORY_CATEGORY_CODE,
                                territoryCategoryCode, event.input
                            )
                        }

                    is TerritoryCategoryInputEvent.TerritoryCategoryMark ->
                        when (TerritoryCategoryInputValidator.TerritoryCategoryMark.errorIdOrNull(
                            event.input
                        )) {
                            null -> setStateValue(
                                TerritoryCategoryFields.TERRITORY_CATEGORY_MARK,
                                territoryCategoryMark, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryCategoryFields.TERRITORY_CATEGORY_MARK,
                                territoryCategoryMark, event.input
                            )
                        }

                    is TerritoryCategoryInputEvent.TerritoryCategoryName ->
                        when (TerritoryCategoryInputValidator.TerritoryCategoryName.errorIdOrNull(
                            event.input
                        )) {
                            null -> setStateValue(
                                TerritoryCategoryFields.TERRITORY_CATEGORY_NAME,
                                territoryCategoryName, event.input, true
                            )

                            else -> setStateValue(
                                TerritoryCategoryFields.TERRITORY_CATEGORY_NAME,
                                territoryCategoryName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoryCategoryInputEvent.TerritoryCategoryCode ->
                        setStateValue(
                            TerritoryCategoryFields.TERRITORY_CATEGORY_CODE, territoryCategoryCode,
                            TerritoryCategoryInputValidator.TerritoryCategoryCode.errorIdOrNull(
                                event.input
                            )
                        )

                    is TerritoryCategoryInputEvent.TerritoryCategoryMark ->
                        setStateValue(
                            TerritoryCategoryFields.TERRITORY_CATEGORY_MARK, territoryCategoryMark,
                            TerritoryCategoryInputValidator.TerritoryCategoryMark.errorIdOrNull(
                                event.input
                            )
                        )

                    is TerritoryCategoryInputEvent.TerritoryCategoryName ->
                        setStateValue(
                            TerritoryCategoryFields.TERRITORY_CATEGORY_NAME, territoryCategoryName,
                            TerritoryCategoryInputValidator.TerritoryCategoryName.errorIdOrNull(
                                event.input
                            )
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        TerritoryCategoryInputValidator.TerritoryCategoryCode.errorIdOrNull(territoryCategoryCode.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritoryCategoryFields.TERRITORY_CATEGORY_CODE.name,
                        errorId = it
                    )
                )
            }
        TerritoryCategoryInputValidator.TerritoryCategoryMark.errorIdOrNull(territoryCategoryMark.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritoryCategoryFields.TERRITORY_CATEGORY_MARK.name,
                        errorId = it
                    )
                )
            }
        TerritoryCategoryInputValidator.TerritoryCategoryName.errorIdOrNull(territoryCategoryName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = TerritoryCategoryFields.TERRITORY_CATEGORY_NAME.name,
                        errorId = it
                    )
                )
            }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (TerritoryCategoryFields.valueOf(error.fieldName)) {
                TerritoryCategoryFields.TERRITORY_CATEGORY_CODE -> territoryCategoryCode.value.copy(
                    errorId = error.errorId
                )

                TerritoryCategoryFields.TERRITORY_CATEGORY_MARK -> territoryCategoryMark.value.copy(
                    errorId = error.errorId
                )

                TerritoryCategoryFields.TERRITORY_CATEGORY_NAME -> territoryCategoryName.value.copy(
                    errorId = error.errorId
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryCategoryViewModel {
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
                override val territoryCategoryCode = MutableStateFlow(InputWrapper())
                override val territoryCategoryMark = MutableStateFlow(InputWrapper())
                override val territoryCategoryName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                //override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: TerritoryCategoryUiAction): Job? = null
                override fun handleActionJob(action: () -> Unit, afterAction: (CoroutineScope) -> Unit) {}
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoryCategoryFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): TerritoryCategoryUi {
            val territoryCategoryUi = TerritoryCategoryUi(
                territoryCategoryCode = TerritoryCategoryType.HOUSES,
                territoryCategoryMark = ctx.resources.getString(R.string.def_house_territory_category_mark),
                territoryCategoryName = ctx.resources.getString(R.string.def_house_territory_category_name)
            )
            territoryCategoryUi.id = UUID.randomUUID()
            return territoryCategoryUi
        }
    }
}