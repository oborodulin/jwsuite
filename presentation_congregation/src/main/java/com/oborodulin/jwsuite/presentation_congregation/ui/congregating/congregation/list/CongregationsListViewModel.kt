package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem

interface CongregationsListViewModel :
    ListViewModeled<List<CongregationsListItem>, CongregationsListUiAction, CongregationsListUiSingleEvent>