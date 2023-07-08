package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.data.R
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.domain.usecases.DashboardingUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.DashboardingUi
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.converters.FavoriteCongregationConverter
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Dashboarding.ui.DashboardingViewModelImpl"

@HiltViewModel
class DashboardingViewModelImpl @Inject constructor(
    private val state: SavedStateHandle,
    private val dashboardingUseCases: DashboardingUseCases,
    private val congregationConverter: FavoriteCongregationConverter
) : DashboardingViewModel,
    MviViewModel<DashboardingUi, UiState<DashboardingUi>, DashboardingUiAction, DashboardingUiSingleEvent>(
        state = state
    ) {
    override fun initState(): UiState<DashboardingUi> = UiState.Loading

    override suspend fun handleAction(action: DashboardingUiAction): Job {
        Timber.tag(TAG)
            .d(
                "handleAction(DashboardingUiAction) called: %s [JwSuiteDatabase.isImportExecute = %s]",
                action.javaClass.name,
                JwSuiteDatabase.isImportExecute
            )
        if (JwSuiteDatabase.isImportExecute) JwSuiteDatabase.isImportDone?.await()
        Timber.tag(TAG)
            .d(
                "await(): JwSuiteDatabase.isImportExecute = %s; JwSuiteDatabase.isImportDone = %s",
                JwSuiteDatabase.isImportExecute,
                JwSuiteDatabase.isImportDone
            )
        val job = when (action) {
            is DashboardingUiAction.Init -> loadFavoriteCongregation()
        }
        return job
    }

    private fun loadFavoriteCongregation(): Job {
        Timber.tag(TAG).d("loadFavoriteCongregation() called")
        val job = viewModelScope.launch(errorHandler) {
            dashboardingUseCases.getFavoriteCongregationUseCase.execute(
                GetFavoriteCongregationUseCase.Request
            ).map {
                congregationConverter.convert(it)
            }.collect {
                submitState(it)
            }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: Any): Job? = null

    companion object {
        fun previewModel(ctx: Context) =
            object : DashboardingViewModel {
                override val uiStateFlow =
                    MutableStateFlow(
                        UiState.Success(
                            DashboardingUi(favoriteCongregation = previewCongregationModel(ctx))
                        )
                    )
                override val singleEventFlow = Channel<DashboardingUiSingleEvent>().receiveAsFlow()

                override fun submitAction(action: DashboardingUiAction): Job? {
                    return null
                }
            }

        fun previewCongregationModel(ctx: Context): CongregationUi {
            val congregationUi = CongregationUi(
                congregationNum = ctx.resources.getString(R.string.def_congregation1_num),
                congregationName = ctx.resources.getString(R.string.def_congregation1_name),
                territoryMark = ctx.resources.getString(R.string.def_congregation1_card_mark),
                locality = LocalityViewModelImpl.previewUiModel(ctx),
                isFavorite = true
            )
            congregationUi.id = UUID.randomUUID()
            return congregationUi
        }
    }
}