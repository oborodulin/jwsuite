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
    val memberShortName: String = "",
    val territoryStreet: TerritoryStreet? = null,
    val house: House? = null,
    val room: Room? = null,
    val territoryMemberId: UUID,
    val territoryReportMark: TerritoryReportMark = TerritoryReportMark.PP,
    val languageCode: String? = null,
    val gender: Boolean? = null,
    val age: Int? = null,
    val isProcessed: Boolean = false,
    val territoryReportDesc: String? = null
) : DomainModel() {
    val territoryMark =
        ctx?.let { it.resources.getStringArray(R.array.territory_marks)[territoryReportMark.ordinal] }
            .orEmpty()
    val territoryShortMark =
        ctx?.let { it.resources.getStringArray(R.array.territory_short_marks)[territoryReportMark.ordinal] }
            .orEmpty()
    val genderInfo = gender?.let {
        when (gender) {
            true -> ctx?.resources?.getString(R.string.male_expr).orEmpty()
            else -> ctx?.resources?.getString(R.string.female_expr).orEmpty()
        }
    }
    val ageInfo = age?.let {
        "($it ${ctx?.resources?.getString(R.string.age_expr).orEmpty()})"
    }
    val languageInfo = languageCode?.let{"[$it]"}
    val personInfo = listOfNotNull(genderInfo, ageInfo).joinToString(" ")
    val info = listOfNotNull(territoryMark, genderInfo, ageInfo)
}
