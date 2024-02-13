package com.oborodulin.jwsuite.presentation_dashboard.ui.database

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class DatabaseFields : Focusable {
    RECEIVER_MEMBER;

    override fun key() = this.name
}