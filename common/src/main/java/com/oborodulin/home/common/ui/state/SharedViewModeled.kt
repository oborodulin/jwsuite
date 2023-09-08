package com.oborodulin.home.common.ui.state

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

interface SharedViewModeled<T : Any?> {
    val sharedFlow: StateFlow<T?>
    fun submitData(data: T): Job?
    fun sharedData(): T?
}