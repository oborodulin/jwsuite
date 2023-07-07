package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.accounting.domain.usecases.CongregatingUseCases
import com.oborodulin.home.domain.usecases.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregatingUi
import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.congregating.model.converters.FavoriteCongregationConverter
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.data.R
import com.oborodulin.home.data.local.db.HomeDatabase
import com.oborodulin.jwsuite.domain.usecases.CongregatingUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregatingUi
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.converters.FavoriteCongregationConverter
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

private const val TAG = "Congregating.ui.CongregatingViewModelImpl"

@HiltViewModel
class CongregatingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val congregatingUseCases: CongregatingUseCases,
    private val payerConverter: FavoriteCongregationConverter
) : CongregatingViewModel,
    MviViewModel<CongregatingUi, UiState<CongregatingUi>, CongregatingUiAction, CongregatingUiSingleEvent>(
        state = state
    ) {
    override fun initState(): UiState<CongregatingUi> = UiState.Loading

    override suspend fun handleAction(action: CongregatingUiAction): Job {
        val job = when (action) {
            is CongregatingUiAction.Init -> loadFavoriteCongregation()
        }
        return job
    }

    private fun loadFavoriteCongregation(): Job {
        Timber.tag(TAG).d("loadFavoriteCongregation() called")
        val job = viewModelScope.launch(errorHandler) {
            congregatingUseCases.getFavoriteCongregationUseCase.execute(
                GetFavoriteCongregationUseCase.Request)
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
            object : CongregatingViewModel {
                override val uiStateFlow =
                    MutableStateFlow(
                        UiState.Success(CongregatingUi(favoriteCongregation = previewCongregationModel(ctx)))
                    )
                override val singleEventFlow = Channel<CongregatingUiSingleEvent>().receiveAsFlow()

                override fun submitAction(action: CongregatingUiAction): Job? {
                    return null
                }
            }

        fun previewCongregationModel(ctx: Context) =
            CongregationUi(
                id = UUID.randomUUID(),
                congregationName = ctx.resources.getString(R.string.def_payer1_full_name),
                territoryMark = ctx.resources.getString(R.string.def_payer1_address),
                totalArea = BigDecimal("61"),
                livingSpace = BigDecimal("59"),
                paymentDay = 20,
                personsNum = 2,
                isFavorite = true,
            )
    }
}