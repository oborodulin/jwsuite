package com.oborodulin.home.common.ui.components.field.util

interface Validatable {
    fun errorIdOrNull(vararg inputs: String?): Int?
    fun isValid(vararg inputs: String?) = errorIdOrNull(*inputs) == null
}