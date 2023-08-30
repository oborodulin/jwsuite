package com.oborodulin.jwsuite.presentation_congregation.ui

import com.oborodulin.home.common.ui.state.SharedViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteCongregationViewModelImpl @Inject constructor() :
    FavoriteCongregationViewModel<CongregationsListItem?>,
    SharedViewModel<CongregationsListItem?>() {
    companion object {
        val previewModel = object : FavoriteCongregationViewModel<CongregationsListItem?> {
            override val sharedFlow: StateFlow<CongregationsListItem?> = MutableStateFlow(null)
            override fun submitData(data: CongregationsListItem?) = null
            override fun sharedData(): CongregationsListItem? = null
        }
    }
}
