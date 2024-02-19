package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.state.MviViewModel
import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.home.common.util.LogLevel.LOG_FLOW_ACTION
import com.oborodulin.jwsuite.domain.usecases.DashboardingUseCases
import com.oborodulin.jwsuite.domain.usecases.dashboard.GetDashboardInfoUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.CongregationTotalsUi
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardingUi
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.TerritoryTotalsUi
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DashboardingConverter
import com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single.LocalityViewModelImpl
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
    private val useCases: DashboardingUseCases,
    private val converter: DashboardingConverter
) : DashboardingViewModel,
    MviViewModel<DashboardingUi, UiState<DashboardingUi>, DashboardingUiAction, DashboardingUiSingleEvent>() {
    override fun initState() = UiState.Loading

    override suspend fun handleAction(action: DashboardingUiAction): Job {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d("handleAction(DashboardingUiAction) called: %s ", action.javaClass.name)
        /*
        if (JwSuiteDatabase.isImportExecute) JwSuiteDatabase.isImportDone?.await()
        Timber.tag(TAG)
            .d(
                "await(): JwSuiteDatabase.isImportExecute = %s; JwSuiteDatabase.isImportDone = %s",
                JwSuiteDatabase.isImportExecute,
                JwSuiteDatabase.isImportDone
            )
         */
        val job = when (action) {
            is DashboardingUiAction.Init -> loadDashboard()
        }
        return job
    }

    private fun loadDashboard(): Job {
        Timber.tag(TAG).d("loadDashboard() called")
        val job = viewModelScope.launch(errorHandler) {
            useCases.getDashboardInfoUseCase.execute(GetDashboardInfoUseCase.Request)
                .map {
                    converter.convert(it)
                }.collect {
                    submitState(it)
                }
        }
        return job
    }

    override fun initFieldStatesByUiModel(uiModel: DashboardingUi): Job? = null

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

        fun previewCongregationModel(ctx: Context) = CongregationUi(
            congregationNum = "12",// ctx.resources.getString(R.string.def_congregation1_num),
            congregationName = "",//ctx.resources.getString(R.string.def_congregation1_name),
            territoryMark = "К",//ctx.resources.getString(R.string.def_congregation1_card_mark),
            locality = LocalityViewModelImpl.previewUiModel(ctx),
            isFavorite = true
        ).also { it.id = UUID.randomUUID() }

        fun previewCongregationTotalsModel(ctx: Context) = CongregationTotalsUi(
            congregation = previewCongregationModel(ctx),
            totalGroups = 7,
            totalMembers = 124,
            totalFulltimeMembers = 28,
            diffGroups = 1,
            diffMembers = 0,
            diffFulltimeMembers = 3
        ).also { it.id = UUID.randomUUID() }

        fun previewTerritoryTotalsModel(ctx: Context) = TerritoryTotalsUi(
            congregation = previewCongregationModel(ctx),
            totalTerritories = 175,
            totalTerritoryIssued = 59,
            totalTerritoryProcessed = 99,
            diffTerritories = 9,
            diffTerritoryIssued = 16,
            diffTerritoryProcessed = 11
        ).also { it.id = UUID.randomUUID() }
    }
}