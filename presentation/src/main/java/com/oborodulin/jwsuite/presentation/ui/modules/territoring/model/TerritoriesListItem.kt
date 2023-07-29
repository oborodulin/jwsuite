package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import android.os.Parcelable
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi
import java.util.UUID

data class TerritoriesListItem(
    val id: UUID,
    val congregation: CongregationUi,
    val territoryCategory: TerritoryCategoryUi,
    val locality: LocalityUi,
    val cardNum: String,
    val cardLocation: String,
    val territoryNum: Int,
    val isBusiness: Boolean,
    val isGroupMinistry: Boolean,
    val isInPerimeter: Boolean,
    val isProcessed: Boolean,
    val isActive: Boolean,
    val territoryDesc: String? = null,
    val member: MemberUi? = null,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null,
    val handOutDays: Int? = null,
    val expiredDays: Int? = null,
    var isChecked: Boolean = false,
    var isSelected: Boolean = false
) : Parcelable,
    ListItemModel(itemId = id, headline = "$cardNum $cardLocation", supportingText = territoryDesc)
