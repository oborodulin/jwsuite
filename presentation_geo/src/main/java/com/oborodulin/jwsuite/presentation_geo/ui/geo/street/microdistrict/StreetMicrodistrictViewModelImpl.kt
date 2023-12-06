package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetMicrodistrictsForStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.SaveStreetMicrodistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetMicrodistrictsUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetMicrodistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.StreetMicrodistrictViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class StreetMicrodistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val streetUseCases: StreetUseCases,
    private val converter: StreetMicrodistrictConverter
) : StreetMicrodistrictViewModel,
    DialogViewModel<StreetMicrodistrictsUiModel, UiState<StreetMicrodistrictsUiModel>, StreetMicrodistrictUiAction, UiSingleEvent, StreetMicrodistrictFields, InputWrapper>(
        state, initFocusedTextField = StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET
    ) {
    override val street: StateFlow<InputListItemWrapper<StreetsListItem>> by lazy {
        state.getStateFlow(
            StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET.name,
            InputListItemWrapper()
        )
    }

    private val _checkedListItems: MutableStateFlow<List<MicrodistrictsListItem>> =
        MutableStateFlow(emptyList())
    override val checkedListItems = _checkedListItems.asStateFlow()

    override val areInputsValid = flow { emit(checkedListItems.value.isNotEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun observeCheckedListItems() {
        Timber.tag(TAG).d("observeCheckedListItems() called")
        uiState()?.let { uiState ->
            _checkedListItems.value = uiState.microdistricts.filter { it.checked }
            Timber.tag(TAG).d("checked %d List Items", _checkedListItems.value.size)
        }
    }

    override fun initState(): UiState<StreetMicrodistrictsUiModel> = UiState.Loading

    override suspend fun handleAction(action: StreetMicrodistrictUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(StreetMicrodistrictUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is StreetMicrodistrictUiAction.Load -> {
                setDialogTitleResId(R.string.street_microdistrict_new_subheader)
                loadMicrodistrictsForStreet(action.streetId)
            }

            is StreetMicrodistrictUiAction.Save -> saveStreetMicrodistricts()
        }
        return job
    }

    private fun loadMicrodistrictsForStreet(streetId: UUID): Job {
        Timber.tag(TAG).d("loadMicrodistrictsForStreet(UUID) called: %s", streetId)
        val job = viewModelScope.launch(errorHandler) {
            streetUseCases.getMicrodistrictsForStreetUseCase.execute(
                GetMicrodistrictsForStreetUseCase.Request(streetId)
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun saveStreetMicrodistricts(): Job {
        val districtIds: Map<UUID, List<UUID>> =
            _checkedListItems.value.groupBy({ it.localityDistrictId!! }) { it.id }
        Timber.tag(TAG).d(
            "saveStreetMicrodistricts() called: streetId = %s; districtIds.size = %d",
            street.value.item?.itemId,
            districtIds.size
        )
        val job = viewModelScope.launch(errorHandler) {
            streetUseCases.saveStreetMicrodistrictsUseCase.execute(
                SaveStreetMicrodistrictsUseCase.Request(street.value.item?.itemId!!, districtIds)
            ).collect {
                Timber.tag(TAG).d("saveStreetMicrodistricts() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<StreetMicrodistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: StreetMicrodistrictsUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(StreetMicrodistrictUiModel) called: uiModel = %s",
                uiModel
            )
        initStateValue(
            StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET, street,
            uiModel.street.toStreetsListItem()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is StreetMicrodistrictInputEvent.Street -> setStateValue(
                        StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET, street,
                        event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is StreetMicrodistrictInputEvent.Street -> setStateValue(
                        StreetMicrodistrictFields.STREET_MICRODISTRICT_STREET, street, null
                    )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull() = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : StreetMicrodistrictViewModel {
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

                override val checkedListItems =
                    MutableStateFlow(MicrodistrictsListViewModelImpl.previewList(ctx))

                override fun observeCheckedListItems() {}

                override val id = MutableStateFlow(InputWrapper())
                override val street = MutableStateFlow(InputListItemWrapper<StreetsListItem>())
                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: StreetMicrodistrictUiAction): Job? = null
                override fun handleActionJob(action: () -> Unit, afterAction: (CoroutineScope) -> Unit) {}
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: StreetMicrodistrictFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = StreetMicrodistrictsUiModel(
            street = StreetViewModelImpl.previewUiModel(ctx),
            microdistricts = MicrodistrictsListViewModelImpl.previewList(ctx)
        )
    }
}