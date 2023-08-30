package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class StreetsListUiAction : UiAction {
    data class Load(
        val localityId: UUID? = null,
        val localityDistrictId: UUID? = null,
        val microdistrictId: UUID? = null,
        val isPrivateSector: Boolean = false
    ) : StreetsListUiAction()

    data class LoadForTerritory(
        val localityId: UUID,
        val localityDistrictId: UUID? = null,
        val microdistrictId: UUID? = null,
        val excludes: List<UUID> = emptyList()
    ) : StreetsListUiAction()

    data class EditStreet(val streetId: UUID) : StreetsListUiAction()
    data class DeleteStreet(val streetId: UUID) : StreetsListUiAction()
}