package com.oborodulin.home.common.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//create an extension function on a date class which returns a string
fun Date.dateToString(format: String): String {
    //simple date formatter
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

    //return the formatted date string
    return dateFormatter.format(this)
}