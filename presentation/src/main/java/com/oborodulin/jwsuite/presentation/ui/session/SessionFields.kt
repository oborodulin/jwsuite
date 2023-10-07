package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class SessionFields : Focusable {
    SESSION_USERNAME,
    SESSION_PASSWORD,
    SESSION_CONFIRM_PASSWORD;

    override fun key(): String {
        return this.name
    }
}
