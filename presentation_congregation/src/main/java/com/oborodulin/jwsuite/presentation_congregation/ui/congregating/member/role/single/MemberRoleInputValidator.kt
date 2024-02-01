package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.home.common.extensions.toFullFormatOffsetDateTime
import com.oborodulin.jwsuite.presentation_congregation.R
import java.time.OffsetDateTime

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
            inputs[0]?.let {
                if (it.isNotEmpty()) {
                    val expiredDate = it.toFullFormatOffsetDateTime()
                    return when {
                        expiredDate.toLocalDate().isBefore(
                            OffsetDateTime.now().toLocalDate()
                        ) -> R.string.member_role_expired_date_less_then_now_error
                        //etc..
                        else -> null
                    }
                }
            }
            return null
        }
    }
}
