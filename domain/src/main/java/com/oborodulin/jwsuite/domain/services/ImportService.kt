package com.oborodulin.jwsuite.domain.services

import androidx.annotation.WorkerThread
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.opencsv.CSVReader
import com.opencsv.bean.CsvToBeanBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileReader

// https://chetangupta.net/db-to-csv/
object ImportService {
    fun <T : Importable> import(type: Imports, contentType: Class<T>): Flow<List<T>> =
        when (type) {
            is Imports.CSV -> readCSV(type.csvConfig, contentType)
        }

    @WorkerThread
    private fun <T : Importable> readCSV(csvConfig: CsvConfig, contentType: Class<T>) =
        flow<List<T>> {
            with(csvConfig) {
                hostPath.ifEmpty { throw IllegalStateException("Wrong Path") }
                val csvFile = File("$hostPath/$fileName")
                if (csvFile.exists()) {
                    // read csv file
                    val csvReader = CSVReader(FileReader(csvFile))
                    val beans =
                        CsvToBeanBuilder<T>(csvReader).withType(contentType).build().parse();
                    csvReader.close()
                    // emit success
                    emit(beans)
                } else {
                    // emit empty
                    emit(listOf())
                }
            }
        }
}