package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalityDistrictsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val localityId: UUID? = null, val streetId: UUID? = null) :
        LocalityDistrictsListUiAction()

    data class EditLocalityDistrict(val localityDistrictId: UUID) : LocalityDistrictsListUiAction()
    data class DeleteLocalityDistrict(val localityDistrictId: UUID) :
        LocalityDistrictsListUiAction()
    data class DeleteStreetLocalityDistrict(val streetId: UUID, val localityDistrictId: UUID) :
        LocalityDistrictsListUiAction()
}