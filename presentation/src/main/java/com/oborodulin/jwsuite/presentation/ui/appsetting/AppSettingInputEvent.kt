package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class AppSettingInputEvent(val value: String) : Inputable {
    data class DatabaseBackupPeriod(val input: String) : AppSettingInputEvent(input)

    override fun value() = this.value
}
