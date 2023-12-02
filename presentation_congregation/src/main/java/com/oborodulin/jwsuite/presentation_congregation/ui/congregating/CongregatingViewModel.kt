package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregatingUi
import kotlinx.coroutines.flow.StateFlow

interface CongregatingViewModel :
    SingleViewModeled<CongregatingUi, CongregatingUiAction, CongregatingUiSingleEvent, CongregatingFields> {
    val isService: StateFlow<InputWrapper>
}