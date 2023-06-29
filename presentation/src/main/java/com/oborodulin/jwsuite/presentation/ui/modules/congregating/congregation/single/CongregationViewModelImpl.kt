package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.congregation.CongregationUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.SaveCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters.CongregationConverter
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationUiToCongregationMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Congregating.ui.CongregationViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class CongregationViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: CongregationUseCases,
    private val converter: CongregationConverter,
    private val mapper: CongregationUiToCongregationMapper
) : CongregationViewModel,
    SingleViewModel<CongregationUi, UiState<CongregationUi>, CongregationUiAction, UiSingleEvent, CongregationFields, InputWrapper>(
        state,
        CongregationFields.CONGREGATION_NUM
    ) {
    private val congregationId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.CONGREGATION_ID.name,
            InputWrapper()
        )
    }
    override val localityId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.LOCALITY_ID.name,
            InputWrapper()
        )
    }
    override val congregationNum: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.CONGREGATION_NUM.name,
            InputWrapper()
        )
    }
    override val congregationName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.CONGREGATION_NAME.name,
            InputWrapper()
        )
    }
    override val territoryMark: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.TERRITORY_MARK.name,
            InputWrapper()
        )
    }
    override val isFavorite: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            CongregationFields.IS_FAVORITE.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            localityId,
            congregationNum,
            congregationName,
            territoryMark
        ) { localityId, congregationNum, congregationName, territoryMark ->
            localityId.errorId == null && congregationNum.errorId == null && congregationName.errorId == null && territoryMark.errorId == null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<CongregationUi> = UiState.Loading

    override suspend fun handleAction(action: CongregationUiAction): Job {
        Timber.tag(TAG).d("handleAction(CongregationUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is CongregationUiAction.Create -> {
                submitState(UiState.Success(CongregationUi()))
            }

            is CongregationUiAction.Load -> {
                loadCongregation(action.congregationId)
            }

            is CongregationUiAction.Save -> {
                saveCongregation()
            }
        }
        return job
    }

    private fun loadCongregation(congregationId: UUID): Job {
        Timber.tag(TAG)
            .d("loadCongregation(UUID) called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getCongregationUseCase.execute(GetCongregationUseCase.Request(congregationId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveCongregation(): Job {
        val locality = LocalityUi()
        locality.id = UUID.fromString(localityId.value.value)
        val congregationUi = CongregationUi(
            congregationNum = congregationNum.value.value,
            congregationName = congregationName.value.value,
            territoryMark = territoryMark.value.value,
            locality = locality
        )
        congregationUi.id = if (congregationId.value.value.isNotEmpty()) {
            UUID.fromString(congregationId.value.value)
        } else null
        Timber.tag(TAG).d("saveCongregation() called: UI model %s", congregationUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveCongregationUseCase.execute(
                SaveCongregationUseCase.Request(mapper.map(congregationUi))
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<CongregationFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val congregationUi = uiModel as CongregationUi
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(CongregationUi) called: CongregationUi = %s",
                congregationUi
            )
        congregationUi.id?.let {
            initStateValue(CongregationFields.CONGREGATION_ID, congregationId, it.toString())
        }
        initStateValue(
            CongregationFields.CONGREGATION_NUM,
            congregationNum,
            congregationUi.congregationNum
        )
        initStateValue(
            CongregationFields.CONGREGATION_NAME,
            congregationName,
            congregationUi.congregationName
        )
        initStateValue(
            CongregationFields.TERRITORY_MARK,
            territoryMark, congregationUi.territoryMark
        )
        initStateValue(
            CongregationFields.LOCALITY_ID,
            localityId,
            congregationUi.locality.id.toString() ?: ""
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is CongregationInputEvent.LocalityId ->
                        when (CongregationInputValidator.LocalityId.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.LOCALITY_ID, localityId, event.input, true
                            )

                            else -> setStateValue(
                                CongregationFields.LOCALITY_ID, localityId, event.input
                            )
                        }

                    is CongregationInputEvent.CongregationNum ->
                        when (CongregationInputValidator.CongregationNum.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.CONGREGATION_NUM,
                                congregationNum, event.input, true
                            )

                            else -> setStateValue(
                                CongregationFields.CONGREGATION_NUM, congregationNum, event.input
                            )
                        }

                    is CongregationInputEvent.CongregationName ->
                        when (CongregationInputValidator.CongregationName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.CONGREGATION_NAME, congregationName, event.input,
                                true
                            )

                            else -> setStateValue(
                                CongregationFields.CONGREGATION_NAME, congregationName, event.input
                            )
                        }

                    is CongregationInputEvent.TerritoryMark ->
                        when (CongregationInputValidator.TerritoryMark.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                CongregationFields.TERRITORY_MARK,
                                territoryMark, event.input, true
                            )

                            else -> setStateValue(
                                CongregationFields.TERRITORY_MARK, territoryMark, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is CongregationInputEvent.LocalityId ->
                        setStateValue(
                            CongregationFields.LOCALITY_ID, localityId,
                            CongregationInputValidator.LocalityId.errorIdOrNull(event.input)
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
                            CongregationFields.TERRITORY_MARK,
                            territoryMark,
                            CongregationInputValidator.TerritoryMark.errorIdOrNull(event.input)
                        )
                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        CongregationInputValidator.LocalityId.errorIdOrNull(localityId.value.value)?.let {
            inputErrors.add(
                InputError(
                    fieldName = CongregationFields.LOCALITY_ID.name,
                    errorId = it
                )
            )
        }
        CongregationInputValidator.CongregationNum.errorIdOrNull(congregationNum.value.value)?.let {
            inputErrors.add(
                InputError(
                    fieldName = CongregationFields.CONGREGATION_NUM.name,
                    errorId = it
                )
            )
        }
        CongregationInputValidator.CongregationName.errorIdOrNull(congregationName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = CongregationFields.CONGREGATION_NAME.name,
                        errorId = it
                    )
                )
            }
        CongregationInputValidator.TerritoryMark.errorIdOrNull(territoryMark.value.value)?.let {
            inputErrors.add(
                InputError(
                    fieldName = CongregationFields.TERRITORY_MARK.name,
                    errorId = it
                )
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                CongregationFields.LOCALITY_ID.name -> localityId.value.copy(errorId = error.errorId)
                CongregationFields.CONGREGATION_NUM.name -> congregationNum.value.copy(errorId = error.errorId)
                CongregationFields.CONGREGATION_NAME.name -> congregationName.value.copy(errorId = error.errorId)
                CongregationFields.TERRITORY_MARK.name -> territoryMark.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : CongregationViewModel {
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val localityId = MutableStateFlow(InputWrapper())
                override val congregationNum = MutableStateFlow(InputWrapper())
                override val congregationName = MutableStateFlow(InputWrapper())
                override val territoryMark = MutableStateFlow(InputWrapper())
                override val isFavorite = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: CongregationFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
            }
    }
}