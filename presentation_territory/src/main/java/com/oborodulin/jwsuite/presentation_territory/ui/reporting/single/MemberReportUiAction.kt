package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberReportUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryMemberReportId: UUID? = null) : MemberReportUiAction()
    data object Save : MemberReportUiAction()
}