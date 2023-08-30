package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem

interface CongregationsListViewModel :
    MviViewModeled<List<CongregationsListItem>, CongregationsListUiAction, CongregationsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}