package com.oborodulin.jwsuite.domain.model.territory

import android.content.Context
import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.domain.model.congregation.Member
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import java.util.UUID

data class Territory(
    val ctx: Context? = null,
    val congregation: Congregation,
    val territoryCategory: TerritoryCategory,
    val locality: GeoLocality,
    val localityDistrict: GeoLocalityDistrict?,
    val microdistrict: GeoMicrodistrict?,
    val territoryNum: Int,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val isActive: Boolean = true,
    val territoryDesc: String? = null,
    val territoryStreets: List<TerritoryStreet> = emptyList(),
    var streetNames: String? = null,
    var houseNums: String? = null,
    val member: Member? = null,
    val congregationId: UUID? = null,
    val isPrivateSector: Boolean? = null,
    val handOutTotalDays: Int? = null,
    val expiredTotalDays: Int? = null,
    val territoryBusinessMark: String? = null
) : DomainModel() {
    val cardNum =
        "${congregation.territoryMark}${territoryCategory.territoryCategoryMark}-$territoryNum".plus(
            if (isBusiness) "-$territoryBusinessMark" else ""
        )
    val cardLocation =
        "[".plus(if (locality.id != congregation.locality.id) "${locality.localityShortName}:" else "")
            .plus(localityDistrict?.let { "${it.districtShortName}:" } ?: "")
            .plus(microdistrict?.let { "${it.microdistrictShortName}]" } ?: "]")
            .replace("[]", "")
            .replace(":]", "]")
    val fullCardNum = "$cardNum $cardLocation".trim()

    // https://www.sanfoundry.com/java-program-convert-given-number-days-terms-years-weeks-days/
    // https://stackoverflow.com/questions/29791881/how-to-convert-number-days-to-years-months-days
    val handOutDaysPeriod = handOutTotalDays?.let { totalDays ->
        val handOutYears = totalDays / 365
        val handOutMonths = totalDays % 365 / 30
        val handOutDays = totalDays % 365 % 30
        ((if (handOutYears > 0) "$handOutYears ${
            ctx?.resources?.getString(com.oborodulin.home.common.R.string.year_unit).orEmpty()
        } " else "")
            .plus(
                if (handOutMonths > 0) "$handOutMonths ${
                    ctx?.resources?.getString(com.oborodulin.home.common.R.string.month_unit)
                        .orEmpty()
                } " else ""
            )
            .plus(
                if (handOutDays > 0) "$handOutDays ${
                    ctx?.resources?.getString(com.oborodulin.home.common.R.string.day_unit)
                        .orEmpty()
                }" else ""
            ))
            .trim()
    }
    val expiredDaysPeriod = expiredTotalDays?.let { totalDays ->
        val expiredYears = totalDays / 365
        val expiredMonths = totalDays % 365 / 30
        val expiredDays = totalDays % 365 % 30
        ((if (expiredYears > 0) "$expiredYears ${
            ctx?.resources?.getString(com.oborodulin.home.common.R.string.year_unit).orEmpty()
        } " else "")
            .plus(
                if (expiredMonths > 0) "$expiredMonths ${
                    ctx?.resources?.getString(com.oborodulin.home.common.R.string.month_unit)
                        .orEmpty()
                } " else ""
            )
            .plus(
                if (expiredDays > 0) "$expiredDays ${
                    ctx?.resources?.getString(com.oborodulin.home.common.R.string.day_unit)
                        .orEmpty()
                }" else ""
            ))
            .trim()
    }
}
