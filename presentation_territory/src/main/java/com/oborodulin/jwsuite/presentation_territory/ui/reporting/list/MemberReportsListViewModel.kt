package com.oborodulin.jwsuite.presentation_territory.ui.reporting.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetsListItem

interface MemberReportsListViewModel :
    ListViewModeled<List<TerritoryMemberReportsListItem>, MemberReportsListUiAction, UiSingleEvent>