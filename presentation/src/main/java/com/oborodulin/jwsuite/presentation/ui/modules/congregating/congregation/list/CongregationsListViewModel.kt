package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface CongregationsListViewModel :
    MviViewModeled<List<CongregationsListItem>, CongregationsListUiAction, CongregationsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}