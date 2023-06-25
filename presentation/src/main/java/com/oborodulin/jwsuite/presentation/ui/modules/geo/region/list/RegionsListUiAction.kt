package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class RegionsListUiAction : UiAction {
    object Load : RegionsListUiAction()
    data class EditPayer(val payerId: UUID) : RegionsListUiAction()
    data class DeletePayer(val payerId: UUID) : RegionsListUiAction()
    data class FavoritePayer(val payerId: UUID) : RegionsListUiAction()
}