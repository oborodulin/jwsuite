package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.Result
import com.oborodulin.home.common.extensions.toUUIDOrNull
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.LocalityDistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.SaveLocalityDistrictUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.LocalityDistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.toLocalityUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Geo.LocalityDistrictViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class LocalityDistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: LocalityDistrictUseCases,
    private val converter: LocalityDistrictConverter,
    private val localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictsListItemMapper
) : LocalityDistrictViewModel,
    DialogViewModel<LocalityDistrictUi, UiState<LocalityDistrictUi>, LocalityDistrictUiAction, UiSingleEvent, LocalityDistrictFields, InputWrapper>(
        state, LocalityDistrictFields.LOCALITY_DISTRICT_ID.name,
        LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY
    ) {
    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY.name,
            InputListItemWrapper()
        )
    }
    override val districtShortName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME.name, InputWrapper())
    }
    override val districtName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(LocalityDistrictFields.LOCALITY_DISTRICT_NAME.name, InputWrapper())
    }

    override val areInputsValid = combine(locality, districtShortName, districtName)
    { locality, districtShortName, districtName ->
        locality.errorId == null && districtShortName.errorId == null && districtName.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: LocalityDistrictUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(LocalityDistrictUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is LocalityDistrictUiAction.Load -> {
                when (action.localityDistrictId) {
                    null -> setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.locality_district_new_subheader)
                    else -> setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.locality_district_subheader)
                }
                loadLocalityDistrict(action.localityDistrictId)
            }

            is LocalityDistrictUiAction.Save -> saveLocalityDistrict()
        }
        return job
    }

    private fun loadLocalityDistrict(localityDistrictId: UUID? = null): Job {
        Timber.tag(TAG)
            .d("loadLocalityDistrict(UUID?) called: localityDistrictId = %s", localityDistrictId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getLocalityDistrictUseCase.execute(
                GetLocalityDistrictUseCase.Request(localityDistrictId)
            )
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveLocalityDistrict(): Job {
        val localityDistrictUi = LocalityDistrictUi(
            locality = locality.value.item.toLocalityUi(),
            districtShortName = districtShortName.value.value,
            districtName = districtName.value.value
        ).also { it.id = id.value.value.toUUIDOrNull() }
        Timber.tag(TAG).d("saveLocalityDistrict() called: UI model %s", localityDistrictUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveLocalityDistrictUseCase.execute(
                SaveLocalityDistrictUseCase.Request(localityDistrictUiMapper.map(localityDistrictUi))
            ).collect {
                Timber.tag(TAG).d("saveLocalityDistrict() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(localityDistrictMapper.map(it.data.localityDistrict))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<LocalityDistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: LocalityDistrictUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(LocalityDistrictUi) called: uiModel = %s",
                uiModel
            )
        uiModel.id?.let {
            initStateValue(LocalityDistrictFields.LOCALITY_DISTRICT_ID, id, it.toString())
        }
        initStateValue(
            LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY, locality,
            uiModel.locality.toListItemModel()
        )
        initStateValue(
            LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME, districtShortName,
            uiModel.districtShortName
        )
        initStateValue(
            LocalityDistrictFields.LOCALITY_DISTRICT_NAME, districtName,
            uiModel.districtName
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is LocalityDistrictInputEvent.Locality -> setStateValue(
                        LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY, locality,
                        event.input,
                        LocalityDistrictInputValidator.Locality.isValid(event.input.headline)
                    )

                    is LocalityDistrictInputEvent.DistrictShortName -> setStateValue(
                        LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME,
                        districtShortName, event.input,
                        LocalityDistrictInputValidator.DistrictShortName.isValid(event.input)
                    )

                    is LocalityDistrictInputEvent.DistrictName -> setStateValue(
                        LocalityDistrictFields.LOCALITY_DISTRICT_NAME, districtName,
                        event.input,
                        LocalityDistrictInputValidator.DistrictName.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is LocalityDistrictInputEvent.Locality ->
                        setStateValue(
                            LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY, locality,
                            LocalityDistrictInputValidator.Locality.errorIdOrNull(event.input.headline)
                        )

                    is LocalityDistrictInputEvent.DistrictShortName ->
                        setStateValue(
                            LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME, districtShortName,
                            LocalityDistrictInputValidator.DistrictShortName.errorIdOrNull(event.input)
                        )

                    is LocalityDistrictInputEvent.DistrictName ->
                        setStateValue(
                            LocalityDistrictFields.LOCALITY_DISTRICT_NAME, districtName,
                            LocalityDistrictInputValidator.DistrictName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        LocalityDistrictInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY.name,
                    errorId = it
                )
            )
        }
        LocalityDistrictInputValidator.DistrictShortName.errorIdOrNull(districtShortName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME.name,
                        errorId = it
                    )
                )
            }
        LocalityDistrictInputValidator.DistrictName.errorIdOrNull(districtName.value.value)?.let {
            inputErrors.add(
                InputError(
                    fieldName = LocalityDistrictFields.LOCALITY_DISTRICT_NAME.name,
                    errorId = it
                )
            )
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (LocalityDistrictFields.valueOf(error.fieldName)) {
                LocalityDistrictFields.LOCALITY_DISTRICT_LOCALITY -> locality.value.copy(
                    errorId = error.errorId
                )

                LocalityDistrictFields.LOCALITY_DISTRICT_SHORT_NAME -> districtShortName.value.copy(
                    errorId = error.errorId
                )

                LocalityDistrictFields.LOCALITY_DISTRICT_NAME -> districtName.value.copy(
                    errorId = error.errorId
                )

                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : LocalityDistrictViewModel {
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

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val districtShortName = MutableStateFlow(InputWrapper())
                override val districtName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: LocalityDistrictUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: LocalityDistrictFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = LocalityDistrictUi(
            locality = LocalityViewModelImpl.previewUiModel(ctx),
            districtShortName = ctx.resources.getString(R.string.def_donetsk_short_name),
            districtName = ctx.resources.getString(R.string.def_donetsk_name)
        ).also { it.id = UUID.randomUUID() }
    }
}