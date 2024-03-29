package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class SessionFields : Focusable {
    SESSION_USERNAME,
    SESSION_PIN,
    SESSION_CONFIRM_PIN;

    override fun key() = this.name
}
