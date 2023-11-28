package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list

import com.oborodulin.home.common.ui.state.ListViewModeled
import com.oborodulin.jwsuite.presentation.ui.model.MemberRolesListItem

interface MemberRolesListViewModel :
    ListViewModeled<List<MemberRolesListItem>, MemberRolesListUiAction, MemberRolesListUiSingleEvent>