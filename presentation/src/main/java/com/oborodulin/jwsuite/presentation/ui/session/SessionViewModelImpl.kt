package com.oborodulin.jwsuite.presentation.ui.session

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_DATABASE
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.domain.usecases.session.CheckPasswordValidUseCase
import com.oborodulin.jwsuite.domain.usecases.session.GetSessionUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LoginUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import com.oborodulin.jwsuite.domain.usecases.session.SignoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.Graph
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.converters.LoginSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SignoutSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SignupSessionConverter
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Presentation.SessionViewModelImpl"

// Todo: split session viewModel to signup and login viewModels

@OptIn(FlowPreview::class)
@HiltViewModel
class SessionViewModelImpl @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext val ctx: Context,
    private val state: SavedStateHandle,
    private val db: JwSuiteDatabase,
    private val useCases: SessionUseCases,
    private val sessionConverter: SessionConverter,
    private val signupConverter: SignupSessionConverter,
    private val signoutConverter: SignoutSessionConverter,
    private val loginConverter: LoginSessionConverter//,
    //private val passwordValidConverter: PasswordValidConverter
) : SessionViewModel,
    DialogViewModel<SessionUi, UiState<SessionUi>, SessionUiAction, SessionUiSingleEvent, SessionFields, InputWrapper>(
        state, initFocusedTextField = SessionFields.SESSION_PIN
    ) {
    private val _sessionMode = MutableStateFlow(SessionModeType.SIGNUP)
    private val sessionMode = _sessionMode.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(false)
    override val isPasswordValid = _isPasswordValid.asStateFlow()

    private val _isLogged = MutableStateFlow(false)
    override val isLogged = _isLogged.asStateFlow()

    override val username: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(SessionFields.SESSION_USERNAME.name, InputWrapper())
    }
    override val pin: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(SessionFields.SESSION_PIN.name, InputWrapper())
    }
    override val confirmPin: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(SessionFields.SESSION_CONFIRM_PIN.name, InputWrapper())
    }

    override val areSignupInputsValid =
        combine(username, pin, confirmPin) { username, pin, confirmPin ->
            username.errorId == null && pin.errorId == null && confirmPin.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override val areInputsValid = //flow { emit(pin.value.errorId == null)
        combine(pin, isLogged) { pin, isLogged -> pin.errorId == null && isLogged }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), false
        )

    init {
        Timber.tag(TAG).d("init called")
        loadSession()
    }

    override fun initState() = UiState.Loading

    override fun setSessionMode(mode: SessionModeType) {
        _sessionMode.value = mode
        /* focusedTextField = when (mode) {
             SessionModeType.SIGNUP -> FocusedTextField(
                 textField = SessionFields.SESSION_USERNAME,
                 key = SessionFields.SESSION_USERNAME.key()
             )

             SessionModeType.LOGIN -> FocusedTextField(
                 textField = SessionFields.SESSION_PIN,
                 key = SessionFields.SESSION_PIN.key()
             )
         }
         focusOnLastSelectedTextField()

         */
    }

    override suspend fun handleAction(action: SessionUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("handleAction(SessionUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is SessionUiAction.Load -> loadSession()
            is SessionUiAction.Signup -> signup()
            is SessionUiAction.Signout -> signout()
            is SessionUiAction.CheckPasswordValid -> checkPasswordValid()
            is SessionUiAction.Login -> login()
            is SessionUiAction.Logout -> logout(action.lastDestination)
            is SessionUiAction.StartSession -> {
                Timber.tag(TAG).d("handleAction: _isLogged.value = %s", _isLogged.value)
                if (_isLogged.value) {
                    submitSingleEvent(SessionUiSingleEvent.OpenMainScreen(Graph.MAIN))
                } else {
                    setDialogTitleResId(R.string.session_login_subheader)
                    submitSingleEvent(SessionUiSingleEvent.OpenLoginScreen(Graph.AUTH))
                }
            }

            is SessionUiAction.EnterPin -> {
                setDialogTitleResId(R.string.session_login_subheader)
                submitSingleEvent(SessionUiSingleEvent.OpenLoginScreen(NavRoutes.Login.route))
            }

            is SessionUiAction.Registration -> {
                setDialogTitleResId(R.string.session_signup_subheader)
                submitSingleEvent(SessionUiSingleEvent.OpenSignupScreen(NavRoutes.Signup.route))
            }
        }
        return job
    }

    private fun loadSession(): Job {
        Timber.tag(TAG).d("loadSession() called")
        /*val state = useCases.getSessionUseCase.execute(GetSessionUseCase.Request)
            .map { sessionConverter.convert(it) }.first()
        uiState(state)?.let { session ->
            when (session.isSigned) {
                false -> setDialogTitleResId(R.string.session_signup_subheader)
                true -> when (session.isLogged) {
                    false -> setDialogTitleResId(R.string.session_login_subheader)
                    true -> {}
                }
            }
        }
        submitState(state)*/
        val job = viewModelScope.launch(errorHandler) {
            useCases.getSessionUseCase.execute(GetSessionUseCase.Request)
                .map { sessionConverter.convert(it) }.collect { state ->
                    Timber.tag(TAG).d("loadSession: state = %s", state)
                    uiState(state)?.let { session ->
                        when (session.isSigned) {
                            false -> setDialogTitleResId(R.string.session_signup_subheader)
                            true -> when (session.isLogged) {
                                false -> setDialogTitleResId(R.string.session_login_subheader)
                                true -> {}
                            }
                        }
                    }
                    submitState(state)
                }
        }
        return job
        //return null
    }

    private fun signup(): Job {
        Timber.tag(TAG).d("signup() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.signupUseCase.execute(
                SignupUseCase.Request(username.value.value.trim(), pin.value.value.trim())
            ).map { signupConverter.convert(it) }
                .collect {
                    _isLogged.value = false
                    submitState(it)
                }
            //.collect {}
        }
        return job
    }

    private fun signout(): Job {
        Timber.tag(TAG).d("signout() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.signoutUseCase.execute(SignoutUseCase.Request)
                .map { signoutConverter.convert(it) }
                .collect { submitState(it) }
            /*.collect {
            _isLogged.value = false
        }*/
            //.collect { submitState(UiState.Success(SessionUi(lastDestination = NavRoutes.Signup.route))) }
        }
        return job
    }

    private fun checkPasswordValid(): Job {
        Timber.tag(TAG).d("checkPasswordValid() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.checkPasswordValidUseCase.execute(CheckPasswordValidUseCase.Request(pin.value.value.trim()))
                .collect {
                    if (it is Result.Success) {
                        _isPasswordValid.value = it.data.isPasswordValid
                        if (it.data.isPasswordValid) {
                            // https://stackoverflow.com/questions/41188042/how-to-recreate-a-database-on-an-android-application-update
                            if (LOG_DATABASE) {
                                Timber.tag(TAG).d("checkPasswordValid: Init Database")
                            }
                            db.openHelper.readableDatabase
                        }
                    }
                }
        }
        return job
    }

    private fun login(): Job {
        Timber.tag(TAG).d("login() called")
        val job = viewModelScope.launch(errorHandler) {
            val state = useCases.loginUseCase.execute(LoginUseCase.Request(pin.value.value.trim()))
                .map { loginConverter.convert(it) }.first()
            uiState(state)?.let { session ->
                Timber.tag(TAG).d("login: session.isLogged = %s", session.isLogged)
                _isLogged.value = session.isLogged
                submitState(state)
                //if (_isLogged.value) submitState(state)
            }
            /*useCases.loginUseCase.execute(LoginUseCase.Request(pin.value.value))
                .map { loginConverter.convert(it) }.collect { state ->
                    uiState(state)?.let { session ->
                        Timber.tag(TAG).d("login: session.isLogged = %s", session.isLogged)
                        _isLogged.value = session.isLogged
                    }
                    if (_isLogged.value) submitState(state)
                }*/
        }
        return job
    }

    private fun logout(lastDestination: String? = null): Job {
        Timber.tag(TAG).d("logout(...) called: lastDestination = %s", lastDestination)
        val job = viewModelScope.launch(errorHandler) {
            useCases.logoutUseCase.execute(LogoutUseCase.Request(lastDestination)).collect {}
            //.map { logoutConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    override fun stateInputFields() = enumValues<SessionFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: SessionUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) {
            Timber.tag(TAG)
                .d("initFieldStatesByUiModel(SessionUi) called: uiModel = %s", uiModel)
        }
        initStateValue(
            SessionFields.SESSION_USERNAME, username,
            ctx.resources.getString(com.oborodulin.jwsuite.data_congregation.R.string.def_admin_member_pseudonym)
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# observeInputEvents() called")
        }
        inputEvents.receiveAsFlow().onEach { event ->
            when (event) {
                is SessionInputEvent.Username -> setStateValue(
                    SessionFields.SESSION_USERNAME, username, event.input,
                    SessionInputValidator.Username.isValid(event.input)
                )

                is SessionInputEvent.Pin -> setStateValue(
                    SessionFields.SESSION_PIN, pin, event.input,
                    SessionInputValidator.Pin.isValid(event.input)
                )

                is SessionInputEvent.ConfirmPin -> setStateValue(
                    SessionFields.SESSION_CONFIRM_PIN, confirmPin, event.input,
                    SessionInputValidator.ConfirmPin.isValid(event.input, pin.value.value)
                )
            }
        }.debounce(350)
            .collect { event ->
                when (event) {
                    is SessionInputEvent.Username -> setStateValue(
                        SessionFields.SESSION_USERNAME, username,
                        SessionInputValidator.Username.errorIdOrNull(event.input)
                    )

                    is SessionInputEvent.Pin -> {
                        val errorId = SessionInputValidator.Pin.errorIdOrNull(event.input)
                        setStateValue(
                            SessionFields.SESSION_PIN, pin,
                            errorId, errorId?.let { ctx.resources.getString(it, PASS_MIN_LENGTH) }
                        )
                    }

                    is SessionInputEvent.ConfirmPin -> setStateValue(
                        SessionFields.SESSION_CONFIRM_PIN, confirmPin,
                        SessionInputValidator.ConfirmPin.errorIdOrNull(event.input, pin.value.value)
                    )
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        }
        val inputErrors: MutableList<InputError> = mutableListOf()
        when (sessionMode.value) {
            SessionModeType.SIGNUP -> {
                SessionInputValidator.Username.errorIdOrNull(username.value.value)?.let {
                    inputErrors.add(
                        InputError(fieldName = SessionFields.SESSION_USERNAME.name, errorId = it)
                    )
                }
                SessionInputValidator.Pin.errorIdOrNull(pin.value.value)?.let {
                    inputErrors.add(
                        InputError(fieldName = SessionFields.SESSION_PIN.name, errorId = it)
                    )
                }
                SessionInputValidator.ConfirmPin.errorIdOrNull(
                    confirmPin.value.value.trim(), pin.value.value.trim()
                )?.let {
                    inputErrors.add(
                        InputError(
                            fieldName = SessionFields.SESSION_CONFIRM_PIN.name, errorId = it
                        )
                    )
                }
            }

            SessionModeType.LOGIN -> {
                SessionInputValidator.Pin.errorIdOrNull(pin.value.value)?.let {
                    inputErrors.add(
                        InputError(fieldName = SessionFields.SESSION_PIN.name, errorId = it)
                    )
                }
            }
        }
        return inputErrors.ifEmpty { null }
    }

    // https://stackoverflow.com/questions/3656371/is-it-possible-to-have-placeholders-in-strings-xml-for-runtime-values
    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            when (sessionMode.value) {
                SessionModeType.SIGNUP -> {
                    state[error.fieldName] = when (SessionFields.valueOf(error.fieldName)) {
                        SessionFields.SESSION_USERNAME -> username.value.copy(errorId = error.errorId)
                        SessionFields.SESSION_PIN -> pin.value.copy(errorId = error.errorId)
                        SessionFields.SESSION_CONFIRM_PIN -> confirmPin.value.copy(errorId = error.errorId)
                    }
                }

                SessionModeType.LOGIN -> {
                    if (SessionFields.valueOf(error.fieldName) == SessionFields.SESSION_PIN)
                        state[error.fieldName] = pin.value.copy(errorId = error.errorId)
                }
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) = object : SessionViewModel {
            override val uiStateErrorMsg = MutableStateFlow("")
            override val isUiStateChanged = MutableStateFlow(true)
            override val dialogTitleResId =
                MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
            override val savedListItem = MutableStateFlow(ListItemModel())
            override val showDialog = MutableStateFlow(true)
            override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
            override val singleEventFlow = Channel<SessionUiSingleEvent>().receiveAsFlow()
            override val events = Channel<ScreenEvent>().receiveAsFlow()
            override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

            override fun redirectedErrorMessage() = null
            override val searchText = MutableStateFlow(TextFieldValue(""))
            override val isSearching = MutableStateFlow(false)
            override fun onSearchTextChange(text: TextFieldValue) {}
            override fun clearSearchText() {}

            override val isPasswordValid = MutableStateFlow(false)
            override val isLogged = MutableStateFlow(false)
            override val id = MutableStateFlow(InputWrapper())
            override fun id() = null
            override val username = MutableStateFlow(InputWrapper())
            override val pin = MutableStateFlow(InputWrapper())
            override val confirmPin = MutableStateFlow(InputWrapper())

            override val areSignupInputsValid = MutableStateFlow(true)
            override val areInputsValid = MutableStateFlow(true)

            override fun setSessionMode(mode: SessionModeType) {}
            override fun submitAction(action: SessionUiAction): Job? = null
            override fun handleActionJob(
                action: () -> Unit,
                afterAction: (CoroutineScope) -> Unit
            ) {
            }

            override fun onTextFieldEntered(inputEvent: Inputable) {}
            override fun onTextFieldFocusChanged(
                focusedField: SessionFields, isFocused: Boolean
            ) {
            }

            override fun moveFocusImeAction() {}
            override fun onContinueClick(
                isPartialInputsValid: Boolean, onSuccess: () -> Unit
            ) {
            }

            override fun setDialogTitleResId(dialogTitleResId: Int) {}
            override fun setSavedListItem(savedListItem: ListItemModel) {}
            override fun onOpenDialogClicked() {}
            override fun onDialogConfirm(onConfirm: () -> Unit) {}
            override fun onDialogDismiss(onDismiss: () -> Unit) {}
        }

        fun previewUiModel(ctx: Context) = SessionUi(
            isSigned = true, isLogged = true, roles = emptyList(), lastDestination = null
        ).also { it.id = UUID.randomUUID() }
    }
}