package com.oborodulin.jwsuite.presentation.ui.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.AppSettingParam
import java.util.UUID

data class AppSettingsListItem(
    val id: UUID,
    val paramName: AppSettingParam,
    val paramValue: String = "",
    val paramFullName: String = ""
) : Parcelable, ListItemModel(itemId = id, headline = paramFullName, supportingText = paramValue)
