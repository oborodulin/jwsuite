package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val TAG = "Congregating.GroupInputValidator"

sealed class MemberRoleInputValidator : Validatable {
    data object Member : MemberRoleInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_role_member_empty_error
                else -> null
            }
    }

    data object Role : MemberRoleInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_role_role_empty_error
                else -> null
            }
    }

    data object RoleExpiredDate : MemberRoleInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? {
            if (!inputs[0].isNullOrEmpty()) {
                val expiredDate = LocalDate.parse(
                    inputs[0],
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                ).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()
                return when {
                    expiredDate.toLocalDate().isBefore(
                        OffsetDateTime.now().toLocalDate()
                    ) -> R.string.member_role_expired_date_less_then_now_error
                    //etc..
                    else -> null
                }
            }
            return null
        }
    }
}
