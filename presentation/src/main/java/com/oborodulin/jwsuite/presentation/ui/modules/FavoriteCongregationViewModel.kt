package com.oborodulin.jwsuite.presentation.ui.modules

import kotlinx.coroutines.flow.SharedFlow

interface FavoriteCongregationViewModel<T : Any> {
    val sharedFlow: SharedFlow<T>
    fun sendData(data: T)
}