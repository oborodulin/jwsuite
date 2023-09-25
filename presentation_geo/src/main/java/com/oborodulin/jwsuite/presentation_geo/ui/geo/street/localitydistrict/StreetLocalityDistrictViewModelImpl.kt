package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

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
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetLocalityDistrictsForStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.SaveStreetLocalityDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single.StreetViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetLocalityDistrictUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetLocalityDistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.StreetLocalityDistrictViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class StreetLocalityDistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val streetUseCases: StreetUseCases,
    private val converter: StreetLocalityDistrictConverter
) : StreetLocalityDistrictViewModel,
    DialogSingleViewModel<StreetLocalityDistrictUiModel, UiState<StreetLocalityDistrictUiModel>, StreetLocalityDistrictUiAction, UiSingleEvent, StreetLocalityDistrictFields, InputWrapper>(
        state, initFocusedTextField = StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET
    ) {
    override val street: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET.name,
            InputListItemWrapper()
        )
    }

    private val _checkedListItems: MutableStateFlow<List<LocalityDistrictsListItem>> =
        MutableStateFlow(emptyList())
    override val checkedListItems = _checkedListItems.asStateFlow()

    override val areInputsValid = flow { emit(checkedListItems.value.isNotEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun observeCheckedListItems() {
        Timber.tag(TAG).d("observeCheckedListItems() called")
        uiState()?.let { uiState ->
            _checkedListItems.value = uiState.localityDistricts.filter { it.checked }
            Timber.tag(TAG).d("checked %d List Items", _checkedListItems.value.size)
        }
    }

    override fun initState(): UiState<StreetLocalityDistrictUiModel> = UiState.Loading

    override suspend fun handleAction(action: StreetLocalityDistrictUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(StreetLocalityDistrictUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is StreetLocalityDistrictUiAction.Load -> {
                setDialogTitleResId(R.string.street_locality_district_new_subheader)
                loadLocalityDistrictsForStreet(action.streetId)
            }

            is StreetLocalityDistrictUiAction.Save -> saveStreetLocalityDistricts(action.localityDistrictIds)
        }
        return job
    }

    private fun loadLocalityDistrictsForStreet(streetId: UUID): Job {
        Timber.tag(TAG).d("loadLocalityDistrictsForStreet(UUID) called: %s", streetId)
        val job = viewModelScope.launch(errorHandler) {
            streetUseCases.getLocalityDistrictsForStreetUseCase.execute(
                GetLocalityDistrictsForStreetUseCase.Request(streetId)
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun saveStreetLocalityDistricts(localityDistrictIds: List<UUID> = emptyList()): Job {
        Timber.tag(TAG).d(
            "saveStreetLocalityDistricts() called: streetId = %s; localityDistrictIds.size = %d",
            street.value.item?.itemId,
            localityDistrictIds.size
        )
        val job = viewModelScope.launch(errorHandler) {
            streetUseCases.saveStreetLocalityDistrictsUseCase.execute(
                SaveStreetLocalityDistrictsUseCase.Request(
                    street.value.item?.itemId!!, localityDistrictIds
                )
            ).collect {
                Timber.tag(TAG).d("saveStreetLocalityDistricts() collect: %s", it)
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<StreetLocalityDistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: StreetLocalityDistrictUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(StreetLocalityDistrictUiModel) called: uiModel = %s",
                uiModel
            )
        initStateValue(
            StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET, street,
            uiModel.street.toStreetsListItem()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is StreetLocalityDistrictInputEvent.Street -> setStateValue(
                        StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET, street,
                        event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is StreetLocalityDistrictInputEvent.Street -> setStateValue(
                        StreetLocalityDistrictFields.STREET_LOCALITY_DISTRICT_STREET, street, null
                    )
                }
            }
    }

    override fun performValidation() {}

    override fun getInputErrorsOrNull() = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : StreetLocalityDistrictViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val checkedListItems =
                    MutableStateFlow(LocalityDistrictsListViewModelImpl.previewList(ctx))

                override fun observeCheckedListItems() {}

                override val street = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val areInputsValid = MutableStateFlow(true)

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: StreetLocalityDistrictUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: StreetLocalityDistrictFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = StreetLocalityDistrictUiModel(
            street = StreetViewModelImpl.previewUiModel(ctx),
            localityDistricts = LocalityDistrictsListViewModelImpl.previewList(ctx)
        )
    }
}