package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem

interface StreetsListViewModel :
    ListViewModeled<List<StreetsListItem>, StreetsListUiAction, StreetsListUiSingleEvent>