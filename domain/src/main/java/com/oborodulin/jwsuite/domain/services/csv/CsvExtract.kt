package com.oborodulin.jwsuite.domain.services.csv

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CsvExtract(val fileNamePrefix: String)
