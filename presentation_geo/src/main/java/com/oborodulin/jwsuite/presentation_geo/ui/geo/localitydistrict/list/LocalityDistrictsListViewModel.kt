package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_geo.model.LocalityDistrictsListItem

interface LocalityDistrictsListViewModel :
    MviViewModeled<List<LocalityDistrictsListItem>, LocalityDistrictsListUiAction, LocalityDistrictsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}