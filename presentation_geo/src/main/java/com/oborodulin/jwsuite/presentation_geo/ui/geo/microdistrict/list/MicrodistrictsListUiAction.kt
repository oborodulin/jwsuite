package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MicrodistrictsListUiAction : UiAction {
    data class Load(val localityId: UUID? = null, val localityDistrictId: UUID? = null) :
        MicrodistrictsListUiAction()

    // data class FilteredLoad(val search: String) : LocalitiesListUiAction()
    data class EditMicrodistrict(val microdistrictId: UUID) : MicrodistrictsListUiAction()
    data class DeleteMicrodistrict(val microdistrictId: UUID) : MicrodistrictsListUiAction()
}