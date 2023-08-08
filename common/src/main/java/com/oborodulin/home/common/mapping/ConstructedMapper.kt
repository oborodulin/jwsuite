package com.oborodulin.home.common.mapping

interface ConstructedMapper<in I, out O> {
    fun map(input: I, vararg properties: Any?): O
}