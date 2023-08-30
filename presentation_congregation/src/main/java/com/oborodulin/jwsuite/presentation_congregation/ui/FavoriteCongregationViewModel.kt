package com.oborodulin.jwsuite.presentation_congregation.ui

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

interface FavoriteCongregationViewModel<T : Any?> {
    val sharedFlow: StateFlow<T>
    fun submitData(data: T): Job?
    fun sharedData(): T?
}