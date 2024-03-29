package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.ListViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.usecases.congregation.CongregationUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.DeleteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.MakeFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.converters.CongregationsListConverter
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
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

private const val TAG = "Congregating.CongregationsListViewModelImpl"

@HiltViewModel
class CongregationsListViewModelImpl @Inject constructor(
    private val congregationUseCases: CongregationUseCases,
    private val congregationsListConverter: CongregationsListConverter
) : CongregationsListViewModel,
    ListViewModel<List<CongregationsListItem>, UiState<List<CongregationsListItem>>, CongregationsListUiAction, CongregationsListUiSingleEvent>() {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: CongregationsListUiAction): Job {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d("handleAction(CongregationsListUiAction) called: %s", action.javaClass.name)
        }
        val job = when (action) {
            is CongregationsListUiAction.Load -> loadCongregations()

            is CongregationsListUiAction.EditCongregation -> {
                submitSingleEvent(
                    CongregationsListUiSingleEvent.OpenCongregationScreen(
                        NavRoutes.Congregation.routeForCongregation(
                            CongregationInput(action.congregationId)
                        )
                    )
                )
            }

            is CongregationsListUiAction.DeleteCongregation -> {
                deleteCongregation(action.congregationId)
            }

            is CongregationsListUiAction.MakeFavoriteCongregation -> {
                makeFavoriteCongregation(action.congregationId)
            }
        }
        return job
    }

    private fun loadCongregations(): Job {
        Timber.tag(TAG).d("loadCongregations() called")
        val job = viewModelScope.launch(errorHandler) {
            congregationUseCases.getCongregationsUseCase.execute(GetCongregationsUseCase.Request)
                .map {
                    congregationsListConverter.convert(it)
                }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deleteCongregation(congregationId: UUID): Job {
        Timber.tag(TAG)
            .d("deleteCongregation() called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            congregationUseCases.deleteCongregationUseCase.execute(
                DeleteCongregationUseCase.Request(congregationId)
            ).collect {}
        }
        return job
    }

    private fun makeFavoriteCongregation(congregationId: UUID): Job {
        Timber.tag(TAG)
            .d("makeFavoriteCongregation() called: congregationId = %s", congregationId)
        val job = viewModelScope.launch(errorHandler) {
            congregationUseCases.makeFavoriteCongregationUseCase.execute(
                MakeFavoriteCongregationUseCase.Request(congregationId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: List<CongregationsListItem>): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : CongregationsListViewModel {
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val selectedItemFlow = MutableStateFlow(ListItemModel())
                override val singleEventFlow =
                    Channel<CongregationsListUiSingleEvent>().receiveAsFlow()
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

                override fun submitAction(action: CongregationsListUiAction): Job? = null
            }

        fun previewList(ctx: Context) = listOf(
            CongregationsListItem(
                id = UUID.randomUUID(),
                congregationNum = ctx.resources.getString(R.string.def_congregation1_num),
                congregationName = ctx.resources.getString(R.string.def_congregation1_name),
                territoryMark = ctx.resources.getString(R.string.def_congregation1_card_mark),
                locality = LocalityUi(),
                isFavorite = true
            ),
            CongregationsListItem(
                id = UUID.randomUUID(),
                congregationNum = ctx.resources.getString(R.string.def_congregation2_num),
                congregationName = ctx.resources.getString(R.string.def_congregation2_name),
                territoryMark = ctx.resources.getString(R.string.def_congregation2_card_mark),
                locality = LocalityUi()
            )
        )
    }
}