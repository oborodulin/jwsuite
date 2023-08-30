package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalitiesListUiAction : UiAction {
    data class Load(val regionId: UUID? = null, val regionDistrictId: UUID? = null) :
        com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction()

    // data class FilteredLoad(val search: String) : LocalitiesListUiAction()
    data class EditLocality(val localityId: UUID) : com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction()
    data class DeleteLocality(val localityId: UUID) : com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.list.LocalitiesListUiAction()
}