package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.oborodulin.home.common.ui.components.*
import com.oborodulin.home.common.ui.components.field.*
import com.oborodulin.home.common.ui.components.field.util.*
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoringUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

private const val TAG = "Territoring.HousesViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class HousesViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    //private val useCases: TerritoringUseCases,
    //private val converter: TerritoryLocationsListConverter
) : HousesViewModel,
    SingleViewModel<Any, UiState<Any>, HousesUiAction, HousesUiSingleEvent, HousesFields, InputWrapper>(
        state//, initFocusedTextField = HousesFields.HOUSES_LOCALITY
    ) {
    override val locality: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(HousesFields.HOUSES_LOCALITY.name, InputListItemWrapper())
    }
    override val street: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(HousesFields.HOUSES_STREET.name, InputListItemWrapper())
    }

    override fun initState(): UiState<TerritoringUi> = UiState.Loading

    override suspend fun handleAction(action: HousesUiAction) = null

    /*        Timber.tag(TAG).d("handleAction(TerritoringUiAction) called: %s", action.javaClass.name)
            val job = when (action) {
                is HousesUiAction.LoadLocations -> loadTerritoryLocations(
                    action.congregationId,
                    action.isPrivateSector
                )

                is HousesUiAction.HandOutTerritoriesConfirmation -> {
                    submitSingleEvent(
                        HousesUiSingleEvent.OpenHandOutTerritoriesConfirmationScreen(
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
    override fun stateInputFields() = enumValues<HousesFields>().map { it.name }

    /*override fun initFieldStatesByUiModel(uiModel: TerritoringUi): Job? {
            super.initFieldStatesByUiModel(uiModel)
            Timber.tag(TAG)
                .d(
                    "initFieldStatesByUiModel(TerritoringUiModel) called: territoringUi = %s",
                    uiModel
                )
            initStateValue(
                HousesFields.HOUSES_LOCALITY, locality,
                uiModel.isPrivateSector.toString()
            )
            val territoryLocation = uiModel.territoryLocations.first()
            initStateValue(
                HousesFields.HOUSES_STREET, street,
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
        Timber.tag(TAG).d("observeInputEvents() called")
        inputEvents.receiveAsFlow()
            .onEach { event ->
                when (event) {
                    is HousesInputEvent.Locality ->
                        setStateValue(HousesFields.HOUSES_LOCALITY, locality, event.input, true)

                    is HousesInputEvent.Street ->
                        setStateValue(HousesFields.HOUSES_STREET, street, event.input, true)
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is HousesInputEvent.Locality ->
                        setStateValue(HousesFields.HOUSES_LOCALITY, locality, null)

                    is HousesInputEvent.Street ->
                        setStateValue(HousesFields.HOUSES_STREET, street, null)
                }
            }
    }

    override fun performValidation() {}
    override fun getInputErrorsOrNull(): List<InputError>? = null

    override fun displayInputErrors(inputErrors: List<InputError>) {}

    companion object {
        fun previewModel(ctx: Context) =
            object : HousesViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(1)) // Any
                override val singleEventFlow = Channel<HousesUiSingleEvent>().receiveAsFlow()
                override val events = Channel<ScreenEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override val locality = MutableStateFlow(InputListItemWrapper<ListItemModel>())
                override val street = MutableStateFlow(InputListItemWrapper<ListItemModel>())

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun submitAction(action: HousesUiAction): Job? = null
                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: HousesFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(isPartialInputsValid: Boolean, onSuccess: () -> Unit) {}
            }
    }
}