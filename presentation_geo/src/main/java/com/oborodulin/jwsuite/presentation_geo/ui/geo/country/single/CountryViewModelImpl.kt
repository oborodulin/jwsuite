package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.extensions.toUUIDOrNull
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geocountry.CountryUseCases
import com.oborodulin.jwsuite.domain.usecases.geocountry.GetCountryUseCase
import com.oborodulin.jwsuite.domain.usecases.geocountry.SaveCountryUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.CoordinatesUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.CountryConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.SaveCountryConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.country.CountryUiToCountryMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.toListItemModel
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

private const val TAG = "Geo.CountryViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class CountryViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: CountryUseCases,
    private val getConverter: CountryConverter,
    private val saveConverter: SaveCountryConverter,
    private val countryUiMapper: CountryUiToCountryMapper
) : CountryViewModel,
    DialogViewModel<CountryUi, UiState<CountryUi>, CountryUiAction, UiSingleEvent, CountryFields, InputWrapper>(
        state, CountryFields.COUNTRY_ID.name, CountryFields.COUNTRY_CODE
    ) {
    override val tlId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_TL_ID.name, InputWrapper())
    }
    override val countryCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_CODE.name, InputWrapper())
    }
    override val countryName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_NAME.name, InputWrapper())
    }
    override val countryGeocode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_GEOCODE.name, InputWrapper())
    }
    override val countryOsmId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_OSM_ID.name, InputWrapper())
    }
    override val latitude: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_LATITUDE.name, InputWrapper())
    }
    override val longitude: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(CountryFields.COUNTRY_LONGITUDE.name, InputWrapper())
    }

    override val areInputsValid = combine(countryCode, countryName) { countryCode, countryName ->
        countryCode.errorId == null && countryName.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: CountryUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG).d("handleAction(CountryUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is CountryUiAction.Load -> when (action.countryId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.country_new_subheader)
                    submitState(UiState.Success(CountryUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation_geo.R.string.country_subheader)
                    loadCountry(action.countryId)
                }
            }

            is CountryUiAction.Save -> saveCountry()
        }
        return job
    }

    private fun loadCountry(countryId: UUID): Job {
        Timber.tag(TAG).d("loadCountry(UUID) called: countryId = %s", countryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getCountryUseCase.execute(GetCountryUseCase.Request(countryId))
                .map {
                    getConverter.convert(it)
                }
                .collect {
                    Timber.tag(TAG).d("loadCountry(): CountryUi = %s", it)
                    submitState(it)
                }
        }
        return job
    }

    private fun saveCountry(): Job {
        val countryUi = CountryUi(
            countryCode = countryCode.value.value,
            countryGeocode = countryGeocode.value.value.ifEmpty { null },
            countryOsmId = countryOsmId.value.value.toLongOrNull(),
            coordinates = CoordinatesUi(
                latitude.value.value.toBigDecimalOrNull(),
                longitude.value.value.toBigDecimalOrNull()
            ),
            countryName = countryName.value.value
        ).also {
            it.id = id()
            it.tlId = tlId.value.value.toUUIDOrNull()
        }
        Timber.tag(TAG).d("saveCountry() called: UI model %s", countryUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveCountryUseCase.execute(
                SaveCountryUseCase.Request(countryUiMapper.map(countryUi))
            ).map { saveConverter.convert(it) }
                .collect {
                    Timber.tag(TAG).d("saveCountry() collect: %s", it)
                    if (it is UiState.Success) {
                        setSavedListItem(it.data.toListItemModel())
                    }
                    submitState(it)
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<CountryFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: CountryUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        if (LOG_UI_STATE) {
            Timber.tag(TAG)
                .d("initFieldStatesByUiModel(CountryUi) called: uiModel = %s", uiModel)
        }
        uiModel.id?.let { initStateValue(CountryFields.COUNTRY_ID, id, it.toString()) }
        uiModel.tlId?.let { initStateValue(CountryFields.COUNTRY_TL_ID, tlId, it.toString()) }
        initStateValue(CountryFields.COUNTRY_CODE, countryCode, uiModel.countryCode)
        initStateValue(CountryFields.COUNTRY_NAME, countryName, uiModel.countryName)
        initStateValue(
            CountryFields.COUNTRY_GEOCODE, countryGeocode, uiModel.countryGeocode.orEmpty()
        )
        initStateValue(
            CountryFields.COUNTRY_OSM_ID, countryOsmId, uiModel.countryOsmId?.toString().orEmpty()
        )
        initStateValue(
            CountryFields.COUNTRY_LATITUDE, latitude,
            uiModel.coordinates.latitude?.toString().orEmpty()
        )
        initStateValue(
            CountryFields.COUNTRY_LONGITUDE, longitude,
            uiModel.coordinates.longitude?.toString().orEmpty()
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG).d("IF# observeInputEvents() called")
        }
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is CountryInputEvent.CountryCode -> setStateValue(
                        CountryFields.COUNTRY_CODE, countryCode, event.input,
                        CountryInputValidator.CountryCode.isValid(event.input)
                    )

                    is CountryInputEvent.CountryName -> setStateValue(
                        CountryFields.COUNTRY_NAME, countryName, event.input,
                        CountryInputValidator.CountryName.isValid(event.input)
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is CountryInputEvent.CountryCode -> setStateValue(
                        CountryFields.COUNTRY_CODE, countryCode,
                        CountryInputValidator.CountryCode.errorIdOrNull(event.input)
                    )

                    is CountryInputEvent.CountryName -> setStateValue(
                        CountryFields.COUNTRY_NAME, countryName,
                        CountryInputValidator.CountryName.errorIdOrNull(event.input)
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
        CountryInputValidator.CountryCode.errorIdOrNull(countryCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = CountryFields.COUNTRY_CODE.name, errorId = it))
        }
        CountryInputValidator.CountryName.errorIdOrNull(countryName.value.value)?.let {
            inputErrors.add(InputError(fieldName = CountryFields.COUNTRY_NAME.name, errorId = it))
        }
        return inputErrors.ifEmpty { null }
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        if (LOG_FLOW_INPUT) {
            Timber.tag(TAG)
                .d("IF# displayInputErrors(...) called: inputErrors.count = %d", inputErrors.size)
        }
        for (error in inputErrors) {
            state[error.fieldName] = when (CountryFields.valueOf(error.fieldName)) {
                CountryFields.COUNTRY_CODE -> countryCode.value.copy(errorId = error.errorId)
                CountryFields.COUNTRY_NAME -> countryName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : CountryViewModel {
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
                override val tlId = MutableStateFlow(InputWrapper())
                override val countryCode = MutableStateFlow(InputWrapper())
                override val countryName = MutableStateFlow(InputWrapper())
                override val countryGeocode = MutableStateFlow(InputWrapper())
                override val countryOsmId = MutableStateFlow(InputWrapper())
                override val latitude = MutableStateFlow(InputWrapper())
                override val longitude = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: CountryUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: CountryFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context) = CountryUi(
            countryCode = ctx.resources.getString(R.string.def_country_code),
            countryName = ctx.resources.getString(R.string.def_country_name)
        ).also { it.id = UUID.randomUUID() }
    }
}