package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.domain.usecases.geolocality.DeleteLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetAllLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.LocalityUseCases
import com.oborodulin.jwsuite.domain.util.LocalityType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.ui.model.LocalitiesListItem
import com.oborodulin.jwsuite.presentation.ui.model.converters.AllLocalitiesListConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.LocalitiesListConverter
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

private const val TAG = "Geo.ui.LocalitiesListViewModelImpl"

@HiltViewModel
class LocalitiesListViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val useCases: LocalityUseCases,
    private val localitiesConverter: LocalitiesListConverter,
    private val allLocalitiesConverter: AllLocalitiesListConverter
) : LocalitiesListViewModel,
    MviViewModel<List<LocalitiesListItem>, UiState<List<LocalitiesListItem>>, LocalitiesListUiAction, LocalitiesListUiSingleEvent>(
        state = state
    ) {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: LocalitiesListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(LocalitiesListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is LocalitiesListUiAction.LoadAll -> {
                loadAllLocalities()
            }

            is LocalitiesListUiAction.Load -> {
                loadLocalities(action.regionId, action.regionDistrictId)
            }

            /*          is LocalitiesListUiAction.FilteredLoad -> {
                          loadFilteredLocalities(action.search)
                      }*/

            is LocalitiesListUiAction.EditLocality -> {
                submitSingleEvent(
                    LocalitiesListUiSingleEvent.OpenLocalityScreen(
                        NavRoutes.Locality.routeForLocality(LocalityInput(action.localityId))
                    )
                )
            }

            is LocalitiesListUiAction.DeleteLocality -> {
                deleteLocality(action.localityId)
            }
        }
        return job
    }

    private fun loadAllLocalities(): Job {
        Timber.tag(TAG).d("loadAllLocalities() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getAllLocalitiesUseCase.execute(GetAllLocalitiesUseCase.Request).map {
                allLocalitiesConverter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    private fun loadLocalities(regionId: UUID, regionDistrictId: UUID? = null): Job {
        Timber.tag(TAG).d("loadLocalities() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getLocalitiesUseCase.execute(
                GetLocalitiesUseCase.Request(regionId, regionDistrictId)
            ).map {
                localitiesConverter.convert(it)
            }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    /*
        private fun loadFilteredLocalities(search: String): Job {
            Timber.tag(TAG).d("loadFilteredLocalities() called")
            val job = viewModelScope.launch(errorHandler) {
                useCases.getLocalitiesUseCase.execute(
                    GetLocalitiesUseCase.Request(regionId, regionDistrictId)
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
    private fun deleteLocality(localityId: UUID): Job {
        Timber.tag(TAG).d("deleteLocality() called: localityId = %s", localityId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteLocalityUseCase.execute(
                DeleteLocalityUseCase.Request(localityId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : LocalitiesListViewModel {
                override var primaryObjectData: StateFlow<ArrayList<String>> =
                    MutableStateFlow(arrayListOf())
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<LocalitiesListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                //fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: LocalitiesListUiAction): Job? = null
                override fun setPrimaryObjectData(value: ArrayList<String>) {}
            }

        fun previewList(ctx: Context) = listOf(
            LocalitiesListItem(
                id = UUID.randomUUID(),
                localityCode = ctx.resources.getString(R.string.def_donetsk_code),
                localityType = LocalityType.CITY,
                localityShortName = ctx.resources.getString(R.string.def_donetsk_short_name),
                localityName = ctx.resources.getString(R.string.def_donetsk_name)
            ),
            LocalitiesListItem(
                id = UUID.randomUUID(),
                localityCode = ctx.resources.getString(R.string.def_luhansk_code),
                localityType = LocalityType.CITY,
                localityShortName = ctx.resources.getString(R.string.def_luhansk_short_name),
                localityName = ctx.resources.getString(R.string.def_luhansk_name)
            )
        )
    }
}