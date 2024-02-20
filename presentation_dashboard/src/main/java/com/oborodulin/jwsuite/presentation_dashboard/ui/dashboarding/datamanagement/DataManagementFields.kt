package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class DataManagementFields : Focusable {
    DATABASE_BACKUP_PERIOD;

    override fun key() = this.name
}
