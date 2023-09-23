package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import android.content.Context
import androidx.annotation.ArrayRes
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
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.MicrodistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.SaveMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.util.VillageType
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single.LocalityDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.MicrodistrictConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
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
    DialogSingleViewModel<MicrodistrictUi, UiState<MicrodistrictUi>, MicrodistrictUiAction, UiSingleEvent, MicrodistrictFields, InputWrapper>(
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
        for (type in VillageType.values()) _microdistrictTypes.value[type] = resArray[type.ordinal]
    }

    override fun initState(): UiState<MicrodistrictUi> = UiState.Loading

    override suspend fun handleAction(action: MicrodistrictUiAction): Job {
        Timber.tag(TAG).d("handleAction(MicrodistrictUiAction) called: %s", action.javaClass.name)
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
        Timber.tag(TAG).d("loadMicrodistrict(UUID) called: %s", microdistrictId.toString())
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
        val localityUi = LocalityUi()
        localityUi.id = locality.value.item?.itemId
        val localityDistrictUi = LocalityDistrictUi()
        localityDistrictUi.id = localityDistrict.value.item?.itemId

        val microdistrictUi = MicrodistrictUi(
            locality = localityUi,
            localityDistrict = localityDistrictUi,
            microdistrictType = VillageType.valueOf(microdistrictType.value.value),
            microdistrictShortName = microdistrictShortName.value.value,
            microdistrictName = microdistrictName.value.value
        )
        microdistrictUi.id =
            if (id.value.value.isNotEmpty()) UUID.fromString(id.value.value) else null
        Timber.tag(TAG).d(
            "saveMicrodistrict() called: UI model %s; localityUi.id = %s; localityDistrictUi.id = %s",
            microdistrictUi,
            localityUi.id,
            localityDistrictUi.id
        )
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
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is MicrodistrictInputEvent.Locality ->
                        when (MicrodistrictInputValidator.Locality.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_LOCALITY, locality, event.input,
                                true
                            )

                            else -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_LOCALITY, locality, event.input
                            )
                        }

                    is MicrodistrictInputEvent.LocalityDistrict ->
                        when (MicrodistrictInputValidator.LocalityDistrict.errorIdOrNull(event.input.headline)) {
                            null -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT,
                                localityDistrict, event.input, true
                            )

                            else -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_LOCALITY_DISTRICT,
                                localityDistrict, event.input
                            )
                        }

                    is MicrodistrictInputEvent.MicrodistrictShortName ->
                        when (MicrodistrictInputValidator.MicrodistrictShortName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_SHORT_NAME,
                                microdistrictShortName, event.input, true
                            )

                            else -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_SHORT_NAME,
                                microdistrictShortName, event.input
                            )
                        }

                    is MicrodistrictInputEvent.MicrodistrictType ->
                        setStateValue(
                            MicrodistrictFields.MICRODISTRICT_TYPE, microdistrictType, event.input,
                            true
                        )

                    is MicrodistrictInputEvent.MicrodistrictName ->
                        when (MicrodistrictInputValidator.MicrodistrictName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_NAME, microdistrictName,
                                event.input, true
                            )

                            else -> setStateValue(
                                MicrodistrictFields.MICRODISTRICT_NAME, microdistrictName,
                                event.input
                            )
                        }
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
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
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
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
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
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val microdistrictTypes =
                    MutableStateFlow(mutableMapOf<VillageType, String>())

                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val localityDistrict =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val microdistrictShortName = MutableStateFlow(InputWrapper())
                override val microdistrictType = MutableStateFlow(InputWrapper())
                override val microdistrictName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: MicrodistrictUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: MicrodistrictFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): MicrodistrictUi {
            val microdistrictUi = MicrodistrictUi(
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                localityDistrict = LocalityDistrictViewModelImpl.previewUiModel(ctx),
                microdistrictType = VillageType.MICRO_DISTRICT,
                microdistrictShortName = ctx.resources.getString(R.string.def_don_short_name),
                microdistrictName = ctx.resources.getString(R.string.def_don_name)
            )
            microdistrictUi.id = UUID.randomUUID()
            return microdistrictUi
        }
    }
}