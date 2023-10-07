package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH

private const val TAG = "Presentation.SignupInputValidator"

sealed class SessionInputValidator : Validatable {
    data object Username : SessionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.signup_username_empty_error
                //etc..
                else -> null
            }
    }

    // https://stackoverflow.com/questions/3656371/is-it-possible-to-have-placeholders-in-strings-xml-for-runtime-values
    data object Password : SessionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.password_empty_error
                inputs[0]?.let { it.length <= PASS_MIN_LENGTH }
                    ?: true -> R.string.signup_password_length_error

                else -> null
            }
    }

    data object ConfirmPassword : SessionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.signup_confirm_password_empty_error
                inputs[0]?.let { it != inputs[0].orEmpty() }
                    ?: true -> R.string.signup_confirm_password_error

                else -> null
            }
    }
}
