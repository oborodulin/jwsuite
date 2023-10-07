package com.oborodulin.jwsuite.presentation.ui.session

import android.annotation.SuppressLint
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
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionUiToRegionMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Presentation.RegionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class SessionViewModelImpl @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext val ctx: Context,
    private val state: SavedStateHandle,
    private val useCases: SessionUseCases,
    private val converter: RegionConverter,
    private val regionUiMapper: RegionUiToRegionMapper,
    private val regionMapper: RegionToRegionsListItemMapper
) : SessionViewModel,
    DialogSingleViewModel<Any, UiState<Any>, SessionUiAction, UiSingleEvent, SessionFields, InputWrapper>(
        state, initFocusedTextField = SessionFields.SESSION_USERNAME
    ) {
    override val username: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(SessionFields.SESSION_USERNAME.name, InputWrapper())
    }
    override val password: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(SessionFields.SESSION_PASSWORD.name, InputWrapper())
    }
    override val confirmPassword: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(SessionFields.SESSION_CONFIRM_PASSWORD.name, InputWrapper())
    }

    override val areInputsValid =
        combine(username, password, confirmPassword) { username, password, confirmPassword ->
            username.errorId == null && password.errorId == null && confirmPassword.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<Any> = UiState.Loading

    override suspend fun handleAction(action: SessionUiAction): Job {
        Timber.tag(TAG).d("handleAction(SignupUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is SessionUiAction.Signup -> signup(action.username, action.password)
        }
        return job
    }

    private fun signup(username: String, password: String): Job {
        Timber.tag(TAG).d("signup(...) called")
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

    override fun stateInputFields() = enumValues<SessionFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: RegionUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(RegionModel) called: regionUi = %s", uiModel)
        uiModel.id?.let { initStateValue(SessionFields.REGION_ID, id, it.toString()) }
        initStateValue(SessionFields.SESSION_USERNAME, username, uiModel.regionCode)
        initStateValue(SessionFields.SESSION_PASSWORD, password, uiModel.regionName)
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is SessionInputEvent.Username ->
                        when (SessionInputValidator.Username.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                SessionFields.SESSION_USERNAME, username, event.input, true
                            )

                            else -> setStateValue(
                                SessionFields.SESSION_USERNAME, username, event.input
                            )
                        }

                    is SessionInputEvent.Password ->
                        when (SessionInputValidator.Password.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                SessionFields.SESSION_PASSWORD, password, event.input, true
                            )

                            else -> setStateValue(
                                SessionFields.SESSION_PASSWORD, password, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is SessionInputEvent.Username ->
                        setStateValue(
                            SessionFields.SESSION_USERNAME, username,
                            SessionInputValidator.Username.errorIdOrNull(event.input)
                        )

                    is SessionInputEvent.Password ->
                        setStateValue(
                            SessionFields.SESSION_PASSWORD, password,
                            SessionInputValidator.Password.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        SessionInputValidator.Username.errorIdOrNull(username.value.value)?.let {
            inputErrors.add(InputError(fieldName = SessionFields.SESSION_USERNAME.name, errorId = it))
        }
        SessionInputValidator.Password.errorIdOrNull(password.value.value)?.let {
            inputErrors.add(InputError(fieldName = SessionFields.SESSION_PASSWORD.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    // https://stackoverflow.com/questions/3656371/is-it-possible-to-have-placeholders-in-strings-xml-for-runtime-values
    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        val res = ctx.resources
        for (error in inputErrors) {
            state[error.fieldName] = when (SessionFields.valueOf(error.fieldName)) {
                SessionFields.SESSION_USERNAME -> username.value.copy(errorId = error.errorId)
                SessionFields.SESSION_PASSWORD -> password.value.copy(
                    errorId = error.errorId,
                    errorMsg = res.getString(error.errorId!!, PASS_MIN_LENGTH)
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : SessionViewModel {
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

                override val username = MutableStateFlow(InputWrapper())
                override val password = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: SessionUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: SessionFields, isFocused: Boolean
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