package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalitiesListUiAction : UiAction {
    data class Load(val regionId: UUID, val regionDistrictId: UUID? = null) :
        LocalitiesListUiAction()

   // data class FilteredLoad(val search: String) : LocalitiesListUiAction()
    data class EditLocality(val localityId: UUID) : LocalitiesListUiAction()
    data class DeleteLocality(val localityId: UUID) : LocalitiesListUiAction()
}