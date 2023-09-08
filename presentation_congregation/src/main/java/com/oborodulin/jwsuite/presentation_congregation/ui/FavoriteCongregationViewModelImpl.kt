package com.oborodulin.jwsuite.presentation_congregation.ui

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteCongregationViewModelImpl @Inject constructor() :
    SharedViewModeled<ListItemModel?>, SharedViewModel<ListItemModel?>() {
    companion object {
        val previewModel = object : SharedViewModeled<ListItemModel?> {
            override val sharedFlow: StateFlow<ListItemModel?> = MutableStateFlow(null)
            override fun submitData(data: ListItemModel?) = null
            override fun sharedData(): ListItemModel? = null
        }
    }
}
