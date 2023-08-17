package com.oborodulin.jwsuite.presentation.ui.modules.geo.street.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class StreetsListUiAction : UiAction {
    data class Load(
        val localityId: UUID? = null,
        val localityDistrictId: UUID? = null,
        val microdistrictId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : StreetsListUiAction()

    data class EditStreet(val streetId: UUID) : StreetsListUiAction()
    data class DeleteStreet(val streetId: UUID) : StreetsListUiAction()
}