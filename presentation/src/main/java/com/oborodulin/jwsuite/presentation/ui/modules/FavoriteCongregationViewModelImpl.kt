package com.oborodulin.jwsuite.presentation.ui.modules

import com.oborodulin.home.common.ui.state.SharedViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteCongregationViewModelImpl @Inject constructor() :
    FavoriteCongregationViewModel<CongregationsListItem>, SharedViewModel<CongregationsListItem>() {
    companion object {
        val previewModel = object : FavoriteCongregationViewModel<CongregationsListItem> {
            override val sharedFlow: SharedFlow<CongregationsListItem> = MutableSharedFlow()
            override fun submitData(data: CongregationsListItem) {}
        }
    }
}
