package com.oborodulin.home.common.util

object Constants {
    const val LANGUAGE_RU = "ru"
    const val LANGUAGE_EN = "en"

    val EMPTY_LIST_ITEM_EVENT: OnListItemEvent = {}
    val EMPTY_BLOCK: () -> Unit = {}

    // Application constants:
    const val CONV_COEFF_BIGDECIMAL: Long = 10000000
    const val APP_DAY_DATE_TIME = "yyyy-MM-dd"
    const val APP_OFFSET_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSxxx"
}