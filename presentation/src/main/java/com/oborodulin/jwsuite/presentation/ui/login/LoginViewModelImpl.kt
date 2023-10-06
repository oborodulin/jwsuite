package com.oborodulin.jwsuite.presentation.ui.login

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

private const val TAG = "Presentation.RegionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionUseCases,
    private val converter: RegionConverter,
    private val regionUiMapper: RegionUiToRegionMapper,
    private val regionMapper: RegionToRegionsListItemMapper
) : LoginViewModel,
    DialogSingleViewModel<RegionUi, UiState<RegionUi>, LoginUiAction, UiSingleEvent, LoginFields, InputWrapper>(
        state, LoginFields.REGION_ID.name, LoginFields.LOGIN_USERNAME
    ) {
    override val password: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LoginFields.LOGIN_USERNAME.name,
            InputWrapper()
        )
    }
    override val regionName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            LoginFields.LOGIN_PASSWORD.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            password,
            regionName
        ) { regionCode, regionName ->
            regionCode.errorId == null && regionName.errorId == null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<RegionUi> = UiState.Loading

    override suspend fun handleAction(action: LoginUiAction): Job {
        Timber.tag(TAG).d("handleAction(RegionUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is LoginUiAction.Load -> when (action.regionId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.region_new_subheader)
                    submitState(UiState.Success(RegionUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.region_subheader)
                    loadRegion(action.regionId)
                }
            }

            is LoginUiAction.Save -> {
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
            regionCode = password.value.value,
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

    override fun stateInputFields() = enumValues<LoginFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: RegionUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(RegionModel) called: regionUi = %s", uiModel)
        uiModel.id?.let { initStateValue(LoginFields.REGION_ID, id, it.toString()) }
        initStateValue(LoginFields.LOGIN_USERNAME, password, uiModel.regionCode)
        initStateValue(LoginFields.LOGIN_PASSWORD, regionName, uiModel.regionName)
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is LoginInputEvent.Username ->
                        when (LoginInputValidator.LoginCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LoginFields.LOGIN_USERNAME, password, event.input, true
                            )

                            else -> setStateValue(
                                LoginFields.LOGIN_USERNAME, password, event.input
                            )
                        }

                    is LoginInputEvent.Password ->
                        when (LoginInputValidator.LoginName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                LoginFields.LOGIN_PASSWORD, regionName, event.input, true
                            )

                            else -> setStateValue(
                                LoginFields.LOGIN_PASSWORD, regionName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is LoginInputEvent.Username ->
                        setStateValue(
                            LoginFields.LOGIN_USERNAME, password,
                            LoginInputValidator.LoginCode.errorIdOrNull(event.input)
                        )

                    is LoginInputEvent.Password ->
                        setStateValue(
                            LoginFields.LOGIN_PASSWORD, regionName,
                            LoginInputValidator.LoginName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        LoginInputValidator.LoginCode.errorIdOrNull(password.value.value)?.let {
            inputErrors.add(InputError(fieldName = LoginFields.LOGIN_USERNAME.name, errorId = it))
        }
        LoginInputValidator.LoginName.errorIdOrNull(regionName.value.value)?.let {
            inputErrors.add(InputError(fieldName = LoginFields.LOGIN_PASSWORD.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (LoginFields.valueOf(error.fieldName)) {
                LoginFields.LOGIN_USERNAME -> password.value.copy(errorId = error.errorId)
                LoginFields.LOGIN_PASSWORD -> regionName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : LoginViewModel {
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

                override val password = MutableStateFlow(InputWrapper())
                override val regionName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: LoginUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: LoginFields, isFocused: Boolean
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