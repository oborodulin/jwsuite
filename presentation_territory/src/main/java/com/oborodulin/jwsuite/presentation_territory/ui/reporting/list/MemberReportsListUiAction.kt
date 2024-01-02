package com.oborodulin.jwsuite.presentation_territory.ui.reporting.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberReportsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(
        val territoryStreetId: UUID? = null,
        val houseId: UUID? = null,
        val roomId: UUID? = null
    ) : MemberReportsListUiAction()

    data class EditMemberReport(val territoryMemberReportId: UUID) : MemberReportsListUiAction()
    data class DeleteMemberReport(val territoryMemberReportId: UUID) : MemberReportsListUiAction()
}