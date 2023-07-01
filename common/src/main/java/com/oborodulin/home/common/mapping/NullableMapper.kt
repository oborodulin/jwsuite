package com.oborodulin.home.common.mapping

interface NullableMapper<in I, out O> {
    fun map(input: I?): O?
}