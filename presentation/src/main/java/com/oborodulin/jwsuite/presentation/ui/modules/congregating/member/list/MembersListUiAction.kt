package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class MembersListUiAction : UiAction {
    object Load : MembersListUiAction()
    data class EditPayer(val payerId: UUID) : MembersListUiAction()
    data class DeletePayer(val payerId: UUID) : MembersListUiAction()
    data class FavoritePayer(val payerId: UUID) : MembersListUiAction()
}