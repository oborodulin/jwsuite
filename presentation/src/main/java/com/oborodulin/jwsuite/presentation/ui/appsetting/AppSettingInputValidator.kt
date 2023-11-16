package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class AppSettingInputValidator : Validatable {
    data object Congregation : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
        when {
            inputs[0].isNullOrEmpty() -> R.string.member_congregation_empty_error
            else -> null
        }
    }
    data object Group : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_group_empty_error
            else -> null
        }*/
    }

    data object AppSettingNum : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_num_empty_error
            //etc..
            else -> null
        }*/
    }

    data object Pseudonym : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_pseudonym_empty_error
                //etc..
                else -> null
            }
    }

    data object PhoneNumber : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*    when {
                !inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
                //etc..
                else -> null
            }
         */
    }

    data object AppSettingType : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object DateOfBirth : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object DateOfBaptism : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object LoginExpiredDate : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object MovementDate : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_movement_date_empty_error
                //etc..
                else -> null
            }
    }
}
