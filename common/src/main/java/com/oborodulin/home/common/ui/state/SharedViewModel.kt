package com.oborodulin.home.common.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_SHARED
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * https://stackoverflow.com/questions/70262850/sending-data-between-fragments-using-shared-viewmodel-and-sharedflow
 */
private const val TAG = "Common.SharedViewModel"

abstract class SharedViewModel<T : Any?> : SharedViewModeled<T>, ViewModel() {
    //: MutableSharedFlow<T> =MutableSharedFlow(replay = 1) //MutableSharedFlow<T>(replay = 2) // , extraBufferCapacity = 1
    private val _sharedFlow: MutableStateFlow<T?> by lazy { MutableStateFlow(null) }
    override val sharedFlow = _sharedFlow.asStateFlow() //.asSharedFlow()//.distinctUntilChanged()

    override fun submitData(data: T): Job {
        return viewModelScope.launch {
            if (LOG_MVI_SHARED) {
                Timber.tag(TAG).d("submitData(): data = %s", data)
            }
            //_sharedFlow.emit(data)
            _sharedFlow.value = data
        }
    }

    override fun sharedData(): T? {
        var data: T? = null
        viewModelScope.launch {
            sharedFlow.collectLatest { data = it }
            if (LOG_MVI_SHARED) {
                Timber.tag(TAG).d("sharedData(): data = %s", data)
            }
        }
        return data
    }
}