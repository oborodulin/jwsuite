package com.oborodulin.home.common.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * https://stackoverflow.com/questions/70262850/sending-data-between-fragments-using-shared-viewmodel-and-sharedflow
 */

abstract class SharedViewModel<T : Any> : ViewModel() {
    private val _sharedFlow = MutableSharedFlow<T>(replay = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun sendData(data: T) {
        viewModelScope.launch {
            _sharedFlow.emit(data)
        }
    }
}