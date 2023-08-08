package com.oborodulin.home.common.mapping

interface NullableConstructedMapper<in I, out O> {
    fun nullableMap(input: I?, vararg properties: Any?): O?
}