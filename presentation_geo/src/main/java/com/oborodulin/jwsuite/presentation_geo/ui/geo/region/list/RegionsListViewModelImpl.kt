package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.georegion.DeleteRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionsListConverter
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

private const val TAG = "Geo.RegionsListViewModelImpl"

@HiltViewModel
class RegionsListViewModelImpl @Inject constructor(
    private val regionUseCases: RegionUseCases,
    private val regionsListConverter: RegionsListConverter
) : RegionsListViewModel,
    ListViewModel<List<RegionsListItem>, UiState<List<RegionsListItem>>, RegionsListUiAction, RegionsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RegionsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(RegionsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionsListUiAction.Load -> loadRegions()

            is RegionsListUiAction.EditRegion -> {
                submitSingleEvent(
                    RegionsListUiSingleEvent.OpenRegionScreen(
                        NavRoutes.Region.routeForRegion(RegionInput(action.regionId))
                    )
                )
            }

            is RegionsListUiAction.DeleteRegion -> deleteRegion(action.regionId)
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

    override fun initFieldStatesByUiModel(uiModel: List<RegionsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : RegionsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<RegionsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: RegionsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            RegionsListItem(
                id = UUID.randomUUID(),
                regionCode = ctx.resources.getString(R.string.def_reg_donetsk_code),
                regionName = ctx.resources.getString(R.string.def_reg_donetsk_name)
            ),
            RegionsListItem(
                id = UUID.randomUUID(),
                regionCode = ctx.resources.getString(R.string.def_reg_luhansk_code),
                regionName = ctx.resources.getString(R.string.def_reg_luhansk_name)
            )
        )
    }
}