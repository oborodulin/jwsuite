package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregatingUi
import com.oborodulin.home.common.ui.state.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CongregatingViewModel {
    val uiStateFlow: StateFlow<UiState<CongregatingUi>>
    val singleEventFlow : Flow<CongregatingUiSingleEvent>
//    val uiMeterValuesState: MutableState<List<MeterValueListItem>>

    fun submitAction(action: CongregatingUiAction): Job?
}