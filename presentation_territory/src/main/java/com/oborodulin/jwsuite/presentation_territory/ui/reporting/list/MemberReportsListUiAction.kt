package com.oborodulin.jwsuite.presentation_territory.ui.reporting.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MemberReportsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID) : MemberReportsListUiAction()
    data class EditTerritoryStreet(val territoryId: UUID, val territoryStreetId: UUID) :
        MemberReportsListUiAction()

    data class DeleteTerritoryStreet(val territoryStreetId: UUID) : MemberReportsListUiAction()
}