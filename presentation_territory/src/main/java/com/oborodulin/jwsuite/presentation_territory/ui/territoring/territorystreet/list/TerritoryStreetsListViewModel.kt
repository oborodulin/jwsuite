package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetsListItem

interface TerritoryStreetsListViewModel :
    ListViewModeled<List<TerritoryStreetsListItem>, TerritoryStreetsListUiAction, UiSingleEvent>