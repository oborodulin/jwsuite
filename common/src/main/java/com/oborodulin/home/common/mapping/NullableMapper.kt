package com.oborodulin.home.common.mapping

interface NullableMapper<in I, out O> {
    fun nullableMap(input: I?): O?
}