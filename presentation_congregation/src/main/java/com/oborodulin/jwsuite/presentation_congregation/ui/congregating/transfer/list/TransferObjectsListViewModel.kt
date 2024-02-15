package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.transfer.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem

interface TransferObjectsListViewModel :
    ListViewModeled<List<RolesListItem>, TransferObjectsListUiAction, UiSingleEvent>