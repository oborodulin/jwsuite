package com.oborodulin.home.common.util

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.oborodulin.home.common.R
import java.math.BigDecimal
import java.util.regex.Pattern

/**
 *
 *
 * This class is used to validate data like email, phone, password policy, etc.
 * It also sets error to the EditText or TextInputLayout of the EditText if needed.
 */
class Validator(private val context: Context) {

    fun isNotEmpty(
        data: View,
        s: CharSequence?,
        @StringRes resId: Int,
        updateUI: Boolean = true
    ): Boolean {
        val valid = s?.toString()?.trim()?.isNotEmpty() ?: false
        if (updateUI) {
            val error: String? = if (valid) null else context.resources.getString(resId)
            setError(data, error)
        }
        return valid
    }

    fun isEmptyOrDecimal(
        data: View,
        s: CharSequence?,
        updateUI: Boolean = true
    ): Boolean {
        val valid = (s == null || s.toString().trim().isEmpty()) ||
                (s.matches(Regex("^(\\d+)(?:\\.(\\d{1,3}))?\$")) &&
                        BigDecimal(s.toString()).compareTo(BigDecimal(0)) == 1)
        if (updateUI) {
            val error: String? =
                if (valid) null else context.resources.getString(R.string.number_negative_error)
            setError(data, error)
        }
        return valid
    }

    fun isEmptyOrNumber(
        data: View,
        s: CharSequence?,
        updateUI: Boolean = true
    ): Boolean {
        val valid = (s == null || s.toString().trim().isEmpty()) ||
                (s.matches(Regex("^\\d+\$")) &&
                        s.toString().toInt() > 0)
        if (updateUI) {
            val error: String? =
                if (valid) null else context.resources.getString(R.string.number_negative_error)
            setError(data, error)
        }
        return valid
    }

    companion object {

        // Default validation messages
        private val PASSWORD_POLICY = """Password should be minimum 8 characters long,
            |should contain at least one capital letter,
            |at least one small letter,
            |at least one number and
            |at least one special character among ~!@#$%^&*()-_=+|[]{};:'\",<.>/?""".trimMargin()

        private const val NAME_VALIDATION_MSG = "Enter a valid name"
        private const val EMAIL_VALIDATION_MSG = "Enter a valid email address"
        private const val PHONE_VALIDATION_MSG = "Enter a valid phone number"

        /**
         * Retrieve string data from the parameter.
         * @param data - can be EditText or String
         * @return - String extracted from EditText or data if its data type is Strin.
         */
        private fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.toString()
            } else if (data is String) {
                str = data
            }
            return str
        }

        /**
         * Checks if the name is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the name is valid.
         */
        fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the email is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the email is valid.
         */
        fun isValidEmail(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = Patterns.EMAIL_ADDRESS.matcher(str).matches()

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else EMAIL_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the phone is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the phone is valid.
         */
        fun isValidPhone(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = Patterns.PHONE.matcher(str).matches()

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PHONE_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the password is valid AS per the following password policy.
         * Password should be minimum minimum 8 characters long.
         * Password should contain at least one number.
         * Password should contain at least one capital letter.
         * Password should contain at least one small letter.
         * Password should contain at least one special character.
         * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
         *
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the password is valid AS per the password policy.
         */
        fun isValidPassword(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 8) {
                valid = false
            }
            // Password should contain at least one number
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one capital letter
            exp = ".*[A-Z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one small letter
            exp = ".*[a-z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY
                setError(data, error)
            }

            return valid
        }

        /**
         * Sets error on EditText or TextInputLayout of the EditText.
         * @param data - Should be EditText
         * @param error - Message to be shown AS error, can be null if no error is to be set
         */
        private fun setError(data: Any, error: String?) {
            if (data is EditText) {
                if (data.parent.parent is TextInputLayout) {
                    (data.parent.parent as TextInputLayout).error = error
                    (data.parent.parent as TextInputLayout).isErrorEnabled = error != null
                    error?.let {
                        data.requestFocus()
                    }
                } else {
                    data.error = error
                }
            }
        }
    }
}