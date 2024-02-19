package com.oborodulin.jwsuite.domain.services

import androidx.annotation.WorkerThread
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.opencsv.CSVReader
import com.opencsv.bean.CsvToBeanBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File
import java.io.FileReader
import kotlin.reflect.KCallable

private const val TAG = "Domain.ImportService"

// https://chetangupta.net/db-to-csv/
class ImportService(private val csvRepositories: List<CsvTransferableRepo> = emptyList()) {
    fun <T : Importable> import(type: Imports, contentType: Class<T>): Flow<List<T>> =
        when (type) {
            is Imports.CSV -> readFromCSV(type.csvConfig, contentType)
        }

    fun csvRepositoryLoads(fileNamePrefix: String? = null): Map<CsvLoad<*>, Pair<KCallable<*>, CsvTransferableRepo>> {
        val callables: MutableMap<CsvLoad<*>, Pair<KCallable<*>, CsvTransferableRepo>> =
            mutableMapOf()
        csvRepositories.forEach { repo ->
            repo.javaClass.kotlin.members.forEach { method ->
                method.annotations.find { anno -> anno is CsvLoad<*> && fileNamePrefix?.let { it == anno.fileNamePrefix } ?: true }
                    ?.let { anno ->
                        anno as CsvLoad<*>
                        callables[anno] = method to repo
                    }
            }
        }
        return callables
    }

    @WorkerThread
    private fun <T : Importable> readFromCSV(csvConfig: CsvConfig, contentType: Class<T>) =
        flow<List<T>> {
            with(csvConfig) {
                parent.path.ifEmpty { throw IllegalStateException("Wrong Path") }
                val csvFile = File(parent, "$childPath/$fileName")
                if (csvFile.exists()) {
                    // read csv file
                    val csvReader = CSVReader(FileReader(csvFile))
                    val content =
                        CsvToBeanBuilder<T>(csvReader).withType(contentType).build().parse();
                    csvReader.close()
                    Timber.tag(TAG).d("readFromCSV: csvFile.path = %s", csvFile.path)
                    // emit success
                    emit(content)
                } else {
                    Timber.tag(TAG).d("readFromCSV: csvFile not exist!")
                    // emit empty
                    emit(listOf())
                }
            }
        }
}