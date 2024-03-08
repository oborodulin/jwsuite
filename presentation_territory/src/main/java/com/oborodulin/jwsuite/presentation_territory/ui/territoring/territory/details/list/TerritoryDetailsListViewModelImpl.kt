package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryDetailsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryDetailsListConverter
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

private const val TAG = "Territoring.TerritoryDetailsListViewModelImpl"

@HiltViewModel
class TerritoryDetailsListViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryDetailsListConverter
) : TerritoryDetailsListViewModel,
    ListViewModel<List<TerritoryDetailsListItem>, UiState<List<TerritoryDetailsListItem>>, TerritoryDetailsListUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryDetailsListUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(TerritoryDetailsListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is TerritoryDetailsListUiAction.Load -> {
                loadTerritoryDetails(territoryId = action.territoryId)
            }
        }
        return job
    }

    private fun loadTerritoryDetails(territoryId: UUID): Job {
        Timber.tag(TAG).d("loadTerritoryDetails() called: territoryId = %s", territoryId)
        val job = viewModelScope.launch(errorHandler) {
            useCases.getTerritoryDetailsUseCase.execute(
                GetTerritoryDetailsUseCase.Request(territoryId = territoryId)
            )
                .map {
                    converter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<TerritoryDetailsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryDetailsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
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

                override fun submitAction(action: TerritoryDetailsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            TerritoryDetailsListItem(
                territoryStreetId = UUID.randomUUID(),
                streetId = UUID.randomUUID(),
                streetInfo = "ул. Собинова",
                housesInfo = "д. 129, 131 - не жилой (80 кв.)"
            ),
            TerritoryDetailsListItem(
                territoryStreetId = UUID.randomUUID(),
                streetId = UUID.randomUUID(),
                streetInfo = "пр-т Киевский",
                entrancesInfo = "д. 5 (III-VI подъезды, 96 кв.)",
            ),
            TerritoryDetailsListItem(
                territoryStreetId = UUID.randomUUID(),
                streetId = UUID.randomUUID(),
                streetInfo = "ул. Листопрокатчиков",
                roomsInfo = "д. 14, кв. 1"
            )
        )
    }
}