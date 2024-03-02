package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.home.common.extensions.firstCapitalLetterOrEmpty
import com.oborodulin.home.common.extensions.ifNotEmpty
import java.time.OffsetDateTime

data class Member(
    val congregation: Congregation? = null,
    val group: Group? = null,
    val memberNum: String? = null,
    val memberName: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val pseudonym: String,
    val phoneNumber: String? = null,
    val dateOfBirth: OffsetDateTime? = null,
    val dateOfBaptism: OffsetDateTime? = null,
    val loginExpiredDate: OffsetDateTime? = null,
    val lastCongregation: MemberCongregation? = null,
    val lastMovement: MemberMovement? = null
) : DomainModel() {
    val fullNum = fullNum(group?.groupNum, memberNum)
    val fullName =
        "${surname.orEmpty()} ${memberName.orEmpty()} ${patronymic.orEmpty()} [$pseudonym]"
            .replace(spacesPattern, " ").trim()
    val shortName = ("$surname ${
        memberName.firstCapitalLetterOrEmpty().ifNotEmpty { "$it." }
    }${patronymic.firstCapitalLetterOrEmpty().ifNotEmpty { "$it." }}"
        .plus(" [$pseudonym]")).replace(spacesPattern, " ").trim()

    companion object {
        // https://stackoverflow.com/questions/37070352/how-do-i-replace-duplicate-whitespaces-in-a-string-in-kotlin
        val spacesPattern = "\\s+".toRegex()

        fun fullNum(groupNum: Int?, memberNum: String?) =
            groupNum?.let { gn -> "$gn${memberNum.ifNotEmpty { mn -> ".$mn" }}" }.orEmpty()

        fun pseudonym(
            surname: String?, memberName: String?, groupNum: Int?, memberNum: String?
        ) = "${surname.firstCapitalLetterOrEmpty()}${memberName.firstCapitalLetterOrEmpty()}${
            fullNum(groupNum, memberNum)
        }"
    }
}
