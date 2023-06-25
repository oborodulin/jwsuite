package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.domain.util.LocalityType
import java.util.UUID

data class LocalityListItem(
    val id: UUID,
    val localityCode: String,
    val localityType: LocalityType,
    val localityShortName: String,
    val localityName: String
) : ListItemModel(
    itemId = id,
    headline = localityName,
    supportingText = "$localityCode: $localityShortName"
)
