package com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.list

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.MicrodistrictsListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface MicrodistrictsListViewModel :
    MviViewModeled<List<MicrodistrictsListItem>, MicrodistrictsListUiAction, MicrodistrictsListUiSingleEvent> {
    val actionsJobFlow: SharedFlow<Job?>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}