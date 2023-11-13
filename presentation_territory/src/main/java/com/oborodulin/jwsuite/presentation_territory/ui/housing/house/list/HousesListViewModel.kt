package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

interface HousesListViewModel :
    ListViewModeled<List<HousesListItem>, HousesListUiAction, UiSingleEvent>