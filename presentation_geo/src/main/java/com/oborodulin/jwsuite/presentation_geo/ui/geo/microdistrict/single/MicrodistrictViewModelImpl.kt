package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.domain.entities.Result
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
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.types.VillageType
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.MicrodistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.SaveMicrodistrictUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.MicrodistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.toLocalityDistrictUi
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
import kotlinx.coroutines.flow.asStateFlow
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

private const val TAG = "Geo.MicrodistrictViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class MicrodistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val resHelper: ResourcesHelper,
    private val useCases: MicrodistrictUseCases,
    private val converter: MicrodistrictConverter,
    private val microdistrictUiMapper: MicrodistrictUiToMicrodistrictMapper,
    private val microdistrictMapper: MicrodistrictToMicrodistrictsListItemMapper
) : MicrodistrictViewModel,
    DialogViewModel<MicrodistrictUi, UiState<MicrodistrictUi>, MicrodistrictUiAction, UiSingleEvent, MicrodistrictFields, InputWrapper>(
        state, MicrodistrictFields.MICRODISTRICT_ID.name, MicrodistrictFields.MICRODISTRICT_LOCALITY
    ) {
    private val _microdistrictTypes: MutableStateFlow<MutableMap<VillageType, String>> =
        MutableStateFlow(mutableMapOf())
    override val microdistrictTypes = _microdistrictTypes.asStateFlow()

    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(MicrodistrictFields.MICRODISTRICT_LOCALITY.name, InputListItemWrapper())
    }
    override val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT.name,
            InputListItemWrapper()
        )
    }
    override val microdistrictShortName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MicrodistrictFields.MICRODISTRICT_SHORT_NAME.name, InputWrapper())
    }
    override val microdistrictType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MicrodistrictFields.MICRODISTRICT_TYPE.name, InputWrapper())
    }
    override val microdistrictName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(MicrodistrictFields.MICRODISTRICT_NAME.name, InputWrapper())
    }

    override val areInputsValid =
        combine(locality, microdistrictShortName, microdistrictName)
        { locality, microdistrictShortName, microdistrictName ->
            locality.errorId == null && microdistrictShortName.errorId == null && microdistrictName.errorId == null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initVillageTypes(com.oborodulin.jwsuite.domain.R.array.village_types)
    }

    private fun initVillageTypes(@ArrayRes arrayId: Int) {
        val resArray = resHelper.appContext.resources.getStringArray(arrayId)
        for (type in VillageType.entries) _microdistrictTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MicrodistrictUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(MicrodistrictUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is MicrodistrictUiAction.Load -> when (action.microdistrictId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.microdistrict_new_subheader)
                    submitState(UiState.Success(MicrodistrictUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.microdistrict_subheader)
                    loadMicrodistrict(action.microdistrictId)
                }
            }

            is MicrodistrictUiAction.Save -> saveMicrodistrict()
        }
        return job
    }

    private fun loadMicrodistrict(microdistrictId: UUID): Job {
        Timber.tag(TAG).d("loadMicrodistrict(UUID) called: %s", microdistrictId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getMicrodistrictUseCase.execute(GetMicrodistrictUseCase.Request(microdistrictId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveMicrodistrict(): Job {
        val microdistrictUi = MicrodistrictUi(
            locality = locality.value.item.toLocalityUi(),
            localityDistrict = localityDistrict.value.item.toLocalityDistrictUi(),
            microdistrictType = VillageType.valueOf(microdistrictType.value.value),
            microdistrictShortName = microdistrictShortName.value.value,
            microdistrictName = microdistrictName.value.value
        )
        microdistrictUi.id = id.value.value.toUUIDOrNull()
        Timber.tag(TAG).d("saveMicrodistrict() called: UI model %s", microdistrictUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveMicrodistrictUseCase.execute(
                SaveMicrodistrictUseCase.Request(microdistrictUiMapper.map(microdistrictUi))
            ).collect {
                Timber.tag(TAG).d("saveMicrodistrict() collect: %s", it)
                if (it is Result.Success) {
                    setSavedListItem(microdistrictMapper.map(it.data.microdistrict))
                }
            }
        }
        return job
    }

    override fun stateInputFields() = enumValues<MicrodistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: MicrodistrictUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(MicrodistrictModel) called: microdistrictUi = %s",
                uiModel
            )
        uiModel.id?.let { initStateValue(MicrodistrictFields.MICRODISTRICT_ID, id, it.toString()) }
        initStateValue(
            MicrodistrictFields.MICRODISTRICT_LOCALITY, locality,
            ListItemModel(uiModel.locality.id, uiModel.locality.localityName)
        )
        initStateValue(
            MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT, localityDistrict,
            ListItemModel(
                uiModel.localityDistrict.id,
                uiModel.localityDistrict.districtName
            )
        )
        initStateValue(
            MicrodistrictFields.MICRODISTRICT_SHORT_NAME, microdistrictShortName,
            uiModel.microdistrictShortName
        )
        initStateValue(
            MicrodistrictFields.MICRODISTRICT_TYPE, microdistrictType,
            uiModel.microdistrictType.name
        )
        initStateValue(
            MicrodistrictFields.MICRODISTRICT_NAME, microdistrictName,
            uiModel.microdistrictName
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MicrodistrictInputEvent.Locality -> setStateValue(
                        MicrodistrictFields.MICRODISTRICT_LOCALITY, locality, event.input,
                        MicrodistrictInputValidator.Locality.isValid(event.input.headline)
                    )

                    is MicrodistrictInputEvent.LocalityDistrict -> setStateValue(
                        MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT,
                        localityDistrict, event.input,
                        MicrodistrictInputValidator.LocalityDistrict.isValid(event.input.headline)
                    )

                    is MicrodistrictInputEvent.MicrodistrictShortName -> setStateValue(
                        MicrodistrictFields.MICRODISTRICT_SHORT_NAME,
                        microdistrictShortName, event.input,
                        MicrodistrictInputValidator.MicrodistrictShortName.isValid(event.input)
                    )

                    is MicrodistrictInputEvent.MicrodistrictType -> setStateValue(
                        MicrodistrictFields.MICRODISTRICT_TYPE, microdistrictType, event.input,
                        true
                    )

                    is MicrodistrictInputEvent.MicrodistrictName -> setStateValue(
                        MicrodistrictFields.MICRODISTRICT_NAME, microdistrictName,
                        event.input,
                        MicrodistrictInputValidator.MicrodistrictName.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is MicrodistrictInputEvent.Locality ->
                        setStateValue(
                            MicrodistrictFields.MICRODISTRICT_LOCALITY, locality,
                            MicrodistrictInputValidator.Locality.errorIdOrNull(event.input.headline)
                        )

                    is MicrodistrictInputEvent.LocalityDistrict ->
                        setStateValue(
                            MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT, localityDistrict,
                            MicrodistrictInputValidator.LocalityDistrict.errorIdOrNull(event.input.headline)
                        )

                    is MicrodistrictInputEvent.MicrodistrictShortName ->
                        setStateValue(
                            MicrodistrictFields.MICRODISTRICT_SHORT_NAME, microdistrictShortName,
                            MicrodistrictInputValidator.MicrodistrictShortName.errorIdOrNull(event.input)
                        )

                    is MicrodistrictInputEvent.MicrodistrictType ->
                        setStateValue(
                            MicrodistrictFields.MICRODISTRICT_TYPE, microdistrictType, null
                        )

                    is MicrodistrictInputEvent.MicrodistrictName ->
                        setStateValue(
                            MicrodistrictFields.MICRODISTRICT_NAME, microdistrictName,
                            MicrodistrictInputValidator.MicrodistrictName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        MicrodistrictInputValidator.Locality.errorIdOrNull(locality.value.item?.headline)?.let {
            inputErrors.add(
                InputError(
                    fieldName = MicrodistrictFields.MICRODISTRICT_LOCALITY.name, errorId = it
                )
            )
        }
        MicrodistrictInputValidator.LocalityDistrict.errorIdOrNull(localityDistrict.value.item?.headline)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT.name,
                        errorId = it
                    )
                )
            }
        MicrodistrictInputValidator.MicrodistrictShortName.errorIdOrNull(microdistrictShortName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = MicrodistrictFields.MICRODISTRICT_SHORT_NAME.name, errorId = it
                    )
                )
            }
        MicrodistrictInputValidator.MicrodistrictName.errorIdOrNull(microdistrictName.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = MicrodistrictFields.MICRODISTRICT_NAME.name, errorId = it
                    )
                )
            }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) Timber.tag(TAG)
            .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (MicrodistrictFields.valueOf(error.fieldName)) {
                MicrodistrictFields.MICRODISTRICT_LOCALITY -> locality.value.copy(errorId = error.errorId)
                MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT -> localityDistrict.value.copy(
                    errorId = error.errorId
                )

                MicrodistrictFields.MICRODISTRICT_SHORT_NAME -> microdistrictShortName.value.copy(
                    errorId = error.errorId
                )

                MicrodistrictFields.MICRODISTRICT_NAME -> microdistrictName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : MicrodistrictViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val microdistrictTypes =
                    MutableStateFlow(mutableMapOf<VillageType, String>())

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
                override val localityDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val microdistrictShortName = MutableStateFlow(InputWrapper())
                override val microdistrictType = MutableStateFlow(InputWrapper())
                override val microdistrictName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: MicrodistrictUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: MicrodistrictFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = MicrodistrictUi(
            locality = LocalityViewModelImpl.previewUiModel(ctx),
            localityDistrict = LocalityDistrictViewModelImpl.previewUiModel(ctx),
            microdistrictType = VillageType.MICRO_DISTRICT,
            microdistrictShortName = ctx.resources.getString(R.string.def_don_short_name),
            microdistrictName = ctx.resources.getString(R.string.def_don_name)
        ).also { it.id = UUID.randomUUID() }
    }
}