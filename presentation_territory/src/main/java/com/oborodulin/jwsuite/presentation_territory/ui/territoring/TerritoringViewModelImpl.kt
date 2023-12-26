package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.types.TerritoryLocationType
import com.oborodulin.jwsuite.domain.usecases.TerritoringUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoringUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryLocationsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Territoring.TerritoringViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class TerritoringViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: TerritoringUseCases,
    private val converter: TerritoryLocationsListConverter
) : TerritoringViewModel,
    SingleViewModel<TerritoringUi, UiState<TerritoringUi>, TerritoringUiAction, TerritoringUiSingleEvent, TerritoringFields, InputWrapper>(
        state, initFocusedTextField = TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR
    ) {
    override val isPrivateSector: StateFlow<InputWrapper> by lazy {
        state.getStateFlow(TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR.name, InputWrapper())
    }
    override val location: StateFlow<InputListItemWrapper<TerritoryLocationsListItem>> by lazy {
        state.getStateFlow(TerritoringFields.TERRITORY_LOCATION.name, InputListItemWrapper())
    }

    override fun initState(): UiState<TerritoringUi> = UiState.Loading

    override suspend fun handleAction(action: TerritoringUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(TerritoringUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoringUiAction.LoadLocations -> loadTerritoryLocations(
                action.congregationId, action.isPrivateSector
            )

            is TerritoringUiAction.HandOutConfirmation -> {
                submitSingleEvent(
                    TerritoringUiSingleEvent.OpenHandOutConfirmationScreen(
                        NavRoutes.HandOutConfirmation.routeForHandOutConfirmation()
                    )
                )
            }
        }
        return job
    }

    private fun loadTerritoryLocations(congregationId: UUID?, isPrivateSector: Boolean = false):
            Job {
        Timber.tag(TAG).d(
            "loadTerritoryLocations(...) called: congregationId = %s; isPrivateSector = %s",
            congregationId, isPrivateSector
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryLocationsUseCase.execute(
                GetTerritoryLocationsUseCase.Request(congregationId, isPrivateSector)
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

    override fun stateInputFields() = enumValues<TerritoringFields>().map { it.name }

    override fun initFieldStatesByUiModel(uiModel: TerritoringUi): Job? {
        super.initFieldStatesByUiModel(uiModel)
        Timber.tag(TAG)
            .d(
                "initFieldStatesByUiModel(TerritoringUiModel) called: territoringUi = %s",
                uiModel
            )
        initStateValue(
            TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR, isPrivateSector,
            uiModel.isPrivateSector.toString()
        )
        val territoryLocation = uiModel.territoryLocations.first()
        initStateValue(
            TerritoringFields.TERRITORY_LOCATION, location,
            TerritoryLocationsListItem(
                locationId = territoryLocation.locationId,
                locationShortName = territoryLocation.locationShortName,
                territoryLocationType = territoryLocation.territoryLocationType
            )
        )
        return null
    }

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is TerritoringInputEvent.IsPrivateSector -> setStateValue(
                        TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR, isPrivateSector,
                        event.input.toString(), true
                    )

                    is TerritoringInputEvent.Location -> setStateValue(
                        TerritoringFields.TERRITORY_LOCATION, location, event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is TerritoringInputEvent.IsPrivateSector -> setStateValue(
                        TerritoringFields.TERRITORING_IS_PRIVATE_SECTOR, isPrivateSector, null
                    )

                    is TerritoringInputEvent.Location ->
                        setStateValue(TerritoringFields.TERRITORY_LOCATION, location, null)
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoringViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<TerritoringUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val isPrivateSector = MutableStateFlow(InputWrapper())
                override val location =
                    MutableStateFlow(InputListItemWrapper<TerritoryLocationsListItem>())

                override fun submitAction(action: TerritoringUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: TerritoringFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryLocationsListItem(
                locationId = null,
                congregationId = UUID.randomUUID(),
                locationShortName = ctx.resources.getString(com.oborodulin.home.common.R.string.all_items_val),
                territoryLocationType = TerritoryLocationType.ALL,
                isPrivateSector = false
            ),
            TerritoryLocationsListItem(
                locationId = UUID.randomUUID(),
                congregationId = UUID.randomUUID(),
                locationShortName = ctx.resources.getString(R.string.def_mospino_short_name),
                territoryLocationType = TerritoryLocationType.LOCALITY,
                isPrivateSector = false
            ),
            TerritoryLocationsListItem(
                locationId = UUID.randomUUID(),
                congregationId = UUID.randomUUID(),
                locationShortName = ctx.resources.getString(R.string.def_budyonovsky_short_name),
                territoryLocationType = TerritoryLocationType.LOCALITY_DISTRICT,
                isPrivateSector = true
            ),
            TerritoryLocationsListItem(
                locationId = UUID.randomUUID(),
                congregationId = UUID.randomUUID(),
                locationShortName = ctx.resources.getString(R.string.def_cvetochny_short_name),
                territoryLocationType = TerritoryLocationType.MICRO_DISTRICT,
                isPrivateSector = false
            )
        )

        fun previewUiModel(ctx: Context) = TerritoringUi(
            territoryLocations = previewList(ctx),
            isPrivateSector = false
        )
    }
}