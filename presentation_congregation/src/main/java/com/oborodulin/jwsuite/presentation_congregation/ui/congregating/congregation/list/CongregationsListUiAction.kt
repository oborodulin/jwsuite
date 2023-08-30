package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class CongregationsListUiAction : UiAction {
    object Load : CongregationsListUiAction()
    data class EditCongregation(val congregationId: UUID) : CongregationsListUiAction()
    data class DeleteCongregation(val congregationId: UUID) : CongregationsListUiAction()
    data class MakeFavoriteCongregation(val congregationId: UUID) : CongregationsListUiAction()
}