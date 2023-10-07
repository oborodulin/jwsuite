package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel

data class Group(
    val congregation: Congregation,
    val groupNum: Int,
    val members: List<Member> = emptyList()
) : DomainModel()
