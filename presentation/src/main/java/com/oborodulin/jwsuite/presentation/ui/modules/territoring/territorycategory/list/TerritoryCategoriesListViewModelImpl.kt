package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.territorycategory.DeleteTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.TerritoryCategoryUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryCategoriesListConverter
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

private const val TAG = "Territoring.TerritoryCategoriesListViewModelImpl"

@HiltViewModel
class TerritoryCategoriesListViewModelImpl @Inject constructor(
    private val regionUseCases: TerritoryCategoryUseCases,
    private val regionsListConverter: TerritoryCategoriesListConverter
) : TerritoryCategoriesListViewModel,
    MviViewModel<List<TerritoryCategoriesListItem>, UiState<List<TerritoryCategoriesListItem>>, TerritoryCategoriesListUiAction, TerritoryCategoriesListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryCategoriesListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(TerritoryCategoriesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryCategoriesListUiAction.Load -> loadTerritoryCategories()

            is TerritoryCategoriesListUiAction.EditTerritoryCategory -> {
                submitSingleEvent(
                    TerritoryCategoriesListUiSingleEvent.OpenTerritoryCategoryScreen(
                        NavRoutes.TerritoryCategory.routeForTerritoryCategory(
                            TerritoryCategoryInput(
                                action.territoryCategoryId
                            )
                        )
                    )
                )
            }

            is TerritoryCategoriesListUiAction.DeleteTerritoryCategory -> deleteTerritoryCategory(
                action.territoryCategoryId
            )
        }
        return job
    }

    private fun loadTerritoryCategories(): Job {
        Timber.tag(TAG).d("loadTerritoryCategories() called")
        val job = viewModelScope.launch(errorHandler) {
            regionUseCases.getTerritoryCategoriesUseCase.execute(GetTerritoryCategoriesUseCase.Request)
                .map {
                    regionsListConverter.convert(it)
                }.collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteTerritoryCategory(regionId: UUID): Job {
        Timber.tag(TAG).d("deleteTerritoryCategory() called: regionId = %s", regionId.toString())
        val job = viewModelScope.launch(errorHandler) {
            regionUseCases.deleteTerritoryCategoryUseCase.execute(
                DeleteTerritoryCategoryUseCase.Request(regionId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryCategoriesListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<TerritoryCategoriesListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoryCategoriesListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryCategoriesListItem(
                id = UUID.randomUUID(),
                territoryCategoryCode = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_house_territory_category_code),
                territoryCategoryMark = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_house_territory_category_mark),
                territoryCategoryName = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_house_territory_category_name)
            ),
            TerritoryCategoriesListItem(
                id = UUID.randomUUID(),
                territoryCategoryCode = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_floor_territory_category_code),
                territoryCategoryMark = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_floor_territory_category_mark),
                territoryCategoryName = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_floor_territory_category_name)
            ),
            TerritoryCategoriesListItem(
                id = UUID.randomUUID(),
                territoryCategoryCode = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_room_territory_category_code),
                territoryCategoryMark = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_room_territory_category_mark),
                territoryCategoryName = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_room_territory_category_name)
            )
        )
    }
}