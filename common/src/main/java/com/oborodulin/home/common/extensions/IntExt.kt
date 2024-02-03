package com.oborodulin.home.common.extensions

fun Int?.isEven() = this?.let { it % 2 == 0 } ?: false

fun Int?.isOdd() = this?.let { it % 2 != 0 } ?: false