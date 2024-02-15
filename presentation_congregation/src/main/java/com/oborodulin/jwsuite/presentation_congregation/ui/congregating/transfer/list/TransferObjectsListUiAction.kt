package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.transfer.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class TransferObjectsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val memberId: UUID? = null) : TransferObjectsListUiAction()
}