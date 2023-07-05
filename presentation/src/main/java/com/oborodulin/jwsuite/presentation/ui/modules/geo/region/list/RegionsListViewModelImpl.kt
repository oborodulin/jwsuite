package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.georegion.DeleteRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.model.RegionsListItem
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Geo.ui.RegionsListViewModelImpl"

@HiltViewModel
class RegionsListViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val regionUseCases: RegionUseCases,
    private val regionsListConverter: RegionsListConverter
) : RegionsListViewModel,
    MviViewModel<List<RegionsListItem>, UiState<List<RegionsListItem>>, RegionsListUiAction, RegionsListUiSingleEvent>(
        state = state
    ) {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RegionsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(RegionsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionsListUiAction.Load -> {
                loadRegions()
            }

            is RegionsListUiAction.EditRegion -> {
                submitSingleEvent(
                    RegionsListUiSingleEvent.OpenRegionScreen(
                        NavRoutes.Congregation.routeForCongregation(
                            CongregationInput(action.regionId)
                        )
                    )
                )
            }

            is RegionsListUiAction.DeleteRegion -> {
                deleteRegion(action.regionId)
            }
        }
        return job
    }

    private fun loadRegions(): Job {
        Timber.tag(TAG).d("loadRegions() called")
        val job = viewModelScope.launch(errorHandler) {
            regionUseCases.getRegionsUseCase.execute(GetRegionsUseCase.Request).map {
                regionsListConverter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteRegion(regionId: UUID): Job {
        Timber.tag(TAG).d("deleteRegion() called: regionId = %s", regionId.toString())
        val job = viewModelScope.launch(errorHandler) {
            regionUseCases.deleteRegionUseCase.execute(
                DeleteRegionUseCase.Request(regionId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : RegionsListViewModel {
                override var primaryObjectData: StateFlow<ArrayList<String>> =
                    MutableStateFlow(arrayListOf())
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<RegionsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                //fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: RegionsListUiAction): Job? = null
                override fun setPrimaryObjectData(value: ArrayList<String>) {}
            }

        fun previewList(ctx: Context) = listOf(
            RegionsListItem(
                id = UUID.randomUUID(),
                regionCode = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_reg_donetsk_code),
                regionName = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_reg_donetsk_name)
            ),
            RegionsListItem(
                id = UUID.randomUUID(),
                regionCode = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_reg_luhansk_code),
                regionName = ctx.resources.getString(com.oborodulin.jwsuite.data.R.string.def_reg_luhansk_name)
            )
        )
    }
}