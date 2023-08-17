package com.oborodulin.jwsuite.presentation.ui.modules.geo.street.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geostreet.DeleteStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters.StreetsListConverter
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

private const val TAG = "Geo.StreetsListViewModelImpl"

@HiltViewModel
class StreetsListViewModelImpl @Inject constructor(
    private val useCases: StreetUseCases,
    private val converter: StreetsListConverter
) : StreetsListViewModel,
    MviViewModel<List<StreetsListItem>, UiState<List<StreetsListItem>>, StreetsListUiAction, StreetsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: StreetsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(StreetsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is StreetsListUiAction.Load -> {
                loadStreets(action.localityId, action.localityDistrictId, action.microdistrictId)
            }

            /*          is StreetsListUiAction.FilteredLoad -> {
                          loadFilteredStreets(action.search)
                      }*/

            is StreetsListUiAction.EditStreet -> {
                submitSingleEvent(
                    StreetsListUiSingleEvent.OpenStreetScreen(
                        NavRoutes.Street.routeForStreet(NavigationInput.StreetInput(action.streetId))
                    )
                )
            }

            is StreetsListUiAction.DeleteStreet -> deleteStreet(action.streetId)
        }
        return job
    }

    private fun loadStreets(
        localityId: UUID? = null, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        isPrivateSector: Boolean? = null
    ): Job {
        Timber.tag(TAG).d("loadStreets() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getStreetsUseCase.execute(
                GetStreetsUseCase.Request(
                    localityId = localityId, localityDistrictId = localityDistrictId,
                    microdistrictId = microdistrictId, isPrivateSector = isPrivateSector
                )
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun deleteStreet(streetId: UUID): Job {
        Timber.tag(TAG).d("deleteStreet() called: localityId = %s", streetId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteStreetUseCase.execute(DeleteStreetUseCase.Request(streetId)).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : StreetsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<StreetsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: StreetsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = true,
                estimatedHouses = 56,
                streetName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_baratynskogo_name)
                }"
            ),
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = true,
                estimatedHouses = 56,
                streetName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_patorgynskogo_name)
                }"
            ),
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = false,
                streetName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_strelkovojDivizii_name)
                }"
            ),
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = false,
                streetName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_nezavisimosti_name)
                }"
            )
        )
    }
}