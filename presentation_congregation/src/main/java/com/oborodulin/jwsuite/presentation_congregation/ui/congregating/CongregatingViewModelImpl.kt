package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregatingUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Congregating.CongregatingViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class CongregatingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle
) : CongregatingViewModel,
    SingleViewModel<CongregatingUi, UiState<CongregatingUi>, CongregatingUiAction, CongregatingUiSingleEvent, CongregatingFields, InputWrapper>(
        state
    ) {
    override val isService: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CongregatingFields.CONGREGATING_IS_SERVICE.name, InputWrapper())
    }

    override fun initState(): UiState<CongregatingUi> = UiState.Loading

    override suspend fun handleAction(action: CongregatingUiAction) = null

    override fun stateInputFields() = enumValues<CongregatingFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: CongregatingUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(CongregatingUiModel) called: congregatingUi = %s",
                uiModel
            )
        initStateValue(
            CongregatingFields.CONGREGATING_IS_SERVICE, isService, uiModel.isService.toString()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is CongregatingInputEvent.IsService ->
                        setStateValue(
                            CongregatingFields.CONGREGATING_IS_SERVICE, isService,
                            event.input.toString(), true
                        )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is CongregatingInputEvent.IsService ->
                        setStateValue(
                            CongregatingFields.CONGREGATING_IS_SERVICE, isService, null
                        )
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : CongregatingViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<CongregatingUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val isService = MutableStateFlow(InputWrapper())

                override fun submitAction(action: CongregatingUiAction): Job? = null
                override fun handleActionJob(action: () -> Unit, afterAction: (CoroutineScope) -> Unit) {}
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: CongregatingFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }
            }

        fun previewUiModel(ctx: Context) = CongregatingUi()
    }
}