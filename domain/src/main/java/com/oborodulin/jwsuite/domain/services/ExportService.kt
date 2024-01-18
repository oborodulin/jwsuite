package com.oborodulin.jwsuite.domain.services

import androidx.annotation.WorkerThread
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileWriter

// https://chetangupta.net/db-to-csv/
object ExportService {
    fun <T : Exportable> export(type: Exports, content: List<T>): Flow<Boolean> =
        when (type) {
            is Exports.CSV -> writeToCSV(type.csvConfig, content)
        }

    @WorkerThread
    private fun <T : Exportable> writeToCSV(csvConfig: CsvConfig, content: List<T>) =
        flow {
            with(csvConfig) {
                hostPath.ifEmpty { throw IllegalStateException("Wrong Path") }
                val hostDirectory = File(hostPath)
                if (!hostDirectory.exists()) {
                    hostDirectory.mkdir() // create directory
                }

                // create csv file
                val csvFile = File("${hostDirectory.path}/$fileName")
                val csvWriter = CSVWriter(FileWriter(csvFile))

                // write csv file
                StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build()
                    .write(content)
                csvWriter.close()
            }
            // emit success
            emit(true)
        }
}