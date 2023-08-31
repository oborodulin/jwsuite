package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalitiesListItem

interface LocalitiesListViewModel :
    MviViewModeled<List<LocalitiesListItem>, com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction, com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiSingleEvent> {

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}