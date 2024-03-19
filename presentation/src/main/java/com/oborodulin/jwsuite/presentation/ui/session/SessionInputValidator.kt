package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.util.Constants.PASS_MIN_LENGTH

private const val TAG = "Presentation.SignupInputValidator"

sealed class SessionInputValidator : Validatable {
    data object Username : SessionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.signup_username_empty_error
                //etc..
                else -> null
            }
    }

    // https://stackoverflow.com/questions/3656371/is-it-possible-to-have-placeholders-in-strings-xml-for-runtime-values
    data object Pin : SessionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.pin_empty_error
                inputs[0]?.let { it.length < PASS_MIN_LENGTH }
                    ?: true -> R.string.signup_pin_length_error

                else -> null
            }
    }

    data object ConfirmPin : SessionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.signup_confirm_pin_empty_error
                inputs.size != 2 || inputs[1].isNullOrBlank() -> R.string.signup_confirm_pin_ctrl_error
                inputs[0].orEmpty() != inputs[1].orEmpty().substring(
                    0,
                    minOf(inputs[0].orEmpty().length, inputs[1].orEmpty().length)
                ) -> R.string.signup_confirm_pin_error

                else -> null
            }
    }
}
