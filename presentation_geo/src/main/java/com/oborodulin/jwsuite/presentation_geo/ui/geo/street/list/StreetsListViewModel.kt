package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_geo.model.StreetsListItem

interface StreetsListViewModel :
    MviViewModeled<List<StreetsListItem>, StreetsListUiAction, StreetsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}