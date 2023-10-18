package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem

interface MicrodistrictsListViewModel :
    ListViewModeled<List<MicrodistrictsListItem>, MicrodistrictsListUiAction, MicrodistrictsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}