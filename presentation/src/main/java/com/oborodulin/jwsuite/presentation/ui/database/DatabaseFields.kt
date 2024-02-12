package com.oborodulin.jwsuite.presentation.ui.database

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class DatabaseFields : Focusable {
    DATABASE_TRANSFER_MEMBER;

    override fun key() = this.name
}
