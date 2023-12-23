package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.types.RoadType
import com.oborodulin.jwsuite.domain.usecases.geostreet.DeleteStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsForTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetsForTerritoryListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.StreetsListConverter
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

private const val TAG = "Geo.StreetsListViewModelImpl"

@HiltViewModel
class StreetsListViewModelImpl @Inject constructor(
    private val useCases: StreetUseCases,
    private val streetsConverter: StreetsListConverter,
    private val streetsForTerritoryConverter: StreetsForTerritoryListConverter
) : StreetsListViewModel,
    ListViewModel<List<StreetsListItem>, UiState<List<StreetsListItem>>, StreetsListUiAction, StreetsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: StreetsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(StreetsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is StreetsListUiAction.Load -> loadStreets(
                action.localityId, action.localityDistrictId, action.microdistrictId,
                action.isPrivateSector
            )

            is StreetsListUiAction.LoadForTerritory -> loadStreetsForTerritory(
                action.localityId, action.localityDistrictId, action.microdistrictId,
                action.excludes
            )
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
        Timber.tag(TAG).d(
            "loadStreets(...) called: localityId = %s; localityDistrictId = %s; microdistrictId = %s; isPrivateSector = %s",
            localityId, localityDistrictId, microdistrictId, isPrivateSector
        )
        val job = viewModelScope.launch(errorHandler) {
            useCases.getStreetsUseCase.execute(
                GetStreetsUseCase.Request(
                    localityId = localityId, localityDistrictId = localityDistrictId,
                    microdistrictId = microdistrictId, isPrivateSector = isPrivateSector
                )
            ).map {
                streetsConverter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun loadStreetsForTerritory(
        localityId: UUID, localityDistrictId: UUID? = null, microdistrictId: UUID? = null,
        excludes: List<UUID> = emptyList()
    ): Job {
        Timber.tag(TAG).d("loadStreets() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getStreetsForTerritoryUseCase.execute(
                GetStreetsForTerritoryUseCase.Request(
                    localityId = localityId, localityDistrictId = localityDistrictId,
                    microdistrictId = microdistrictId, excludes = excludes
                )
            ).map {
                streetsForTerritoryConverter.convert(it)
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

    override fun initFieldStatesByUiModel(uiModel: List<StreetsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : StreetsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<StreetsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = null

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: StreetsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = true,
                estimatedHouses = 56,
                streetFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_baratynskogo_name)
                }"
            ),
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = true,
                estimatedHouses = 56,
                streetFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_patorgynskogo_name)
                }"
            ),
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = false,
                streetFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_strelkovojDivizii_name)
                }"
            ),
            StreetsListItem(
                id = UUID.randomUUID(),
                isPrivateSector = false,
                streetFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.road_types)[RoadType.STREET.ordinal]} ${
                    ctx.resources.getString(R.string.def_nezavisimosti_name)
                }"
            )
        )
    }
}