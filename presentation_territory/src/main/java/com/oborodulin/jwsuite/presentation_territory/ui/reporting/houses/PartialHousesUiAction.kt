package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class PartialHousesUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID, val territoryStreetId: UUID? = null) :
        PartialHousesUiAction()

    data class EditMemberReport(val territoryMemberReportId: UUID) : PartialHousesUiAction()
    data class DeleteMemberReport(val territoryMemberReportId: UUID) : PartialHousesUiAction()
}