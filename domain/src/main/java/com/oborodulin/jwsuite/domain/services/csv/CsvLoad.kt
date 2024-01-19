package com.oborodulin.jwsuite.domain.services.csv

import com.oborodulin.jwsuite.domain.services.Importable
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CsvLoad<T : Importable>(val fileNamePrefix: String, val contentType: KClass<T>)
