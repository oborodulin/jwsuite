package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class Territory(
    val congregation: Congregation,
    val territoryCategory: TerritoryCategory,
    val locality: GeoLocality,
    val localityDistrictId: UUID? = null,
    val districtShortName: String?,
    val microdistrictId: UUID? = null,
    val microdistrictShortName: String?,
    val territoryNum: Int,
    val isBusiness: Boolean = false,
    val isGroupMinistry: Boolean = false,
    val isInPerimeter: Boolean = false,
    val isProcessed: Boolean = false,
    val isActive: Boolean = true,
    val territoryDesc: String? = null,
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
            .plus(if (!districtShortName.isNullOrEmpty()) "$districtShortName:" else "")
            .plus(if (!microdistrictShortName.isNullOrEmpty()) "$microdistrictShortName]" else "]")
            .replace("[]", "")
            .replace(":]", "]")

    // https://www.sanfoundry.com/java-program-convert-given-number-days-terms-years-weeks-days/
    // https://stackoverflow.com/questions/29791881/how-to-convert-number-days-to-years-months-days
    val handOutYears = handOutTotalDays?.let { it / 365 }
    val handOutMonths = handOutTotalDays?.let { it % 365 / 30 }
    val handOutDays = handOutTotalDays?.let { it % 365 % 30 }
    val expiredYears = expiredTotalDays?.let { it / 365 }
    val expiredMonths = expiredTotalDays?.let { it % 365 / 30 }
    val expiredDays = expiredTotalDays?.let { it % 365 % 30 }
}
