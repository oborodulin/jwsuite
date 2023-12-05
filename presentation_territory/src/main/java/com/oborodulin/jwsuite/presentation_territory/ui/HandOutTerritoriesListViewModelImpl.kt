package com.oborodulin.jwsuite.presentation_territory.ui

import androidx.lifecycle.SavedStateHandle
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HandOutTerritoriesListViewModelImpl @Inject constructor(private val state: SavedStateHandle) :
    SharedViewModeled<List<ListItemModel>?>, SharedViewModel<List<ListItemModel>?>() {
    companion object {
        val previewModel = object : SharedViewModeled<List<ListItemModel>?> {
            override val sharedFlow: StateFlow<List<ListItemModel>?> = MutableStateFlow(null)
            override fun submitData(data: List<ListItemModel>?) = null
            override fun sharedData(): List<ListItemModel>? = null
        }
    }
}