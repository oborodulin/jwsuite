package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.LocalityUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.domain.util.LocalityType
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation.ui.model.converters.LocalityConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityUiToLocalityMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Geo.ui.LocalityViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class RegionDistrictViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: LocalityUseCases,
    private val converter: LocalityConverter,
    private val mapper: LocalityUiToLocalityMapper
) : RegionDistrictViewModel,
    SingleViewModel<LocalityUi, UiState<LocalityUi>, RegionDistrictUiAction, UiSingleEvent, RegionDistrictFields, InputWrapper>(
        state,
        RegionDistrictFields.LOCALITY_CODE
    ) {
    private val localityId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_ID.name,
            InputWrapper()
        )
    }
    override val region: StateFlow<InputListItemWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.REGION_ID.name,
            InputListItemWrapper()
        )
    }
    override val regionDistrict: StateFlow<InputListItemWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.REGION_DISTRICT_ID.name,
            InputListItemWrapper()
        )
    }
    override val localityCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_CODE.name,
            InputWrapper()
        )
    }
    override val localityShortName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_SHORT_NAME.name,
            InputWrapper()
        )
    }
    override val localityType: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_TYPE.name,
            InputWrapper()
        )
    }
    override val localityName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            RegionDistrictFields.LOCALITY_NAME.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            region,
            localityCode,
            localityShortName,
            localityName
        ) { region, localityCode, localityShortName, localityName ->
            region.errorId == null && localityCode.errorId == null && localityShortName.errorId == null && localityName.errorId == null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<LocalityUi> = UiState.Loading

    override suspend fun handleAction(action: RegionDistrictUiAction): Job {
        Timber.tag(TAG).d("handleAction(LocalityUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionDistrictUiAction.Create -> {
                submitState(UiState.Success(LocalityUi()))
            }

            is RegionDistrictUiAction.Load -> {
                loadLocality(action.localityId)
            }

            is RegionDistrictUiAction.Save -> {
                saveLocality()
            }
        }
        return job
    }

    private fun loadLocality(localityId: UUID): Job {
        Timber.tag(TAG).d("loadLocality(UUID) called: %s", localityId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getLocalityUseCase.execute(GetLocalityUseCase.Request(localityId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveLocality(): Job {
        val regionUi = RegionUi()
        regionUi.id = region.value.item.itemId
        val regionDistrictUi = RegionDistrictUi()
        regionUi.id = regionDistrict.value.item.itemId

        val localityUi = LocalityUi(
            region = regionUi,
            regionDistrict = regionDistrictUi,
            localityCode = localityCode.value.value,
            localityType = LocalityType.valueOf(localityType.value.value),
            localityShortName = localityShortName.value.value,
            localityName = localityName.value.value
        )
        localityUi.id = if (localityId.value.value.isNotEmpty()) {
            UUID.fromString(localityId.value.value)
        } else null
        Timber.tag(TAG).d("saveLocality() called: UI model %s", localityUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveLocalityUseCase.execute(
                SaveLocalityUseCase.Request(mapper.map(localityUi))
            ).collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<RegionDistrictFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val localityUi = uiModel as LocalityUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(LocalityModel) called: localityUi = %s", localityUi)
        localityUi.id?.let {
            initStateValue(RegionDistrictFields.LOCALITY_ID, localityId, it.toString())
        }
        initStateValue(
            RegionDistrictFields.REGION_ID,
            region,
            ListItemModel(localityUi.region.id, localityUi.region.regionName)
        )
        initStateValue(
            RegionDistrictFields.REGION_DISTRICT_ID,
            regionDistrict,
            ListItemModel(
                localityUi.regionDistrict?.id, localityUi.regionDistrict?.districtName ?: ""
            )
        )
        initStateValue(RegionDistrictFields.LOCALITY_CODE, localityCode, localityUi.localityCode)
        initStateValue(
            RegionDistrictFields.LOCALITY_SHORT_NAME,
            localityShortName,
            localityUi.localityShortName
        )
        initStateValue(
            RegionDistrictFields.LOCALITY_TYPE,
            localityType,
            localityUi.localityType.name
        )
        initStateValue(
            RegionDistrictFields.LOCALITY_NAME,
            localityName,
            localityUi.localityName
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is RegionDistrictInputEvent.Region ->
                        when (RegionDistrictInputValidator.Region.errorIdOrNull(event.input.itemId.toString())) {
                            null -> setStateValue(
                                RegionDistrictFields.REGION_ID, region, event.input, true
                            )

                            else -> setStateValue(RegionDistrictFields.REGION_ID, region, event.input)
                        }

                    is RegionDistrictInputEvent.RegionDistrict ->
                        setStateValue(
                            RegionDistrictFields.REGION_DISTRICT_ID, regionDistrict, event.input, true
                        )

                    is RegionDistrictInputEvent.RegionDistrictCode ->
                        when (RegionDistrictInputValidator.RegionDistrictCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LOCALITY_CODE, localityCode, event.input, true
                            )

                            else -> setStateValue(
                                RegionDistrictFields.LOCALITY_CODE, localityCode, event.input
                            )
                        }

                    is RegionDistrictInputEvent.RegionDistrictShortName ->
                        when (RegionDistrictInputValidator.RegionDistrictShortName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LOCALITY_SHORT_NAME, localityShortName, event.input,
                                true
                            )

                            else -> setStateValue(
                                RegionDistrictFields.LOCALITY_SHORT_NAME, localityShortName, event.input
                            )
                        }

                    is RegionDistrictInputEvent.RegionDistrictType ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_TYPE, localityType, event.input, true
                        )

                    is RegionDistrictInputEvent.RegionDistrictName ->
                        when (RegionDistrictInputValidator.RegionDistrictName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                RegionDistrictFields.LOCALITY_NAME, localityName, event.input, true
                            )

                            else -> setStateValue(
                                RegionDistrictFields.LOCALITY_NAME, localityName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is RegionDistrictInputEvent.Region ->
                        setStateValue(
                            RegionDistrictFields.REGION_ID, region,
                            RegionDistrictInputValidator.Region.errorIdOrNull(event.input.itemId.toString())
                        )

                    is RegionDistrictInputEvent.RegionDistrict ->
                        setStateValue(
                            RegionDistrictFields.REGION_DISTRICT_ID, regionDistrict, null
                        )

                    is RegionDistrictInputEvent.RegionDistrictCode ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_CODE, localityCode,
                            RegionDistrictInputValidator.RegionDistrictCode.errorIdOrNull(event.input)
                        )

                    is RegionDistrictInputEvent.RegionDistrictShortName ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_SHORT_NAME, localityShortName,
                            RegionDistrictInputValidator.RegionDistrictShortName.errorIdOrNull(event.input)
                        )

                    is RegionDistrictInputEvent.RegionDistrictType ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_TYPE, localityType, null
                        )

                    is RegionDistrictInputEvent.RegionDistrictName ->
                        setStateValue(
                            RegionDistrictFields.LOCALITY_NAME, localityName,
                            RegionDistrictInputValidator.RegionDistrictName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        RegionDistrictInputValidator.Region.errorIdOrNull(region.value.item.headline)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.REGION_ID.name, errorId = it))
        }
        RegionDistrictInputValidator.RegionDistrictCode.errorIdOrNull(localityCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LOCALITY_CODE.name, errorId = it))
        }
        RegionDistrictInputValidator.RegionDistrictShortName.errorIdOrNull(localityShortName.value.value)?.let {
            inputErrors.add(
                InputError(fieldName = RegionDistrictFields.LOCALITY_SHORT_NAME.name, errorId = it)
            )
        }
        RegionDistrictInputValidator.RegionDistrictName.errorIdOrNull(localityName.value.value)?.let {
            inputErrors.add(InputError(fieldName = RegionDistrictFields.LOCALITY_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                RegionDistrictFields.REGION_ID.name -> region.value.copy(errorId = error.errorId)
                RegionDistrictFields.LOCALITY_CODE.name -> localityCode.value.copy(errorId = error.errorId)
                RegionDistrictFields.LOCALITY_SHORT_NAME.name -> localityShortName.value.copy(errorId = error.errorId)
                RegionDistrictFields.LOCALITY_NAME.name -> localityName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        val previewModel =
            object : RegionDistrictViewModel {
                override var dialogTitleResId: Int? = null
                override val uiStateFlow = MutableStateFlow(UiState.Success(LocalityUi()))
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val region = MutableStateFlow(InputListItemWrapper())
                override val regionDistrict = MutableStateFlow(InputListItemWrapper())
                override val localityCode = MutableStateFlow(InputWrapper())
                override val localityShortName = MutableStateFlow(InputWrapper())
                override val localityType = MutableStateFlow(InputWrapper())
                override val localityName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: RegionDistrictUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: RegionDistrictFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
            }

        fun previewLocalityUi(ctx: Context): LocalityUi {
            val localityUi = LocalityUi(
                region = RegionUi(),
                //regionDistrict = ,
                localityCode = ctx.resources.getString(R.string.def_donetsk_code),
                localityShortName = ctx.resources.getString(R.string.def_donetsk_short_name),
                localityName = ctx.resources.getString(R.string.def_donetsk_name)
            )
            localityUi.id = UUID.randomUUID()
            return localityUi
        }
    }
}