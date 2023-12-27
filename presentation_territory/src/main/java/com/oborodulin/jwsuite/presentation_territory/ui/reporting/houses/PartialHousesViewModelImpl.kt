package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoringUi
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "Reporting.PartialHousesViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class PartialHousesViewModelImpl @Inject constructor(
    private val state: SavedStateHandle
) : PartialHousesViewModel,
    SingleViewModel<Any, UiState<Any>, PartialHousesUiAction, PartialHousesUiSingleEvent, PartialHousesFields, InputWrapper>(
        state
    ) {
    override val territoryStreet: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            PartialHousesFields.PARTIAL_HOUSES_TERRITORY_STREET.name, InputListItemWrapper()
        )
    }

    override fun initState(): UiState<TerritoringUi> = UiState.Loading

    override suspend fun handleAction(action: PartialHousesUiAction) = null

    /*        if (LOG_FLOW_ACTION) Timber.tag(TAG).d("handleAction(TerritoringUiAction) called: %s", action.javaClass.name)
            val job = when (action) {
                is HousingUiAction.LoadLocations -> loadTerritoryLocations(
                    action.congregationId,
                    action.isPrivateSector
                )

                is HousingUiAction.HandOutTerritoriesConfirmation -> {
                    submitSingleEvent(
                        HousingUiSingleEvent.OpenHandOutTerritoriesConfirmationScreen(
                            NavRoutes.HandOutTerritoriesConfirmation.routeForHandOutTerritoriesConfirmation()
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
                congregationId,
                isPrivateSector
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

    */
    override fun stateInputFields() = enumValues<PartialHousesFields>().map { it.name }

    /*override fun initFieldStatesByUiModel(uiModel: TerritoringUi): Job? {
            super.initFieldStatesByUiModel(uiModel)
            Timber.tag(TAG)
                .d(
                    "initFieldStatesByUiModel(TerritoringUiModel) called: territoringUi = %s",
                    uiModel
                )
            initStateValue(
                HousingFields.HOUSES_LOCALITY, locality,
                uiModel.isPrivateSector.toString()
            )
            val territoryLocation = uiModel.territoryLocations.first()
            initStateValue(
                HousingFields.HOUSES_STREET, street,
                TerritoryLocationsListItem(
                    locationId = territoryLocation.locationId,
                    locationShortName = territoryLocation.locationShortName,
                    territoryLocationType = territoryLocation.territoryLocationType
                )
            )
            return null
        }
     */

    override suspend fun observeInputEvents() {
        if (LOG_FLOW_INPUT) Timber.tag(TAG).d("IF# observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is PartialHousesInputEvent.Street -> setStateValue(
                        PartialHousesFields.PARTIAL_HOUSES_TERRITORY_STREET, territoryStreet,
                        event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is PartialHousesInputEvent.Street ->
                        setStateValue(
                            PartialHousesFields.PARTIAL_HOUSES_TERRITORY_STREET, territoryStreet,
                            null
                        )
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? = null
    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : PartialHousesViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(1)) // Any
                override val singleEventFlow = Channel<PartialHousesUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val id = MutableStateFlow(InputWrapper())
                override fun id() = null
                override val territoryStreet =
                    MutableStateFlow(InputListItemWrapper<ListItemModel>())

                override fun submitAction(action: PartialHousesUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: PartialHousesFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }
            }
    }
}