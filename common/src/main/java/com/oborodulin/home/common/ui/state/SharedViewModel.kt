package com.oborodulin.home.common.ui.state

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * https://stackoverflow.com/questions/70262850/sending-data-between-fragments-using-shared-viewmodel-and-sharedflow
 */
private const val TAG = "Common.SingleViewModel"

abstract class SharedViewModel<T : Any?> : ViewModel() {
    private val _sharedFlow = MutableSharedFlow<T>(replay = 2) // , extraBufferCapacity = 1
    val sharedFlow = _sharedFlow.asSharedFlow()//.distinctUntilChanged()

    var fabOnClick = mutableStateOf({})

    fun submitData(data: T) {
        viewModelScope.launch {
            Timber.tag(TAG).d("submitData(): data = %s", data)
            _sharedFlow.emit(data)
        }
    }
}