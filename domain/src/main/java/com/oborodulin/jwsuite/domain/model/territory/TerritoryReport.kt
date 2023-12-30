package com.oborodulin.jwsuite.domain.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.TerritoryMemberMark
import java.util.UUID

data class TerritoryReport(
    val ctx: Context? = null,
    val territoryMemberId: UUID,
    val territoryMemberMark: TerritoryMemberMark? = null,
    val languageCode: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val isProcessed: Boolean = false,
    val territoryReportDesc: String? = null
) : DomainModel() {
    val territoryMark = territoryMemberMark?.let { mark ->
        ctx?.let { it.resources.getStringArray(R.array.territory_marks)[mark.ordinal] }
            .orEmpty()
    }
    val genderInfo = gender?.let {
        when (gender) {
            true -> ctx?.resources?.getString(R.string.male_expr).orEmpty()
            else -> ctx?.resources?.getString(R.string.female_expr).orEmpty()
        }
    }
    val ageInfo = age?.let {
        "($it ${ctx?.resources?.getString(R.string.age_expr).orEmpty()})"
    }
    val info = listOfNotNull(territoryMark, genderInfo, ageInfo)
}
