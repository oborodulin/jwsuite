package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryDetailsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryDetailsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryDetailsListConverter
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

private const val TAG = "Territoring.TerritoryDetailsViewModelImpl"

@HiltViewModel
class TerritoryDetailsViewModelImpl @Inject constructor(
    private val useCases: TerritoryUseCases,
    private val converter: TerritoryDetailsListConverter
) : TerritoryDetailsViewModel,
    MviViewModel<List<TerritoryDetailsListItem>, UiState<List<TerritoryDetailsListItem>>, TerritoryDetailsUiAction, UiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: TerritoryDetailsUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(MembersListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is TerritoryDetailsUiAction.Load -> {
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

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : TerritoryDetailsViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow = Channel<UiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                override val searchText = MutableStateFlow(TextFieldValue(""))
                override val isSearching = MutableStateFlow(false)
                override fun onSearchTextChange(text: TextFieldValue) {}

                override fun singleSelectItem(selectedItem: ListItemModel) {}
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: TerritoryDetailsUiAction): Job? = null
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