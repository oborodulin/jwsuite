package com.oborodulin.jwsuite.presentation_dashboard.ui.database

import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.jwsuite.domain.types.MemberRoleType

sealed class DatabaseUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Init : DatabaseUiAction()
    data object Backup : DatabaseUiAction()
    data object Restore : DatabaseUiAction()
    data class Send(val memberRoleType: MemberRoleType? = null) : DatabaseUiAction()
    data object Receive : DatabaseUiAction()
}