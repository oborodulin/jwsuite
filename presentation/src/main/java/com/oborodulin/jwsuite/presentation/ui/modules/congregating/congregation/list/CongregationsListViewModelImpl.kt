package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.Utils
import com.oborodulin.jwsuite.domain.usecases.congregation.CongregationUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.DeleteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationsUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.MakeFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.jwsuite.presentation.ui.congregating.model.converters.CongregationsListConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

private const val TAG = "Congregating.ui.CongregationsListViewModel"

@HiltViewModel
class CongregationsListViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val congregationUseCases: CongregationUseCases,
    private val congregationsListConverter: CongregationsListConverter
) : CongregationsListViewModel,
    MviViewModel<List<CongregationListItem>, UiState<List<CongregationListItem>>, CongregationsListUiAction, CongregationsListUiSingleEvent>(
        state = state
    ) {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: CongregationsListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(CongregationsListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is CongregationsListUiAction.Load -> {
                loadCongregations()
            }

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

            is CongregationsListUiAction.FavoriteCongregation -> {
                makeFavoriteCongregation(action.congregationId)
            }
            /*is PostListUiAction.UserClick -> {
                updateInteraction(action.interaction)
                submitSingleEvent(
                    PostListUiSingleEvent.OpenUserScreen(
                        NavRoutes.User.routeForUser(
                            UserInput(action.userId)
                        )
                    )
                )
            }*/
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

    private fun deleteCongregation(payerId: UUID): Job {
        Timber.tag(TAG).d("deleteCongregation() called: payerId = %s", payerId.toString())
        val job = viewModelScope.launch(errorHandler) {
            congregationUseCases.deleteCongregationUseCase.execute(
                DeleteCongregationUseCase.Request(payerId)
            ).collect {}
        }
        return job
    }

    private fun makeFavoriteCongregation(payerId: UUID): Job {
        Timber.tag(TAG).d("makeFavoriteCongregation() called: payerId = %s", payerId.toString())
        val job = viewModelScope.launch(errorHandler) {
            congregationUseCases.makeFavoriteCongregationUseCase.execute(
                MakeFavoriteCongregationUseCase.Request(payerId)
            ).collect {}
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : CongregationsListViewModel {
                override var primaryObjectData: StateFlow<ArrayList<String>> =
                    MutableStateFlow(arrayListOf())
                override val uiStateFlow = MutableStateFlow(UiState.Success(previewList(ctx)))
                override val singleEventFlow =
                    Channel<CongregationsListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                //fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: CongregationsListUiAction): Job? = null
                override fun setPrimaryObjectData(value: ArrayList<String>) {}
            }

        fun previewList(ctx: Context) = listOf(
            CongregationListItem(
                id = UUID.randomUUID(),
                fullName = ctx.resources.getString(com.oborodulin.home.data.R.string.def_payer1_full_name),
                address = ctx.resources.getString(com.oborodulin.home.data.R.string.def_payer1_address),
                totalArea = BigDecimal("61"),
                livingSpace = BigDecimal("59"),
                paymentDay = 20,
                personsNum = 2,
                isFavorite = true,
                fromPaymentDate = Utils.toOffsetDateTime("2022-08-01T14:29:10.212+03:00"),
                toPaymentDate = Utils.toOffsetDateTime("2022-09-01T14:29:10.212+03:00"),
                totalDebt = BigDecimal("123456.78")
            ),
            CongregationListItem(
                id = UUID.randomUUID(),
                fullName = ctx.resources.getString(com.oborodulin.home.data.R.string.def_payer2_full_name),
                address = ctx.resources.getString(com.oborodulin.home.data.R.string.def_payer2_address),
                totalArea = BigDecimal("89"),
                livingSpace = BigDecimal("76"),
                paymentDay = 20,
                personsNum = 1,
                fromPaymentDate = Utils.toOffsetDateTime("2022-08-01T14:29:10.212+03:00"),
                toPaymentDate = Utils.toOffsetDateTime("2022-09-01T14:29:10.212+03:00"),
                totalDebt = BigDecimal("876543.21")
            )
        )
    }
}