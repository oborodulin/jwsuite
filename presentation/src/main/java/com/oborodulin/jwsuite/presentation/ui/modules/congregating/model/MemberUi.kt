package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.MemberType
import java.time.OffsetDateTime

data class MemberUi(
    val group: GroupUi,
    val memberNum: String,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    val memberType: MemberType = MemberType.PREACHER,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val inactiveDate: OffsetDateTime? = null
) : ModelUi()