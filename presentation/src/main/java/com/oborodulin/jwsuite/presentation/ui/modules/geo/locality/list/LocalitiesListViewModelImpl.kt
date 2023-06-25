package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.jwsuite.presentation.ui.congregating.model.converters.CongregationsListConverter
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.Utils
import com.oborodulin.home.domain.usecases.DeletePayerUseCase
import com.oborodulin.home.domain.usecases.FavoritePayerUseCase
import com.oborodulin.home.accounting.domain.usecases.GetPayersUseCase
import com.oborodulin.home.accounting.domain.usecases.PayerUseCases
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

private const val TAG = "Accounting.ui.PayersListViewModel"

@HiltViewModel
class LocalitiesListViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val payerUseCases: PayerUseCases,
    private val congregationsListConverter: CongregationsListConverter
) : LocalitiesListViewModel,
    MviViewModel<List<CongregationListItem>, UiState<List<CongregationListItem>>, LocalitiesListUiAction, LocalitiesListUiSingleEvent>(
        state = state
    ) {

    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: LocalitiesListUiAction): Job {
        Timber.tag(TAG)
            .d("handleAction(PayersListUiAction) called: %s", action.javaClass.name)
        val job = when (action) {
            is LocalitiesListUiAction.Load -> {
                loadPayers()
            }
            is LocalitiesListUiAction.EditPayer -> {
                submitSingleEvent(
                    LocalitiesListUiSingleEvent.OpenPayerScreen(
                        NavRoutes.Congregation.routeForCongregation(
                            CongregationInput(action.payerId)
                        )
                    )
                )
            }
            is LocalitiesListUiAction.DeletePayer -> {
                deletePayer(action.payerId)
            }
            is LocalitiesListUiAction.FavoritePayer -> {
                favoritePayer(action.payerId)
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

    private fun loadPayers(): Job {
        Timber.tag(TAG).d("loadPayers() called")
        val job = viewModelScope.launch(errorHandler) {
            payerUseCases.getPayersUseCase.execute(GetPayersUseCase.Request).map {
                congregationsListConverter.convert(it)
            }
                .collect {
                    submitState(it)
                }
        }
        return job
    }

    private fun deletePayer(payerId: UUID): Job {
        Timber.tag(TAG).d("deletePayer() called: payerId = %s", payerId.toString())
        val job = viewModelScope.launch(errorHandler) {
            payerUseCases.deletePayerUseCase.execute(
                DeletePayerUseCase.Request(payerId)
            ).collect {}
        }
        return job
    }

    private fun favoritePayer(payerId: UUID): Job {
        Timber.tag(TAG).d("favoritePayer() called: payerId = %s", payerId.toString())
        val job = viewModelScope.launch(errorHandler) {
            payerUseCases.favoritePayerUseCase.execute(
                FavoritePayerUseCase.Request(payerId)
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
                override val singleEventFlow = Channel<LocalitiesListUiSingleEvent>().receiveAsFlow()
                override val actionsJobFlow: SharedFlow<Job?> = MutableSharedFlow()

                //fun viewModelScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)
                override fun handleActionJob(action: () -> Unit, afterAction: () -> Unit) {}
                override fun submitAction(action: LocalitiesListUiAction): Job? = null
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