package com.oborodulin.jwsuite.presentation.ui.session

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogSingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.session.GetSessionUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LoginUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import com.oborodulin.jwsuite.domain.usecases.session.SignoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.converters.LoginSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SignupSessionConverter
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Presentation.SessionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class SessionViewModelImpl @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext val ctx: Context,
    private val state: SavedStateHandle,
    private val useCases: SessionUseCases,
    private val sessionConverter: SessionConverter,
    private val signupConverter: SignupSessionConverter,
    private val loginConverter: LoginSessionConverter
) : SessionViewModel,
    DialogSingleViewModel<SessionUi, UiState<SessionUi>, SessionUiAction, UiSingleEvent, SessionFields, InputWrapper>(
        state, initFocusedTextField = SessionFields.SESSION_USERNAME
    ) {
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

    override val areLoginInputsValid = flow { emit(pin.value.errorId == null) }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    init {
        Timber.tag(TAG).d("init called")
        loadSession()
    }

    override fun initState(): UiState<SessionUi> = UiState.Loading

    override suspend fun handleAction(action: SessionUiAction): Job {
        Timber.tag(TAG).d("handleAction(SignupUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is SessionUiAction.Load -> loadSession()
            is SessionUiAction.Signup -> signup()
            is SessionUiAction.Signout -> signout()
            is SessionUiAction.Login -> login()
            is SessionUiAction.Logout -> logout()
        }
        return job
    }

    private fun loadSession(): Job {
        Timber.tag(TAG).d("loadSession(...) called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getSessionUseCase.execute(GetSessionUseCase.Request)
                .map { sessionConverter.convert(it) }.collect { state ->
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
    }

    private fun signup(): Job {
        Timber.tag(TAG).d("signup(...) called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.signupUseCase.execute(
                SignupUseCase.Request(username.value.value, pin.value.value)
            ).map { signupConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun signout(): Job {
        Timber.tag(TAG).d("signout(...) called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.signoutUseCase.execute(SignoutUseCase.Request)
                .collect { submitState(UiState.Success(SessionUi(lastDestination = NavRoutes.Signup.route))) }
        }
        return job
    }

    private fun login(): Job {
        Timber.tag(TAG).d("login(...) called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.loginUseCase.execute(LoginUseCase.Request(pin.value.value))
                .map { loginConverter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun logout(): Job {
        Timber.tag(TAG).d("logout(...) called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.logoutUseCase.execute(LogoutUseCase.Request)
                .collect { submitState(UiState.Success(SessionUi())) }
        }
        return job
    }

    override fun stateInputFields() = enumValues<SessionFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: SessionUi) = null

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow().onEach { event ->
            when (event) {
                is SessionInputEvent.Username -> when (SessionInputValidator.Username.errorIdOrNull(
                    event.input
                )) {
                    null -> setStateValue(
                        SessionFields.SESSION_USERNAME, username, event.input, true
                    )

                    else -> setStateValue(
                        SessionFields.SESSION_USERNAME, username, event.input
                    )
                }

                is SessionInputEvent.Pin -> when (SessionInputValidator.Pin.errorIdOrNull(event.input)) {
                    null -> setStateValue(
                        SessionFields.SESSION_PIN, pin, event.input, true
                    )

                    else -> setStateValue(
                        SessionFields.SESSION_PIN, pin, event.input
                    )
                }

                is SessionInputEvent.ConfirmPin -> when (SessionInputValidator.ConfirmPin.errorIdOrNull(
                    event.input
                )) {
                    null -> setStateValue(
                        SessionFields.SESSION_CONFIRM_PIN, confirmPin, event.input, true
                    )

                    else -> setStateValue(
                        SessionFields.SESSION_CONFIRM_PIN, confirmPin, event.input
                    )
                }
            }
        }.debounce(350).collect { event ->
            when (event) {
                is SessionInputEvent.Username -> setStateValue(
                    SessionFields.SESSION_USERNAME, username,
                    SessionInputValidator.Username.errorIdOrNull(event.input)
                )

                is SessionInputEvent.Pin -> setStateValue(
                    SessionFields.SESSION_PIN, pin,
                    SessionInputValidator.Pin.errorIdOrNull(event.input)
                )

                is SessionInputEvent.ConfirmPin -> setStateValue(
                    SessionFields.SESSION_CONFIRM_PIN, confirmPin,
                    SessionInputValidator.ConfirmPin.errorIdOrNull(event.input)
                )
            }
        }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
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
        SessionInputValidator.ConfirmPin.errorIdOrNull(confirmPin.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = SessionFields.SESSION_CONFIRM_PIN.name, errorId = it)
            )
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    // https://stackoverflow.com/questions/3656371/is-it-possible-to-have-placeholders-in-strings-xml-for-runtime-values
    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG).d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        val res = ctx.resources
        for (error in inputErrors) {
            state[error.fieldName] = when (SessionFields.valueOf(error.fieldName)) {
                SessionFields.SESSION_USERNAME -> username.value.copy(errorId = error.errorId)
                SessionFields.SESSION_PIN -> pin.value.copy(
                    errorId = error.errorId,
                    errorMsg = res.getString(error.errorId!!, PASS_MIN_LENGTH)
                )

                SessionFields.SESSION_CONFIRM_PIN -> confirmPin.value.copy(
                    errorId = error.errorId
                )

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
            override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
            override val events = Channel<ScreenEvent>().receiveAsFlow()
            override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

            override val searchText = MutableStateFlow(TextFieldValue(""))
            override val isSearching = MutableStateFlow(false)
            override fun onSearchTextChange(text: TextFieldValue) {}

            override val username = MutableStateFlow(InputWrapper())
            override val pin = MutableStateFlow(InputWrapper())
            override val confirmPin = MutableStateFlow(InputWrapper())

            override val areSignupInputsValid = MutableStateFlow(true)
            override val areLoginInputsValid = MutableStateFlow(true)

            override fun submitAction(action: SessionUiAction): Job? = null
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

        fun previewUiModel(ctx: Context): SessionUi {
            val sessionUi = SessionUi(
                isSigned = true, isLogged = true, roles = emptyList(), lastDestination = null
            )
            sessionUi.id = UUID.randomUUID()
            return sessionUi
        }
    }
}