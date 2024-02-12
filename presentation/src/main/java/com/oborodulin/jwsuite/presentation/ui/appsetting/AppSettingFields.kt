package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class AppSettingFields : Focusable {
    DATABASE_BACKUP_PERIOD;

    override fun key() = this.name
}
