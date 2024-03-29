package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem

interface MembersListViewModel :
    ListViewModeled<List<MembersListItem>, MembersListUiAction, UiSingleEvent>