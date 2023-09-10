package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.street.DeleteHouseUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetHousesUseCase
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.HousesListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
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

private const val TAG = "Territoring.HousesListViewModelImpl"

@HiltViewModel
class HousesListViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val converter: HousesListConverter
) : HousesListViewModel,
    MviViewModel<List<HousesListItem>, UiState<List<HousesListItem>>, HousesListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: HousesListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(HousesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is HousesListUiAction.Load -> loadHouses(action.territoryId)
            is HousesListUiAction.EditHouse -> {
                submitSingleEvent(
                    HousesListUiSingleEvent.OpenHouseScreen(
                        NavRoutes.House.routeForHouse(
                            NavigationInput.HouseInput(
                                action.territoryId, action.territoryStreetId
                            )
                        )
                    )
                )
            }

            is HousesListUiAction.DeleteHouse -> deleteHouse(
                action.territoryStreetId
            )
        }
        return job
    }

    private fun loadHouses(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadHouses() called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getHousesUseCase.execute(
                GetHousesUseCase.Request(territoryId)
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteHouse(territoryStreetId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteHouse() called: territoryStreetId = %s", territoryStreetId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteHouseUseCase.execute(
                DeleteHouseUseCase.Request(territoryStreetId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<HousesListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : HousesListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: HousesListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            HousesListItem(
                id = UUID.randomUUID(),
                streetId = UUID.randomUUID(),
                streetFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_baratynskogo_name)
                }",
                info = listOfNotNull(
                    ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.private_sector_expr),
                    ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.even_expr)
                )
            ),
            HousesListItem(
                id = UUID.randomUUID(),
                streetId = UUID.randomUUID(),
                streetFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_patorgynskogo_name)
                }",
                info = listOfNotNull(
                    ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.private_sector_expr),
                    ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.even_expr),
                    "38 ${ctx.resources.getString(com.oborodulin.jwsuite.domain.R.string.house_expr)}"
                )
            )
        )
    }
}