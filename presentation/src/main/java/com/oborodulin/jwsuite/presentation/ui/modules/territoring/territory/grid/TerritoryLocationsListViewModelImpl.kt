package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.util.TerritoryLocationType
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryLocationsListConverter
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

private const val TAG = "Territoring.TerritoryLocationsListViewModelImpl"

@HiltViewModel
class TerritoryLocationsListViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val listConverter: TerritoryLocationsListConverter
) : TerritoryLocationsListViewModel,
    MviViewModel<List<TerritoryLocationsListItem>, UiState<List<TerritoryLocationsListItem>>, TerritoryLocationsListUiAction, TerritoriesGridUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryLocationsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(TerritoryLocationsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryLocationsListUiAction.Load -> {
                loadTerritoryLocations(action.congregationId, action.isPrivateSector)
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
                    listConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryLocationsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<TerritoriesGridUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoryLocationsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryLocationsListItem(
                locationId = null,
                congregationId = UUID.randomUUID(),
                locationName = ctx.resources.getString(com.oborodulin.home.common.R.string.all_items_unit),
                territoryLocationType = TerritoryLocationType.ALL,
                isPrivateSector = false
            ),
            TerritoryLocationsListItem(
                locationId = UUID.randomUUID(),
                congregationId = UUID.randomUUID(),
                locationName = ctx.resources.getString(R.string.def_mospino_short_name),
                territoryLocationType = TerritoryLocationType.LOCALITY,
                isPrivateSector = false
            ),
            TerritoryLocationsListItem(
                locationId = UUID.randomUUID(),
                congregationId = UUID.randomUUID(),
                locationName = ctx.resources.getString(R.string.def_budyonovsky_short_name),
                territoryLocationType = TerritoryLocationType.LOCALITY_DISTRICT,
                isPrivateSector = true
            ),
            TerritoryLocationsListItem(
                locationId = UUID.randomUUID(),
                congregationId = UUID.randomUUID(),
                locationName = ctx.resources.getString(R.string.def_cvetochny_short_name),
                territoryLocationType = TerritoryLocationType.MICRO_DISTRICT,
                isPrivateSector = false
            )
        )
    }
}