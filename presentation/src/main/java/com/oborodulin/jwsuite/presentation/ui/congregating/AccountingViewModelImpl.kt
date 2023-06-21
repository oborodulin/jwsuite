package com.oborodulin.home.accounting.ui

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.accounting.domain.usecases.CongregatingUseCases
import com.oborodulin.home.domain.usecases.GetFavoritePayerUseCase
import com.oborodulin.home.accounting.ui.model.AccountingUi
import com.oborodulin.home.accounting.ui.model.PayerUi
import com.oborodulin.home.accounting.ui.model.converters.FavoritePayerConverter
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.data.R
import com.oborodulin.home.data.local.db.HomeDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

private const val TAG = "Accounting.ui.AccountingViewModel"

@HiltViewModel
class AccountingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val congregatingUseCases: CongregatingUseCases,
    private val payerConverter: FavoritePayerConverter
) : AccountingViewModel,
    MviViewModel<AccountingUi, UiState<AccountingUi>, AccountingUiAction, AccountingUiSingleEvent>(
        state = state
    ) {
    override fun initState(): UiState<AccountingUi> = UiState.Loading

    override suspend fun handleAction(action: AccountingUiAction): Job {
        Timber.tag(TAG)
            .d(
                "handleAction(AccountingUiAction) called: %s [HomeDatabase.isImportExecute = %s]",
                action.javaClass.name,
                HomeDatabase.isImportExecute
            )
        if (HomeDatabase.isImportExecute) HomeDatabase.isImportDone?.await()
        Timber.tag(TAG)
            .d(
                "await(): HomeDatabase.isImportExecute = %s; HomeDatabase.isImportDone = %s",
                HomeDatabase.isImportExecute,
                HomeDatabase.isImportDone
            )
        val job = when (action) {
            is AccountingUiAction.Init -> loadFavoritePayer()
        }
        return job
    }

    private fun loadFavoritePayer(): Job {
        Timber.tag(TAG).d("loadFavoritePayer() called")
        val job = viewModelScope.launch(errorHandler) {
            congregatingUseCases.getFavoritePayerUseCase.execute(GetFavoritePayerUseCase.Request)
                .map {
                    payerConverter.convert(it)
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
            object : AccountingViewModel {
                override val uiStateFlow =
                    MutableStateFlow(
                        UiState.Success(AccountingUi(favoritePayer = previewPayerModel(ctx)))
                    )
                override val singleEventFlow = Channel<AccountingUiSingleEvent>().receiveAsFlow()

                override fun submitAction(action: AccountingUiAction): Job? {
                    return null
                }
            }

        fun previewPayerModel(ctx: Context) =
            PayerUi(
                id = UUID.randomUUID(),
                fullName = ctx.resources.getString(R.string.def_payer1_full_name),
                address = ctx.resources.getString(R.string.def_payer1_address),
                totalArea = BigDecimal("61"),
                livingSpace = BigDecimal("59"),
                paymentDay = 20,
                personsNum = 2,
                isFavorite = true,
            )
    }
}