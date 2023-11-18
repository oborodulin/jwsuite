package com.oborodulin.jwsuite.presentation.ui.appsetting

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
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.appsetting.AppSettingUseCases
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.domain.usecases.appsetting.SaveAppSettingsUseCase
import com.oborodulin.jwsuite.domain.util.AppSettingParam
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation.ui.model.converters.AppSettingUiModelConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListItemToAppSettingsListMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single.GroupViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberToMembersListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.toCongregationsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

private const val TAG = "Presentation.AppSettingViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class AppSettingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: AppSettingUseCases,
    private val converter: AppSettingUiModelConverter,
    private val appSettingsUiMapper: AppSettingsListItemToAppSettingsListMapper,
    private val memberMapper: MemberToMembersListItemMapper
) : AppSettingViewModel,
    DialogViewModel<AppSettingsUiModel, UiState<AppSettingsUiModel>, AppSettingUiAction, UiSingleEvent, AppSettingFields, InputWrapper>(
        state, //AppSettingFields.MEMBER_ID.name, AppSettingFields.TERRITORY_PROCESSING_PERIOD
    ) {
    override val territoryProcessingPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.TERRITORY_PROCESSING_PERIOD.name, InputWrapper())
    }
    override val territoryAtHandPeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.TERRITORY_AT_HAND_PERIOD.name, InputWrapper())
    }
    override val territoryIdlePeriod: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.TERRITORY_IDLE_PERIOD.name, InputWrapper())
    }
    override val territoryRoomsLimit: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.TERRITORY_ROOMS_LIMIT.name, InputWrapper())
    }
    override val territoryMaxRooms: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(AppSettingFields.TERRITORY_MAX_ROOMS.name, InputWrapper())
    }

    override val areInputsValid =
        combine(
            territoryProcessingPeriod,
            territoryAtHandPeriod,
            territoryIdlePeriod,
            territoryRoomsLimit,
            territoryMaxRooms,
        )
        { stateFlowsArray ->
            var errorIdResult = true
            for (state in stateFlowsArray) errorIdResult = errorIdResult && state.errorId == null
            errorIdResult
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    override fun initState(): UiState<AppSettingsUiModel> = UiState.Loading

    override suspend fun handleAction(action: AppSettingUiAction): Job {
        Timber.tag(TAG).d("handleAction(AppSettingUiModelAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is AppSettingUiAction.Load -> loadAppSettings()
            is AppSettingUiAction.Save -> saveAppSettings()
        }
        return job
    }

    private fun loadAppSettings(): Job {
        Timber.tag(TAG).d("loadAppSettings() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getAppSettingsUseCase.execute(GetAppSettingsUseCase.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
        return job
    }

    private fun saveAppSettings(): Job {
        val appSettings = listOf(
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_PROCESSING_PERIOD,
                paramValue = territoryProcessingPeriod.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_AT_HAND_PERIOD,
                paramValue = territoryAtHandPeriod.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_IDLE_PERIOD,
                paramValue = territoryIdlePeriod.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_ROOMS_LIMIT,
                paramValue = territoryRoomsLimit.value.value
            ),
            AppSettingsListItem(
                paramName = AppSettingParam.TERRITORY_MAX_ROOMS,
                paramValue = territoryMaxRooms.value.value
            )
        )
        Timber.tag(TAG).d("saveAppSettings() called: UI model %s", appSettings)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveAppSettingsUseCase.execute(
                SaveAppSettingsUseCase.Request(
                    appSettingsUiMapper.map(appSettings)
                )
            )
                .collect {
                    Timber.tag(TAG).d("saveMember() collect: %s", it)
                }
        }
        return job
    }

    override fun stateInputFields() = enumValues<AppSettingFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: AppSettingsUiModel): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(MemberModel) called: memberUi = %s", uiModel)
        initStateValue(
            AppSettingFields.TERRITORY_PROCESSING_PERIOD, territoryProcessingPeriod,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_PROCESSING_PERIOD }.paramValue
        )
        initStateValue(
            AppSettingFields.TERRITORY_AT_HAND_PERIOD, territoryAtHandPeriod,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_AT_HAND_PERIOD }.paramValue
        )
        initStateValue(
            AppSettingFields.TERRITORY_IDLE_PERIOD, territoryIdlePeriod,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_IDLE_PERIOD }.paramValue
        )
        initStateValue(
            AppSettingFields.TERRITORY_ROOMS_LIMIT, territoryRoomsLimit,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_ROOMS_LIMIT }.paramValue
        )
        initStateValue(
            AppSettingFields.TERRITORY_MAX_ROOMS, territoryMaxRooms,
            uiModel.settings.first { it.paramName == AppSettingParam.TERRITORY_MAX_ROOMS }.paramValue
        )
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is AppSettingInputEvent.TerritoryProcessingPeriod ->
                        when (AppSettingInputValidator.TerritoryProcessingPeriod.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.TERRITORY_PROCESSING_PERIOD,
                                territoryProcessingPeriod, event.input, true
                            )

                            else -> setStateValue(
                                AppSettingFields.TERRITORY_PROCESSING_PERIOD,
                                territoryProcessingPeriod, event.input
                            )
                        }

                    is AppSettingInputEvent.TerritoryIdlePeriod ->
                        setStateValue(
                            AppSettingFields.TERRITORY_IDLE_PERIOD,
                            territoryIdlePeriod,
                            event.input,
                            true
                        )

                    is AppSettingInputEvent.TerritoryAtHandPeriod ->
                        setStateValue(
                            AppSettingFields.TERRITORY_AT_HAND_PERIOD,
                            territoryAtHandPeriod,
                            event.input,
                            true
                        )

                    is AppSettingInputEvent.TerritoryRoomsLimit ->
                        setStateValue(
                            AppSettingFields.TERRITORY_ROOMS_LIMIT,
                            territoryRoomsLimit,
                            event.input,
                            true
                        )

                    is AppSettingInputEvent.TerritoryMaxRooms ->
                        when (AppSettingInputValidator.TerritoryMaxRooms.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                AppSettingFields.TERRITORY_MAX_ROOMS,
                                territoryMaxRooms,
                                event.input,
                                true
                            )

                            else -> setStateValue(
                                AppSettingFields.TERRITORY_MAX_ROOMS, territoryMaxRooms, event.input
                            )
                        }

                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                       is AppSettingInputEvent.TerritoryProcessingPeriod ->
                        setStateValue(
                            AppSettingFields.TERRITORY_PROCESSING_PERIOD, territoryProcessingPeriod,
                            AppSettingInputValidator.TerritoryProcessingPeriod.errorIdOrNull(event.input)
                        )

                    is AppSettingInputEvent.TerritoryIdlePeriod ->
                        setStateValue(
                            AppSettingFields.TERRITORY_IDLE_PERIOD,
                            territoryIdlePeriod,
                            null
                        )

                    is AppSettingInputEvent.TerritoryAtHandPeriod ->
                        setStateValue(
                            AppSettingFields.TERRITORY_AT_HAND_PERIOD,
                            territoryAtHandPeriod,
                            null
                        )

                    is AppSettingInputEvent.TerritoryRoomsLimit ->
                        setStateValue(
                            AppSettingFields.TERRITORY_ROOMS_LIMIT,
                            territoryRoomsLimit,
                            null
                        )

                    is AppSettingInputEvent.TerritoryMaxRooms ->
                        setStateValue(
                            AppSettingFields.TERRITORY_MAX_ROOMS, territoryMaxRooms,
                            AppSettingInputValidator.TerritoryMaxRooms.errorIdOrNull(event.input)
                        )

                       }
            }
    }

    override fun performValidation() {

    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()

        AppSettingInputValidator.TerritoryProcessingPeriod.errorIdOrNull(territoryProcessingPeriod.value.value)
            ?.let {
                inputErrors.add(
                    InputError(
                        fieldName = AppSettingFields.TERRITORY_PROCESSING_PERIOD.name,
                        errorId = it
                    )
                )
            }
        AppSettingInputValidator.TerritoryMaxRooms.errorIdOrNull(territoryMaxRooms.value.value)
            ?.let {
                inputErrors.add(
                    InputError(fieldName = AppSettingFields.TERRITORY_MAX_ROOMS.name, errorId = it)
                )
            }
          return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (AppSettingFields.valueOf(error.fieldName)) {
                  AppSettingFields.TERRITORY_PROCESSING_PERIOD -> territoryProcessingPeriod.value.copy(
                    errorId = error.errorId
                )

                AppSettingFields.TERRITORY_MAX_ROOMS -> territoryMaxRooms.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : AppSettingViewModel {
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

                override val memberTypes = MutableStateFlow(mutableMapOf<MemberType, String>())

                override val id = MutableStateFlow(InputWrapper())
                override val territoryProcessingPeriod = MutableStateFlow(InputWrapper())
                override val territoryAtHandPeriod = MutableStateFlow(InputWrapper())
                override val territoryIdlePeriod = MutableStateFlow(InputWrapper())
                override val territoryRoomsLimit = MutableStateFlow(InputWrapper())
                override val territoryMaxRooms = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun submitAction(action: AppSettingUiAction): Job? = null
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: AppSettingFields, isFocused: Boolean
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

        fun previewUiModel(ctx: Context): AppSettingsUiModel  {
            val memberUi = AppSettingsUiModel (
                group = GroupViewModelImpl.previewUiModel(ctx),
                memberNum = ctx.resources.getString(R.string.def_ivanov_member_num),
                memberName = ctx.resources.getString(R.string.def_ivanov_member_name),
                surname = ctx.resources.getString(R.string.def_ivanov_member_surname),
                patronymic = ctx.resources.getString(R.string.def_ivanov_member_patronymic),
                pseudonym = ctx.resources.getString(R.string.def_ivanov_member_pseudonym),
                phoneNumber = "+79493851487",
                memberType = MemberType.PREACHER,
                dateOfBirth = Utils.toOffsetDateTime("1981-08-01T14:29:10.212+03:00"),
                dateOfBaptism = Utils.toOffsetDateTime("1994-06-14T14:29:10.212+03:00")
            )
            memberUi.id = UUID.randomUUID()
            return memberUi
        }
    }
}