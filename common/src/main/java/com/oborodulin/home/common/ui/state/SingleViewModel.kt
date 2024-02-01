package com.oborodulin.home.common.ui.state

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.components.field.util.FocusedTextField
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapped
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.InputsWrapper
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_SINGLE
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_UI_STATE
import com.oborodulin.home.common.extensions.toUUIDOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Common.SingleViewModel"
private const val FOCUSED_FIELD_KEY = "focusedTextField"

abstract class SingleViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent, F : Focusable, W : InputWrapped>(
    private val state: SavedStateHandle,
    private val idFieldName: String? = null,
    initFocusedTextField: Focusable? = null
) : MviViewModel<T, S, A, E>(), SingleViewModeled<T, A, E, F> {
    override val id: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(idFieldName.orEmpty(), InputWrapper())
    }
    private val _isUiStateChanged: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isUiStateChanged = _isUiStateChanged.asStateFlow()
    private var focusedTextField = FocusedTextField(
        textField = initFocusedTextField,
        key = state[FOCUSED_FIELD_KEY] ?: initFocusedTextField?.key()
    )
        set(value) {
            field = value
            state[FOCUSED_FIELD_KEY] = value.key
        }

    private val _events = Channel<ScreenEvent>()
    override val events = _events.receiveAsFlow()
    val inputEvents = Channel<Inputable>(Channel.CONFLATED)

    init {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# init: Start observe input events")
        //Dispatchers.Default
        viewModelScope.launch(errorHandler) {
            observeInputEvents()
        }
        focusedTextField.key?.let {
            focusOnLastSelectedTextField()
        }
    }

    override fun initFieldStatesByUiModel(uiModel: T): Job? = null

    abstract suspend fun observeInputEvents()
    override fun id() = id.value.value.toUUIDOrNull()
    fun isNewUiState() = id.value.value.isEmpty()
    fun onInsert(block: () -> Unit) {
        if (isNewUiState()) {
            block.invoke()
        }
    }

    fun onUpdate(block: () -> Unit) {
        if (id.value.value.isNotEmpty()) {
            block.invoke()
        }
    }

    fun onDelete(block: () -> Unit) {
        block.invoke()
    }

    //InputWrapper:
    fun initStateValue(field: F, property: StateFlow<InputWrapper>, value: String) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("initStateValue: exist state %s = '%s'", field.key(), state[field.key()])
        if (property.value.isEmpty) {
            if (LOG_MVI_SINGLE) Timber.tag(TAG).d("initStateValue: %s = '%s'", field.key(), value)
            setStateValue(field, property, value)
            _isUiStateChanged.value = false
        }
    }

    fun setStateValue(
        field: F, property: StateFlow<InputWrapper>, value: String, isValid: Boolean = false
    ) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("setStateValue: %s = '%s' [valid = %s]", field.key(), value, isValid)
        if (isValid) {
            state[field.key()] =
                property.value.copy(value = value, errorId = null, errorMsg = null, isEmpty = false)
        } else {
            state[field.key()] = property.value.copy(value = value, isEmpty = false)
        }
        _isUiStateChanged.value = true
    }

    @JvmName("setInputWrapperStateValue")
    fun setStateValue(
        field: F, property: StateFlow<InputWrapper>,
        @StringRes errorId: Int?, errorMsg: String? = null
    ) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("setStateValue: Validate (debounce) %s - ERR[%s]", field.key(), errorId)
        state[field.key()] =
            property.value.copy(errorId = errorId, errorMsg = errorMsg, isEmpty = false)
        _isUiStateChanged.value = true
    }

    //InputsWrapper:
    fun initStateValue(
        field: F, properties: StateFlow<InputsWrapper>, value: String, key: String
    ) {
        if (LOG_MVI_SINGLE) {
            Timber.tag(TAG)
                .d("initStateValue: exist state %s = '%s'", field.key(), state[field.key()])
            Timber.tag(TAG)
                .d(
                    "initStateValue: properties.value.inputs[%s] = '%s'",
                    key, properties.value.inputs[key]
                )
        }
        if (state.get<T>(field.key()) == null || properties.value.inputs[key] == null || properties.value.inputs[key]?.isEmpty == true) {
            properties.value.inputs[key] = InputWrapper(value = value, isEmpty = false)
            if (LOG_MVI_SINGLE) {
                Timber.tag(TAG)
                    .d(
                        "initStateValue: add init value %s = '%s'",
                        field.key(),
                        properties.value.inputs[key]
                    )
                Timber.tag(TAG)
                    .d("initStateValue: copy %s = '%s'", field.key(), properties.value.inputs)
            }
            state[field.key()] =
                properties.value.copy(inputs = properties.value.inputs.toMutableMap())
        }
    }

    fun setStateValue(
        field: F, properties: StateFlow<InputsWrapper>, value: String, key: String,
        isValid: Boolean = false, isSaved: Boolean = false
    ) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d(
                "setStateValue: %s = '%s' [valid = %s; isSaved = %s]", field.key(),
                value, isValid, isSaved
            )
        if (isValid) {
            properties.value.inputs[key] = InputWrapper(
                value = value, errorId = null, errorMsg = null, isEmpty = false, isSaved = isSaved
            )
        } else {
            properties.value.inputs[key] = InputWrapper(
                value = value, isEmpty = false, isSaved = true
            )
        }
        state[field.key()] = properties.value.copy(inputs = properties.value.inputs.toMutableMap())
        _isUiStateChanged.value = true
    }

    fun setStateValue(
        field: F, properties: StateFlow<InputsWrapper>, key: String,
        @StringRes errorId: Int?, errorMsg: String? = null
    ) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("setStateValue: Validate (debounce) %s - ERR[%s]", field.key(), errorId)
        val property = properties.value.inputs.getValue(key)
        properties.value.inputs[key] =
            InputWrapper(
                value = property.value,
                errorId = errorId,
                errorMsg = errorMsg,
                isEmpty = false,
                isSaved = errorId != null
            )
        state[field.key()] = properties.value.copy(inputs = properties.value.inputs.toMutableMap())
        _isUiStateChanged.value = true
    }

    //InputListItemWrapper:
    fun <T : ListItemModel> initStateValue(
        field: F, property: StateFlow<InputListItemWrapper<T>>, item: T
    ) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("initStateValue: exist state %s = '%s'", field.key(), state[field.key()])
        if (property.value.isEmpty) {
            if (LOG_MVI_SINGLE) Timber.tag(TAG).d("initStateValue: %s = '%s'", field.key(), item)
            setStateValue(field, property, item)
            _isUiStateChanged.value = false
        }
    }

    fun <T : ListItemModel> setStateValue(
        field: F, property: StateFlow<InputListItemWrapper<T>>, item: T, isValid: Boolean = false
    ) {
        //val listItem = if (item.headline.isEmpty()) ListItemModel() else item
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("setStateValue: %s = '%s' [valid = %s]", field.key(), item, isValid)
        if (isValid) {
            state[field.key()] =
                property.value.copy(item = item, errorId = null, errorMsg = null, isEmpty = false)
        } else {
            state[field.key()] = property.value.copy(item = item, isEmpty = false)
        }
        _isUiStateChanged.value = true
    }

    @JvmName("setInputListItemWrapperStateValue")
    fun <T : ListItemModel> setStateValue(
        field: F, property: StateFlow<InputListItemWrapper<T>>,
        @StringRes errorId: Int?, errorMsg: String? = null
    ) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG)
            .d("setStateValue: Validate (debounce) %s - ERR[%s]", field.key(), errorId)
        state[field.key()] =
            property.value.copy(errorId = errorId, errorMsg = errorMsg, isEmpty = false)
        _isUiStateChanged.value = true
    }

    override fun onTextFieldEntered(inputEvent: Inputable) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# onTextFieldEntered: %s", inputEvent.javaClass.name)
        inputEvents.trySend(inputEvent)
    }

    override fun onTextFieldFocusChanged(focusedField: F, isFocused: Boolean) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# onTextFieldFocusChanged: %s - %s", focusedField.key(), isFocused)
        focusedTextField.key = if (isFocused) focusedField.key() else null
    }

    override fun moveFocusImeAction() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# moveFocusImeAction() called")
        _events.trySend(ScreenEvent.MoveFocus())
    }

    override fun onContinueClick(isPartialInputsValid: Boolean, onSuccess: () -> Unit) {
        if (LOG_MVI_SINGLE) Timber.tag(TAG).d("onContinueClick(onSuccess) called")
        viewModelScope.launch(Dispatchers.Default) {
            performValidation()
            when (val inputErrors = if (!isPartialInputsValid) getInputErrorsOrNull() else null) {
                null -> {
                    if (LOG_MVI_SINGLE) Timber.tag(TAG).d("onContinueClick: no errors")
                    clearFocusAndHideKeyboard()
                    onSuccess()
                    _isUiStateChanged.value = false
                    //clearInputFieldsStates()
                    //_events.send(ScreenEvent.ShowToast(com.oborodulin.home.common.R.string.success))
                }

                else -> {
                    if (LOG_MVI_SINGLE) Timber.tag(TAG).d("onContinueClick: errors %s", inputErrors)
                    displayInputErrors(inputErrors)
                }
            }
        }
    }

    abstract fun performValidation()
    abstract fun getInputErrorsOrNull(): List<InputError>?
    abstract fun displayInputErrors(inputErrors: List<InputError>)
    abstract fun stateInputFields(): List<String>
    open fun clearInputFieldsStates() {
        stateInputFields().forEach {
            if (LOG_MVI_SINGLE) Timber.tag(TAG).d("clearInputFieldsStates(): remove state '%s'", it)
            state.remove<W>(it)
        }
    }

    private suspend fun clearFocusAndHideKeyboard() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# clearFocusAndHideKeyboard() called")
        if (focusedTextField.textField != null) {
            _events.send(ScreenEvent.ClearFocus)
            _events.send(ScreenEvent.UpdateKeyboard(false))
            focusedTextField.textField = null
            focusedTextField.key = null
        }
    }

    private fun focusOnLastSelectedTextField() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# focusOnLastSelectedTextField() called")
        viewModelScope.launch(Dispatchers.Default) {
            focusedTextField.textField?.let {
                _events.send(ScreenEvent.RequestFocus(focusedTextField.textField!!))
                delay(250)
                _events.send(ScreenEvent.UpdateKeyboard(true))
            }
        }
    }

    override fun submitState(state: S): Job {
        if (LOG_MVI_UI_STATE) Timber.tag(TAG).d("submitState(S) called")
        return super.submitStateWithErrorStateMessageRedirection(state, true)
    }
}