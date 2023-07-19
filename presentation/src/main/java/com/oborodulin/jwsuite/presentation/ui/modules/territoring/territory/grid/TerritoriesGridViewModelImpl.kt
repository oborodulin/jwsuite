package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single.CongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoriesListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single.TerritoryCategoryViewModelImpl
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

private const val TAG = "Territoring.TerritoriesListViewModelImpl"

@HiltViewModel
class TerritoriesGridViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val listConverter: TerritoriesListConverter
) : TerritoriesGridViewModel,
    MviViewModel<List<TerritoriesListItem>, UiState<List<TerritoriesListItem>>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoriesGridUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(TerritoriesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoriesGridUiAction.Load -> {
                loadTerritories(action.congregationId)
            }

            is TerritoriesGridUiAction.EditTerritory -> {
                submitSingleEvent(
                    TerritoriesGridUiSingleEvent.OpenTerritoryScreen(
                        NavRoutes.Territory.routeForTerritory(TerritoryInput(action.territoryId))
                    )
                )
            }

            is TerritoriesGridUiAction.DeleteTerritory -> {
                deleteTerritory(action.territoryId)
            }
        }
        return job
    }

    private fun loadTerritories(congregationId: UUID?): Job {
        Timber.tag(TAG).d("loadTerritories(...) called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoriesUseCase.execute(GetTerritoriesUseCase.Request(congregationId))
                .map {
                    listConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteTerritory(territoryId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteTerritory() called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteTerritoryUseCase.execute(DeleteTerritoryUseCase.Request(territoryId))
                .collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoriesGridViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<TerritoriesGridUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoriesGridUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                localityDistrictId = UUID.randomUUID(),
                districtShortName = ctx.resources.getString(R.string.def_don_short_name),
                microdistrictId = UUID.randomUUID(),
                microdistrictShortName = ctx.resources.getString(R.string.def_don_short_name),
                territoryNum = 1,
                isPrivateSector = false,
                isBusiness = false,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = "возле маг. \"Базилик\""
            ),
            TerritoriesListItem(
                id = UUID.randomUUID(),
                congregation = CongregationViewModelImpl.previewUiModel(ctx),
                territoryCategory = TerritoryCategoryViewModelImpl.previewUiModel(ctx),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                localityDistrictId = UUID.randomUUID(),
                districtShortName = ctx.resources.getString(R.string.def_budyonovsky_short_name),
                microdistrictId = UUID.randomUUID(),
                microdistrictShortName = ctx.resources.getString(R.string.def_cvetochny_short_name),
                territoryNum = 2,
                isPrivateSector = false,
                isBusiness = false,
                isGroupMinistry = false,
                isInPerimeter = false,
                isProcessed = false,
                isActive = true,
                territoryDesc = "напротив Храма"
            )
        )
    }
}