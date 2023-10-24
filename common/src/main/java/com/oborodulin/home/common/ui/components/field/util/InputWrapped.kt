package com.oborodulin.home.common.ui.components.field.util

import android.content.Context

interface InputWrapped {
    val errorId: Int?
    val errorMsg: String?

    fun errorMessage(ctx: Context): String? {
        return errorMsg ?: errorId?.let { ctx.resources.getString(it) }
    }
}