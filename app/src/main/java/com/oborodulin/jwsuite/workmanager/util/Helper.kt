package com.oborodulin.jwsuite.workmanager.util

fun Double.is5PercentUp(): Boolean {
    return this >= 5
}

fun Double.is5PercentDown(): Boolean {
    return this <= -5
}
