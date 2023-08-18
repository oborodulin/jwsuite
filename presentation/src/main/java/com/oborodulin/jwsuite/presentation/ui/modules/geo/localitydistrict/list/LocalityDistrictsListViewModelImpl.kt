package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data_geo.R
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.LocalityDistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.DeleteLocalityDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetLocalityDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.LocalityDistrictUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters.LocalityDistrictsListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters.LocalityDistrictsListConverter
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

private const val TAG = "Geo.LocalityDistrictsListViewModelImpl"

@HiltViewModel
class LocalityDistrictsListViewModelImpl @Inject constructor(
    private val useCases: LocalityDistrictUseCases,
    private val converter: LocalityDistrictsListConverter
) : LocalityDistrictsListViewModel,
    MviViewModel<List<LocalityDistrictsListItem>, UiState<List<LocalityDistrictsListItem>>, LocalityDistrictsListUiAction, LocalityDistrictsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: LocalityDistrictsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(LocalityDistrictsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is LocalityDistrictsListUiAction.Load -> loadLocalityDistricts(action.localityId)

            is LocalityDistrictsListUiAction.EditLocalityDistrict -> {
                submitSingleEvent(
                    LocalityDistrictsListUiSingleEvent.OpenLocalityDistrictScreen(
                        NavRoutes.LocalityDistrict.routeForLocalityDistrict(
                            LocalityDistrictInput(action.localityDistrictId)
                        )
                    )
                )
            }

            is LocalityDistrictsListUiAction.DeleteLocalityDistrict -> {
                deleteLocalityDistrict(action.localityDistrictId)
            }
        }
        return job
    }

    private fun loadLocalityDistricts(regionId: UUID? = null): Job {
        Timber.tag(TAG).d("loadLocalityDistricts() called: regionId = %s", regionId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getLocalityDistrictsUseCase.execute(GetLocalityDistrictsUseCase.Request(regionId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteLocalityDistrict(regionDistrictId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteLocalityDistrict() called: regionDistrictId = %s", regionDistrictId.toString())
        val job = viewModelScope.launch(errorHandler) {
            useCases.deleteLocalityDistrictUseCase.execute(
                DeleteLocalityDistrictUseCase.Request(regionDistrictId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : LocalityDistrictsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<LocalityDistrictsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: LocalityDistrictsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            LocalityDistrictsListItem(
                id = UUID.randomUUID(),
                districtShortName = ctx.resources.getString(R.string.def_reg_donetsky_short_name),
                districtName = ctx.resources.getString(R.string.def_reg_donetsky_name)
            ),
            LocalityDistrictsListItem(
                id = UUID.randomUUID(),
                districtShortName = ctx.resources.getString(R.string.def_reg_maryinsky_short_name),
                districtName = ctx.resources.getString(R.string.def_reg_maryinsky_name)
            )
        )
    }
}