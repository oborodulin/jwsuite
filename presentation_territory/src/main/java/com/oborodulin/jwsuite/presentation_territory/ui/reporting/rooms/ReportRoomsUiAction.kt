package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class ReportRoomsUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID, val houseId: UUID? = null) : ReportRoomsUiAction()
    data class EditMemberReport(val territoryMemberReportId: UUID) : ReportRoomsUiAction()
    data class DeleteMemberReport(val territoryMemberReportId: UUID) : ReportRoomsUiAction()
}