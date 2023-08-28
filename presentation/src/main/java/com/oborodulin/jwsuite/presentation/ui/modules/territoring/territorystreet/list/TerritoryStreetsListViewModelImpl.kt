package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.street.DeleteTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetsUseCase
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryStreetsListConverter
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

private const val TAG = "Territoring.TerritoryStreetsListViewModelImpl"

@HiltViewModel
class TerritoryStreetsListViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryStreetsListConverter
) : TerritoryStreetsListViewModel,
    MviViewModel<List<TerritoryStreetsListItem>, UiState<List<TerritoryStreetsListItem>>, TerritoryStreetsListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryStreetsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(TerritoryStreetsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryStreetsListUiAction.Load -> loadTerritoryStreets(action.territoryId)
            is TerritoryStreetsListUiAction.EditTerritoryStreet -> {
                submitSingleEvent(
                    TerritoryStreetsListUiSingleEvent.OpenTerritoryStreetScreen(
                        NavRoutes.TerritoryStreet.routeForTerritoryStreet(
                            NavigationInput.TerritoryStreetInput(
                                action.territoryId, action.territoryStreetId
                            )
                        )
                    )
                )
            }

            is TerritoryStreetsListUiAction.DeleteTerritoryStreet -> deleteTerritoryStreet(
                action.territoryStreetId
            )
        }
        return job
    }

    private fun loadTerritoryStreets(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadTerritoryStreets() called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryStreetsUseCase.execute(
                GetTerritoryStreetsUseCase.Request(territoryId)
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteTerritoryStreet(territoryStreetId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteTerritoryStreet() called: territoryStreetId = %s", territoryStreetId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryStreetUseCase.execute(
                DeleteTerritoryStreetUseCase.Request(territoryStreetId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryStreetsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoryStreetsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryStreetsListItem(
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
            TerritoryStreetsListItem(
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