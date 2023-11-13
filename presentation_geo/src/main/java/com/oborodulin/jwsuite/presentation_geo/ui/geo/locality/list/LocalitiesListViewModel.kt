package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalitiesListItem

interface LocalitiesListViewModel :
    ListViewModeled<List<LocalitiesListItem>, LocalitiesListUiAction, LocalitiesListUiSingleEvent>