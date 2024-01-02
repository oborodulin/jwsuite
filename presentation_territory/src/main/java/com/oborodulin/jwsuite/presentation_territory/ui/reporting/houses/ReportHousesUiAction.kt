package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class ReportHousesUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID, val territoryStreetId: UUID? = null) :
        ReportHousesUiAction()

    data class EditMemberReport(val territoryMemberReportId: UUID) : ReportHousesUiAction()
    data class DeleteMemberReport(val territoryMemberReportId: UUID) : ReportHousesUiAction()
    data class ProcessReport(val territoryMemberReportId: UUID) : ReportHousesUiAction()
    data class CancelProcessReport(val territoryMemberReportId: UUID) : ReportHousesUiAction()
}