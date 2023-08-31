package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.DeleteRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.RegionDistrictUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.RegionDistrictsListConverter
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

private const val TAG = "Geo.RegionDistrictsListViewModelImpl"

@HiltViewModel
class RegionDistrictsListViewModelImpl @Inject constructor(
    private val useCases: RegionDistrictUseCases,
    private val converter: RegionDistrictsListConverter
) : RegionDistrictsListViewModel,
    MviViewModel<List<RegionDistrictsListItem>, UiState<List<RegionDistrictsListItem>>, RegionDistrictsListUiAction, RegionDistrictsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: RegionDistrictsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(RegionDistrictsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is RegionDistrictsListUiAction.Load -> loadRegionDistricts(action.regionId)

            is RegionDistrictsListUiAction.EditRegionDistrict -> {
                submitSingleEvent(
                    RegionDistrictsListUiSingleEvent.OpenRegionDistrictScreen(
                        NavRoutes.RegionDistrict.routeForRegionDistrict(
                            RegionDistrictInput(action.regionDistrictId)
                        )
                    )
                )
            }

            is RegionDistrictsListUiAction.DeleteRegionDistrict -> {
                deleteRegionDistrict(action.regionDistrictId)
            }
        }
        return job
    }

    private fun loadRegionDistricts(regionId: UUID? = null): Job {
        Timber.tag(TAG).d("loadRegionDistricts() called: regionId = %s", regionId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getRegionDistrictsUseCase.execute(GetRegionDistrictsUseCase.Request(regionId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteRegionDistrict(regionDistrictId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteRegionDistrict() called: regionDistrictId = %s", regionDistrictId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteRegionDistrictUseCase.execute(
                DeleteRegionDistrictUseCase.Request(regionDistrictId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<RegionDistrictsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : RegionDistrictsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<RegionDistrictsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: RegionDistrictsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            RegionDistrictsListItem(
                id = UUID.randomUUID(),
                districtShortName = ctx.resources.getString(R.string.def_reg_donetsky_short_name),
                districtName = ctx.resources.getString(R.string.def_reg_donetsky_name)
            ),
            RegionDistrictsListItem(
                id = UUID.randomUUID(),
                districtShortName = ctx.resources.getString(R.string.def_reg_maryinsky_short_name),
                districtName = ctx.resources.getString(R.string.def_reg_maryinsky_name)
            )
        )
    }
}