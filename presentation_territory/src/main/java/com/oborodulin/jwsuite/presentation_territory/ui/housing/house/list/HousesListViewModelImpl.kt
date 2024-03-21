package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.BuildingType
import com.oborodulin.jwsuite.domain.usecases.house.DeleteHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.DeleteTerritoryHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.house.GetHousesUseCase
import com.oborodulin.jwsuite.domain.usecases.house.HouseUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.HousesListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Housing.HousesListViewModelImpl"

@HiltViewModel
class HousesListViewModelImpl @Inject constructor(
    private val useCases: HouseUseCases,
    private val converter: HousesListConverter
) : HousesListViewModel,
    ListViewModel<List<HousesListItem>, UiState<List<HousesListItem>>, HousesListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: HousesListUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(HousesListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is HousesListUiAction.Load -> loadHouses(action.streetId, action.territoryId)
            is HousesListUiAction.EditHouse -> {
                submitSingleEvent(
                    HousesListUiSingleEvent.OpenHouseScreen(
                        NavRoutes.House.routeForHouse(NavigationInput.HouseInput(action.houseId))
                    )
                )
            }

            is HousesListUiAction.DeleteHouse -> deleteHouse(action.houseId)
            is HousesListUiAction.DeleteTerritoryHouse -> deleteTerritoryHouse(action.houseId)
        }
        return job
    }

    private fun loadHouses(streetId: UUID? = null, territoryId: UUID? = null): Job {
        Timber.tag(TAG)
            .d("loadHouses(...) called: streetId = %s; territoryId = %s", streetId, territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getHousesUseCase.execute(GetHousesUseCase.Request(streetId, territoryId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteHouse(houseId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteHouse(...) called: houseId = %s", houseId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteHouseUseCase.execute(DeleteHouseUseCase.Request(houseId)).collect {}
        }
        return job
    }

    private fun deleteTerritoryHouse(houseId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteTerritoryHouse(...) called: houseId = %s", houseId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryHouseUseCase.execute(DeleteTerritoryHouseUseCase.Request(houseId))
                .collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<HousesListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : HousesListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val selectedItemFlow = MutableStateFlow(ListItemModel())
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = MutableStateFlow(null)

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: HousesListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            HousesListItem(
                id = UUID.randomUUID(),
                zipCode = "830004",
                houseNum = 1,
                houseFullNum = "1Б",
                buildingType = BuildingType.HOSTEL,
                isBusiness = true,
                isSecurity = true,
                isIntercom = true,
                isResidential = true,
                isForeignLanguage = true,
                isPrivateSector = true,
                houseExpr = ctx.resources?.getString(R.string.house_expr),
                streetFullName = "ул. Независимости",
                info = listOf("общежитие, 76 кв., маг. \"Базилик\"")
            ),
            HousesListItem(
                id = UUID.randomUUID(),
                houseNum = 145,
                houseFullNum = "145",
                buildingType = BuildingType.HOUSE,
                isBusiness = false,
                isSecurity = true,
                isIntercom = true,
                isResidential = true,
                isForeignLanguage = true,
                isPrivateSector = false,
                houseExpr = ctx.resources?.getString(R.string.house_expr),
                streetFullName = "ул. Независимости",
                info = listOf("96 кв., маг. \"Базилик\"")
            )
        )
    }
}