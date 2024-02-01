package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import com.oborodulin.home.common.extensions.toUUIDOrNull
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.congregation.CongregationUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.SaveCongregationUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.CongregationConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.SaveCongregationConverter
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
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
import kotlinx.coroutines.flow.catch
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

private const val TAG = "Congregating.CongregationViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class CongregationViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: CongregationUseCases,
    private val getConverter: CongregationConverter,
    private val saveConverter: SaveCongregationConverter,
    private val mapper: CongregationUiToCongregationMapper
) : CongregationViewModel,
    DialogViewModel<CongregationUi, UiState<CongregationUi>, CongregationUiAction, UiSingleEvent, CongregationFields, InputWrapper>(
        state, CongregationFields.CONGREGATION_ID.name, CongregationFields.CONGREGATION_LOCALITY
    ) {
    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(CongregationFields.CONGREGATION_LOCALITY.name, InputListItemWrapper())
    }
    override val congregationNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CongregationFields.CONGREGATION_NUM.name, InputWrapper())
    }
    override val congregationName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CongregationFields.CONGREGATION_NAME.name, InputWrapper())
    }
    override val territoryMark: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CongregationFields.TERRITORY_MARK.name, InputWrapper())
    }
    override val isFavorite: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CongregationFields.IS_FAVORITE.name, InputWrapper())
    }

    override val areInputsValid =
        combine(locality, congregationNum, congregationName, territoryMark)
        { locality, congregationNum, congregationName, territoryMark ->
            locality.errorId == null && congregationNum.errorId == null && congregationName.errorId == null && territoryMark.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: CongregationUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(CongregationUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is CongregationUiAction.Load -> when (action.congregationId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.congregation_new_subheader)
                    submitState(UiState.Success(CongregationUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_congregation.R.string.congregation_subheader)
                    loadCongregation(action.congregationId)
                }
            }

            is CongregationUiAction.Save -> saveCongregation()
        }
        return job
    }

    private fun loadCongregation(congregationId: UUID): Job {
        Timber.tag(TAG)
            .d("loadCongregation(UUID) called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getCongregationUseCase.execute(GetCongregationUseCase.Request(congregationId))
                .map {
                    getConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveCongregation(): Job {
        val localityUi = LocalityUi(localityName = locality.value.item?.headline.orEmpty())
        localityUi.id = locality.value.item?.itemId
        val congregationUi = CongregationUi(
            congregationNum = congregationNum.value.value,
            congregationName = congregationName.value.value,
            territoryMark = territoryMark.value.value,
            isFavorite = isFavorite.value.value.toBoolean(),
            locality = localityUi
        )
        congregationUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d(
            "saveCongregation() called: UI model %s; localityUi.id = %s",
            congregationUi,
            localityUi.id
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveCongregationUseCase.execute(
                SaveCongregationUseCase.Request(mapper.map(congregationUi))
            ).catch {
                Timber.tag(TAG).d("saveCongregation() error: %s", it.message)
            }.map {
                saveConverter.convert(it)
            }.collect {
                Timber.tag(TAG).d("saveCongregation() result: %s", it::class.java)
                submitState(it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<CongregationFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: CongregationUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(CongregationUi) called: CongregationUi = %s",
                uiModel
            )
        uiModel.id?.let {
            initStateValue(CongregationFields.CONGREGATION_ID, id, it.toString())
        }
        initStateValue(
            CongregationFields.CONGREGATION_LOCALITY, locality,
            ListItemModel(uiModel.locality.id, uiModel.locality.localityName)
        )
        initStateValue(
            CongregationFields.CONGREGATION_NUM, congregationNum, uiModel.congregationNum
        )
        initStateValue(
            CongregationFields.CONGREGATION_NAME, congregationName, uiModel.congregationName
        )
        initStateValue(
            CongregationFields.TERRITORY_MARK, territoryMark, uiModel.territoryMark
        )
        initStateValue(
            CongregationFields.IS_FAVORITE, isFavorite, uiModel.isFavorite.toString()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is CongregationInputEvent.Locality -> setStateValue(
                        CongregationFields.CONGREGATION_LOCALITY, locality, event.input,
                        CongregationInputValidator.Locality.isValid(event.input.headline)
                    )

                    is CongregationInputEvent.CongregationNum -> setStateValue(
                        CongregationFields.CONGREGATION_NUM, congregationNum, event.input,
                        CongregationInputValidator.CongregationNum.isValid(event.input)
                    )

                    is CongregationInputEvent.CongregationName -> setStateValue(
                        CongregationFields.CONGREGATION_NAME, congregationName, event.input,
                        CongregationInputValidator.CongregationName.isValid(event.input)
                    )

                    is CongregationInputEvent.TerritoryMark -> setStateValue(
                        CongregationFields.TERRITORY_MARK, territoryMark, event.input,
                        CongregationInputValidator.TerritoryMark.isValid(event.input)
                    )

                    is CongregationInputEvent.IsFavorite -> setStateValue(
                        CongregationFields.IS_FAVORITE, isFavorite, event.input.toString(), true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is CongregationInputEvent.Locality ->
                        setStateValue(
                            CongregationFields.CONGREGATION_LOCALITY, locality,
                            CongregationInputValidator.Locality.errorIdOrNull(event.input.headline)
                        )

                    is CongregationInputEvent.CongregationNum ->
                        setStateValue(
                            CongregationFields.CONGREGATION_NUM, congregationNum,
                            CongregationInputValidator.CongregationNum.errorIdOrNull(event.input)
                        )

                    is CongregationInputEvent.CongregationName ->
                        setStateValue(
                            CongregationFields.CONGREGATION_NAME, congregationName,
                            CongregationInputValidator.CongregationName.errorIdOrNull(event.input)
                        )

                    is CongregationInputEvent.TerritoryMark ->
                        setStateValue(
                            CongregationFields.TERRITORY_MARK, territoryMark,
                            CongregationInputValidator.TerritoryMark.errorIdOrNull(event.input)
                        )

                    is CongregationInputEvent.IsFavorite ->
                        setStateValue(CongregationFields.IS_FAVORITE, isFavorite, null)
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("#IF getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        CongregationInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = CongregationFields.CONGREGATION_LOCALITY.name, errorId = it
                )
            )
        }
        CongregationInputValidator.CongregationNum.errorIdOrNull(congregationNum.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = CongregationFields.CONGREGATION_NUM.name, errorId = it)
            )
        }
        CongregationInputValidator.CongregationName.errorIdOrNull(congregationName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(fieldName = CongregationFields.CONGREGATION_NAME.name, errorId = it)
                )
            }
        CongregationInputValidator.TerritoryMark.errorIdOrNull(territoryMark.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = CongregationFields.TERRITORY_MARK.name, errorId = it)
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("#IF displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (CongregationFields.valueOf(error.fieldName)) {
                CongregationFields.CONGREGATION_LOCALITY -> locality.value.copy(errorId = error.errorId)
                CongregationFields.CONGREGATION_NUM -> congregationNum.value.copy(errorId = error.errorId)
                CongregationFields.CONGREGATION_NAME -> congregationName.value.copy(errorId = error.errorId)
                CongregationFields.TERRITORY_MARK -> territoryMark.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : CongregationViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(false)

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
                override val congregationNum = MutableStateFlow(InputWrapper())
                override val congregationName = MutableStateFlow(InputWrapper())
                override val territoryMark = MutableStateFlow(InputWrapper())
                override val isFavorite = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: CongregationUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: CongregationFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): CongregationUi {
            val congregationUi = CongregationUi(
                congregationNum = ctx.resources.getString(R.string.def_congregation1_num),
                congregationName = ctx.resources.getString(R.string.def_congregation1_name),
                territoryMark = ctx.resources.getString(R.string.def_congregation1_card_mark),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                isFavorite = true
            )
            congregationUi.id = UUID.randomUUID()
            return congregationUi
        }
    }
}