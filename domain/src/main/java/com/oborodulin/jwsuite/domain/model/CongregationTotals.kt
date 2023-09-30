package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel

data class CongregationTotals(
    val congregation: Congregation,
    val totalMembers: Int,
    val prevMemberTotals: Int,
    val totalFulltimeMembers: Int,
    val prevFulltimeMemberTotals: Int
) : DomainModel() {
    val diffMemberTotals = totalMembers - prevMemberTotals
    val diffFulltimeMemberTotals = totalFulltimeMembers - prevFulltimeMemberTotals
}
