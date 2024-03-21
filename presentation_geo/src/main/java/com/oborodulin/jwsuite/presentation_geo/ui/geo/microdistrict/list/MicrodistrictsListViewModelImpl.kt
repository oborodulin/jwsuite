package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.types.VillageType
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.DeleteMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.MicrodistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.geostreet.DeleteStreetMicrodistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.geostreet.StreetUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.converters.MicrodistrictsListConverter
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

private const val TAG = "Geo.MicrodistrictsListViewModelImpl"

@HiltViewModel
class MicrodistrictsListViewModelImpl @Inject constructor(
    private val microdistrictUseCases: MicrodistrictUseCases,
    private val streetUseCases: StreetUseCases,
    private val converter: MicrodistrictsListConverter
) : MicrodistrictsListViewModel,
    ListViewModel<List<MicrodistrictsListItem>, UiState<List<MicrodistrictsListItem>>, MicrodistrictsListUiAction, MicrodistrictsListUiSingleEvent>() {
    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: MicrodistrictsListUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(MicrodistrictsListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is MicrodistrictsListUiAction.Load -> loadMicrodistricts(
                localityId = action.localityId,
                localityDistrictId = action.localityDistrictId,
                streetId = action.streetId
            )

            /*          is MicrodistrictsListUiAction.FilteredLoad -> {
                          loadFilteredMicrodistricts(action.search)
                      }*/

            is MicrodistrictsListUiAction.EditMicrodistrict -> submitSingleEvent(
                MicrodistrictsListUiSingleEvent.OpenMicrodistrictScreen(
                    NavRoutes.Microdistrict.routeForMicrodistrict(MicrodistrictInput(action.microdistrictId))
                )
            )

            is MicrodistrictsListUiAction.DeleteMicrodistrict -> deleteMicrodistrict(action.microdistrictId)
            is MicrodistrictsListUiAction.DeleteStreetMicrodistrict -> deleteStreetMicrodistrict(
                streetId = action.streetId,
                microdistrictId = action.microdistrictId
            )
        }
        return job
    }

    private fun loadMicrodistricts(
        localityId: UUID? = null, localityDistrictId: UUID? = null, streetId: UUID? = null
    ): Job {
        Timber.tag(TAG).d(
            "loadMicrodistricts(...) called: localityId = %s; localityDistrictId = %s; streetId = %s",
            localityId, localityDistrictId, streetId
        )
        val job = viewModelScope.launch(errorHandler) {
            microdistrictUseCases.getMicrodistrictsUseCase.execute(
                GetMicrodistrictsUseCase.Request(
                    localityId = localityId, localityDistrictId = localityDistrictId,
                    streetId = streetId
                )
            ).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    /*
        private fun loadFilteredMicrodistricts(search: String): Job {
            Timber.tag(TAG).d("loadFilteredMicrodistricts() called")
            val job = viewModelScope.launch(errorHandler) {
                useCases.getMicrodistrictsUseCase.execute(
                    GetMicrodistrictsUseCase.Request(regionId, regionDistrictId)
                ).map {
                    converter.convert(it)
                }
                    .collect {
                        submitState(it)
                    }
            }
            return job
        }
    */
    private fun deleteMicrodistrict(microdistrictId: UUID): Job {
        Timber.tag(TAG).d("deleteMicrodistrict(...) called: microdistrictId = %s", microdistrictId)
        val job = viewModelScope.launch(errorHandler) {
            microdistrictUseCases.deleteMicrodistrictUseCase.execute(
                DeleteMicrodistrictUseCase.Request(microdistrictId)
            ).collect {}
        }
        return job
    }

    private fun deleteStreetMicrodistrict(streetId: UUID, microdistrictId: UUID): Job {
        Timber.tag(TAG).d(
            "deleteStreetMicrodistrict(...) called: streetId = %s; microdistrictId = %s",
            streetId, microdistrictId
        )
        val job = viewModelScope.launch(errorHandler) {
            streetUseCases.deleteStreetMicrodistrictUseCase.execute(
                DeleteStreetMicrodistrictUseCase.Request(
                    streetId = streetId, microdistrictId = microdistrictId
                )
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<MicrodistrictsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : MicrodistrictsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val selectedItemFlow = MutableStateFlow(ListItemModel())
                override val singleEventFlow =
                    Channel<MicrodistrictsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()
                override val uiStateErrorMsg = MutableStateFlow("")

                override fun redirectedErrorMessage() = null
                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}
                override fun clearSearchText() {}

                override val areSingleSelected = MutableStateFlow(false)
                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun singleSelectedItem() = MutableStateFlow(null)

                override fun handleActionJob(
                    action: () -> Unit,
                    afterAction: (CoroutineScope) -> Unit
                ) {
                }

                override fun submitAction(action: MicrodistrictsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            MicrodistrictsListItem(
                id = UUID.randomUUID(),
                microdistrictShortName = ctx.resources.getString(R.string.def_cvetochny_short_name),
                microdistrictFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.village_types)[VillageType.MICRO_DISTRICT.ordinal]} ${
                    ctx.resources.getString(R.string.def_cvetochny_name)
                }"
            ),
            MicrodistrictsListItem(
                id = UUID.randomUUID(),
                microdistrictShortName = ctx.resources.getString(R.string.def_don_short_name),
                microdistrictFullName = "${ctx.resources.getStringArray(com.oborodulin.jwsuite.domain.R.array.village_types)[VillageType.MICRO_DISTRICT.ordinal]} ${
                    ctx.resources.getString(R.string.def_don_name)
                }"
            )
        )
    }
}