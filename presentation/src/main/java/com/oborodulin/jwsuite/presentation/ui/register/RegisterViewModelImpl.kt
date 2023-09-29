package com.oborodulin.jwsuite.presentation.ui.register

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
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionUiToRegionMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Geo.RegionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionUseCases,
    private val converter: RegionConverter,
    private val regionUiMapper: RegionUiToRegionMapper,
    private val regionMapper: RegionToRegionsListItemMapper
) : RegisterViewModel,
    DialogSingleViewModel<RegionUi, UiState<RegionUi>, RegisterUiAction, UiSingleEvent, RegisterFields, InputWrapper>(
        state, RegisterFields.REGION_ID.name, RegisterFields.REGISTER_USERNAME
    ) {
    override val regionCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegisterFields.REGISTER_USERNAME.name,
            InputWrapper()
        )
    }
    override val regionName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegisterFields.REGISTER_PASSWORD.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            regionCode,
            regionName
        ) { regionCode, regionName ->
            regionCode.errorId == null && regionName.errorId == null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<RegionUi> = UiState.Loading

    override suspend fun handleAction(action: RegisterUiAction): Job {
        Timber.tag(TAG).d("handleAction(RegionUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegisterUiAction.Load -> when (action.regionId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.region_new_subheader)
                    submitState(UiState.Success(RegionUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.region_subheader)
                    loadRegion(action.regionId)
                }
            }

            is RegisterUiAction.Register -> {
                saveRegion()
            }
        }
        return job
    }

    private fun loadRegion(regionId: UUID): Job {
        Timber.tag(TAG).d("loadRegion(UUID) called: %s", regionId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRegionUseCase.execute(GetRegionUseCase.Request(regionId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveRegion(): Job {
        val regionUi = RegionUi(
            regionCode = regionCode.value.value,
            regionName = regionName.value.value
        )
        regionUi.id = if (id.value.value.isNotEmpty()) {
            UUID.fromString(id.value.value)
        } else null
        Timber.tag(TAG).d("saveRegion() called: UI model %s", regionUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveRegionUseCase.execute(SaveRegionUseCase.Request(regionUiMapper.map(regionUi)))
                .collect {
                    Timber.tag(TAG).d("saveRegion() collect: %s", it)
                    if (it is Result.Success) {
                        setSavedListItem(regionMapper.map(it.data.region))
                    }
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<RegisterFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: RegionUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(RegionModel) called: regionUi = %s", uiModel)
        uiModel.id?.let { initStateValue(RegisterFields.REGION_ID, id, it.toString()) }
        initStateValue(RegisterFields.REGISTER_USERNAME, regionCode, uiModel.regionCode)
        initStateValue(RegisterFields.REGISTER_PASSWORD, regionName, uiModel.regionName)
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegisterInputEvent.Username ->
                        when (RegisterInputValidator.Username.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegisterFields.REGISTER_USERNAME, regionCode, event.input, true
                            )

                            else -> setStateValue(
                                RegisterFields.REGISTER_USERNAME, regionCode, event.input
                            )
                        }

                    is RegisterInputEvent.Password ->
                        when (RegisterInputValidator.Password.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegisterFields.REGISTER_PASSWORD, regionName, event.input, true
                            )

                            else -> setStateValue(
                                RegisterFields.REGISTER_PASSWORD, regionName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegisterInputEvent.Username ->
                        setStateValue(
                            RegisterFields.REGISTER_USERNAME, regionCode,
                            RegisterInputValidator.Username.errorIdOrNull(event.input)
                        )

                    is RegisterInputEvent.Password ->
                        setStateValue(
                            RegisterFields.REGISTER_PASSWORD, regionName,
                            RegisterInputValidator.Password.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegisterInputValidator.Username.errorIdOrNull(regionCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegisterFields.REGISTER_USERNAME.name, errorId = it))
        }
        RegisterInputValidator.Password.errorIdOrNull(regionName.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegisterFields.REGISTER_PASSWORD.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (RegisterFields.valueOf(error.fieldName)) {
                RegisterFields.REGISTER_USERNAME -> regionCode.value.copy(errorId = error.errorId)
                RegisterFields.REGISTER_PASSWORD -> regionName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : RegisterViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val regionCode = MutableStateFlow(InputWrapper())
                override val regionName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: RegisterUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RegisterFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): RegionUi {
            val regionUi = RegionUi(
                regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code),
                regionName = ctx.resources.getString(R.string.def_reg_donetsk_name)
            )
            regionUi.id = UUID.randomUUID()
            return regionUi
        }
    }
}