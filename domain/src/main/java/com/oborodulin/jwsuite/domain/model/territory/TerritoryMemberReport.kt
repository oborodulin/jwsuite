package com.oborodulin.jwsuite.domain.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.R
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import java.time.OffsetDateTime
import java.util.UUID

data class TerritoryMemberReport(
    val ctx: Context? = null,
    val deliveryDate: OffsetDateTime? = null,
    val territoryStreet: TerritoryStreet? = null,
    val house: House? = null,
    val room: Room? = null,
    val territoryMemberId: UUID,
    val territoryReportMark: TerritoryReportMark? = null,
    val languageCode: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val isProcessed: Boolean? = null,
    val territoryReportDesc: String? = null
) : DomainModel() {
    val territoryMark = territoryReportMark?.let { mark ->
        ctx?.let { it.resources.getStringArray(R.array.territory_marks)[mark.ordinal] }
            .orEmpty()
    }
    val territoryShortMark = territoryReportMark?.let { mark ->
        ctx?.let { it.resources.getStringArray(R.array.territory_short_marks)[mark.ordinal] }
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
