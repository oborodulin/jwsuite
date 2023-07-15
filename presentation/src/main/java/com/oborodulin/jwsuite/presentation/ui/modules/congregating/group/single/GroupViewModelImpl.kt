package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogSingleViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionUiToRegionMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Geo.ui.RegionViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class GroupViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionUseCases,
    private val converter: RegionConverter,
    private val mapper: RegionUiToRegionMapper
) : GroupViewModel,
    DialogSingleViewModel<RegionUi, UiState<RegionUi>, GroupUiAction, UiSingleEvent, GroupFields, InputWrapper>(
        state,
        GroupFields.REGION_CODE
    ) {
    private val regionId: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            GroupFields.REGION_ID.name,
            InputWrapper()
        )
    }
    override val regionCode: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            GroupFields.REGION_CODE.name,
            InputWrapper()
        )
    }
    override val regionName: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(
            GroupFields.REGION_NAME.name,
            InputWrapper()
        )
    }

    override val areInputsValid =
        combine(
            regionCode,
            regionName
        ) { regionCode, regionName ->
            regionCode.errorId == null && regionName.errorId == null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    override fun initState(): UiState<RegionUi> = UiState.Loading

    override suspend fun handleAction(action: GroupUiAction): Job {
        Timber.tag(TAG).d("handleAction(RegionUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is GroupUiAction.Load -> when (action.regionId) {
                null -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.region_new_subheader)
                    submitState(UiState.Success(RegionUi()))
                }

                else -> {
                    setDialogTitleResId(com.oborodulin.jwsuite.presentation.R.string.region_subheader)
                    loadRegion(action.regionId)
                }
            }

            is GroupUiAction.Save -> {
                saveRegion()
            }
        }
        return job
    }

    private fun loadRegion(regionId: UUID): Job {
        Timber.tag(TAG).d("loadRegion(UUID) called: %s", regionId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRegionUseCase.execute(GetRegionUseCase.Request(regionId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun saveRegion(): Job {
        val regionUi = RegionUi(
            regionCode = regionCode.value.value,
            regionName = regionName.value.value
        )
        regionUi.id = if (regionId.value.value.isNotEmpty()) {
            UUID.fromString(regionId.value.value)
        } else null
        Timber.tag(TAG).d("saveRegion() called: UI model %s", regionUi)
        val job = viewModelScope.launch(errorHandler) {
            useCases.saveRegionUseCase.execute(SaveRegionUseCase.Request(mapper.map(regionUi)))
                .collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<GroupFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? {
        super.initFieldStatesByUiModel(uiModel)
        val regionUi = uiModel as RegionUi
        Timber.tag(TAG)
            .d("initFieldStatesByUiModel(RegionModel) called: regionUi = %s", regionUi)
        regionUi.id?.let {
            initStateValue(GroupFields.REGION_ID, regionId, it.toString())
        }
        initStateValue(GroupFields.REGION_CODE, regionCode, regionUi.regionCode)
        initStateValue(GroupFields.REGION_NAME, regionName, regionUi.regionName)
        return null
    }

    override suspend fun observeInputEvents() {
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is GroupInputEvent.GroupCode ->
                        when (GroupInputValidator.GroupCode.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                GroupFields.REGION_CODE, regionCode, event.input, true
                            )

                            else -> setStateValue(
                                GroupFields.REGION_CODE, regionCode, event.input
                            )
                        }

                    is GroupInputEvent.GroupName ->
                        when (GroupInputValidator.GroupName.errorIdOrNull(event.input)) {
                            null -> setStateValue(
                                GroupFields.REGION_NAME, regionName, event.input, true
                            )

                            else -> setStateValue(
                                GroupFields.REGION_NAME, regionName, event.input
                            )
                        }
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is GroupInputEvent.GroupCode ->
                        setStateValue(
                            GroupFields.REGION_CODE, regionCode,
                            GroupInputValidator.GroupCode.errorIdOrNull(event.input)
                        )

                    is GroupInputEvent.GroupName ->
                        setStateValue(
                            GroupFields.REGION_NAME, regionName,
                            GroupInputValidator.GroupName.errorIdOrNull(event.input)
                        )

                }
            }
    }

    override fun getInputErrorsOrNull(): List<InputError>? {
        Timber.tag(TAG).d("getInputErrorsOrNull() called")
        val inputErrors: MutableList<InputError> = mutableListOf()
        GroupInputValidator.GroupCode.errorIdOrNull(regionCode.value.value)?.let {
            inputErrors.add(InputError(fieldName = GroupFields.REGION_CODE.name, errorId = it))
        }
        GroupInputValidator.GroupName.errorIdOrNull(regionName.value.value)?.let {
            inputErrors.add(InputError(fieldName = GroupFields.REGION_NAME.name, errorId = it))
        }
        return if (inputErrors.isEmpty()) null else inputErrors
    }

    override fun displayInputErrors(inputErrors: List<InputError>) {
        Timber.tag(TAG)
            .d("displayInputErrors() called: inputErrors.count = %d", inputErrors.size)
        for (error in inputErrors) {
            state[error.fieldName] = when (error.fieldName) {
                GroupFields.REGION_CODE.name -> regionCode.value.copy(errorId = error.errorId)
                GroupFields.REGION_NAME.name -> regionName.value.copy(errorId = error.errorId)
                else -> null
            }
        }
    }

    companion object {
        fun previewModel(ctx: Context) =
            object : GroupViewModel {
                override val dialogTitleResId =
                    MutableStateFlow(com.oborodulin.home.common.R.string.preview_blank_title)
                override val savedListItem = MutableStateFlow(ListItemModel())
                override val showDialog = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val regionCode = MutableStateFlow(InputWrapper())
                override val regionName = MutableStateFlow(InputWrapper())

                override val areInputsValid = MutableStateFlow(true)

                override fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun submitAction(action: GroupUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: GroupFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(onSuccess: () -> Unit) {}
                override fun setDialogTitleResId(dialogTitleResId: Int) {}
                override fun setSavedListItem(savedListItem: ListItemModel) {}
                override fun onOpenDialogClicked() {}
                override fun onDialogConfirm(onConfirm: () -> Unit) {}
                override fun onDialogDismiss(onDismiss: () -> Unit) {}
            }

        fun previewUiModel(ctx: Context): RegionUi {
            val regionUi = RegionUi(
                regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code),
                regionName = ctx.resources.getString(R.string.def_reg_donetsk_name)
            )
            regionUi.id = UUID.randomUUID()
            return regionUi
        }
    }
}