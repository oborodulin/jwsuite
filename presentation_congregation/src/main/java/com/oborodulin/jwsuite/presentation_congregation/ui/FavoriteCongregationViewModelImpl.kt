package com.oborodulin.jwsuite.presentation_congregation.ui

import androidx.lifecycle.SavedStateHandle
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val SHARED_ITEM_ID_KEY = "SHARED_CONGREGATION_ID"
private const val SHARED_HEAD_LINE_KEY = "SHARED_CONGREGATION_NAME"

@HiltViewModel
class FavoriteCongregationViewModelImpl @Inject constructor(private val state: SavedStateHandle) :
    SharedViewModeled<ListItemModel?>, SharedViewModel<ListItemModel?>() {
    companion object {
        val previewModel = object : SharedViewModeled<ListItemModel?> {
            override val sharedFlow: StateFlow<ListItemModel?> = MutableStateFlow(null)
            override fun submitData(data: ListItemModel?) = null
            override fun sharedData(): ListItemModel? = null
        }
    }
}
