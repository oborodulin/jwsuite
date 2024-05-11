package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegionsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(
        val countryId: UUID? = null,
        val countryGeocodeArea: String = "",
        val countryCode: String = "",
        val isRemoteFetch: Boolean = false
    ) : RegionsListUiAction()

    data class EditRegion(val regionId: UUID) : RegionsListUiAction()
    data class DeleteRegion(val regionId: UUID) : RegionsListUiAction()
}