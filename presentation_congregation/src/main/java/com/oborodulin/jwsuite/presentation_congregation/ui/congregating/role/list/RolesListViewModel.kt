package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.role.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem

interface RolesListViewModel :
    ListViewModeled<List<RolesListItem>, RolesListUiAction, UiSingleEvent>