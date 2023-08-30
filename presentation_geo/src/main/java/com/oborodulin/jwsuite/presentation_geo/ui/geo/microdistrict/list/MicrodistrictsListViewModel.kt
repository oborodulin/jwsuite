package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_geo.model.MicrodistrictsListItem

interface MicrodistrictsListViewModel :
    MviViewModeled<List<MicrodistrictsListItem>, MicrodistrictsListUiAction, MicrodistrictsListUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}