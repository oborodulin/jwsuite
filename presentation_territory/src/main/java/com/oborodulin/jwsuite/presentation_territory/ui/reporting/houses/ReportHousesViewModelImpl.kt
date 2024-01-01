package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.components.field.util.InputError
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_INPUT
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.house.DeleteHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesUseCase
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHouseReportsListItem
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

private const val TAG = "Reporting.PartialHousesViewModelImpl"

@OptIn(FlowPreview::class)
@HiltViewModel
class ReportHousesViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: RegionUseCases,
    private val converter: RegionsListConverter
) :
    ReportHousesViewModel,
    SingleViewModel<List<TerritoryHouseReportsListItem>, UiState<List<TerritoryHouseReportsListItem>>, ReportHousesUiAction, ReportHousesUiSingleEvent, ReportHousesFields, InputWrapper>(
        state
    ) {
    override val territoryStreet: StateFlow<InputListItemWrapper<ListItemModel>> by lazy {
        state.getStateFlow(
            ReportHousesFields.PARTIAL_HOUSES_TERRITORY_STREET.name, InputListItemWrapper()
        )
    }

    override fun initState(): UiState<List<TerritoryHouseReportsListItem>> = UiState.Loading

    override suspend fun handleAction(action: ReportHousesUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(PartialHousesUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is ReportHousesUiAction.Load -> loadPartialHouses(
                action.territoryId, action.territoryStreetId
            )

            is ReportHousesUiAction.EditMemberReport -> submitSingleEvent(
                ReportHousesUiSingleEvent.OpenMemberReportScreen(
                    NavRoutes.MemberReport.routeForMemberReport(
                        NavigationInput.MemberReportInput(action.territoryMemberReportId)
                    )
                )
            )

            is ReportHousesUiAction.DeleteMemberReport -> deleteMemberReport(action.territoryMemberReportId)
        }
        return job
    }

    private fun loadPartialHouses(territoryId: UUID, territoryStreetId: UUID? = null): Job {
        Timber.tag(TAG)
            .d(
                "loadPartialHouses(...) called: territoryId = %s; territoryStreetId = %s",
                territoryId,
                territoryStreetId
            )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getHousesUseCase.execute(
                GetHousesUseCase.Request(
                    territoryId,
                    territoryStreetId
                )
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteMemberReport(memberReportId: UUID): Job {
        Timber.tag(TAG).d("deleteMemberReport(...) called: memberReportId = %s", memberReportId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteHouseUseCase.execute(DeleteHouseUseCase.Request(memberReportId))
                .collect {}
        }
        return job
    }

    override fun stateInputFields() = enumValues<ReportHousesFields>().map { it.name }

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
                    is ReportHousesInputEvent.Street -> setStateValue(
                        ReportHousesFields.PARTIAL_HOUSES_TERRITORY_STREET, territoryStreet,
                        event.input, true
                    )
                }
            }
            .debounce(350)
            .collect { event ->
                when (event) {
                    is ReportHousesInputEvent.Street ->
                        setStateValue(
                            ReportHousesFields.PARTIAL_HOUSES_TERRITORY_STREET, territoryStreet,
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
            object : ReportHousesViewModel {
                override val uiStateErrorMsg = MutableStateFlow("")
                override val isUiStateChanged = MutableStateFlow(true)
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewUiModel(ctx)))
                override val singleEventFlow = Channel<ReportHousesUiSingleEvent>().receiveAsFlow()
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

                override fun submitAction(action: ReportHousesUiAction): Job? = null
                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun onTextFieldEntered(inputEvent: Inputable) {}
                override fun onTextFieldFocusChanged(
                    focusedField: ReportHousesFields, isFocused: Boolean
                ) {
                }

                override fun moveFocusImeAction() {}
                override fun onContinueClick(
                    isPartialInputsValid: Boolean,
                    onSuccess: () -> Unit
                ) {
                }
            }

        fun previewUiModel(ctx: Context) = listOf(
            TerritoryHouseReportsListItem(
                id = UUID.randomUUID(),
                houseNum = 1,
                houseFullNum = "1Б",
                streetFullName = "ул. Независимости",
                territoryMemberId = UUID.randomUUID(),
                territoryShortMark = "ПП",
                languageCode = null,
                genderInfo = ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.male_expr),
                ageInfo = "(45 ${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.age_expr)})",
                isProcessed = false
            ),
            TerritoryHouseReportsListItem(
                id = UUID.randomUUID(),
                houseNum = 145,
                houseFullNum = "145",
                streetFullName = "ул. Независимости",
                territoryMemberId = UUID.randomUUID(),
                territoryShortMark = "ГО",
                languageCode = null,
                genderInfo = ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.female_expr),
                ageInfo = "(54 ${ctx.resources?.getString(com.oborodulin.jwsuite.domain.R.string.age_expr)})",
                isProcessed = true
            )
        )
    }
}