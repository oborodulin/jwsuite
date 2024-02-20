package com.oborodulin.jwsuite.domain.services

import androidx.annotation.WorkerThread
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import kotlin.reflect.KCallable

private const val TAG = "Domain.ExportService"

// https://chetangupta.net/db-to-csv/
class ExportService(private val csvRepositories: List<CsvTransferableRepo> = emptyList()) {
    fun <T : Exportable> export(type: Exports, content: List<T>): Flow<Boolean> {
        Timber.tag(TAG).d("export(...) called: type = %s; content.size = %d", type, content.size)
        return when (type) {
            is Exports.CSV -> writeToCSV(type.csvConfig, content)
        }
    }

    fun csvRepositoryExtracts(fileNamePrefix: String? = null): Map<CsvExtract, Pair<KCallable<*>, CsvTransferableRepo>> {
        val callables: MutableMap<CsvExtract, Pair<KCallable<*>, CsvTransferableRepo>> =
            mutableMapOf()
        csvRepositories.forEach { repo ->
            repo.javaClass.kotlin.members.forEach { method ->
                method.annotations.find { anno -> anno is CsvExtract && fileNamePrefix?.let { it == anno.fileNamePrefix } ?: true }
                    ?.let { anno ->
                        anno as CsvExtract
                        callables[anno] = method to repo
                    }
            }
        }
        Timber.tag(TAG).d("ExportService: callables = %s", callables)
        return callables
    }

    fun <T : List<Exportable>> extract(
        callable: Pair<KCallable<*>, CsvTransferableRepo>, vararg params: Any?
    ): Flow<T> {
        Timber.tag(TAG).d("extract(...) called")
        if (params.size < 2 || params.getOrNull(1) !is Boolean) {
            throw IllegalArgumentException("Invalid argument 'params': size less then 2 or params[1] not Boolean type")
        }
        val extractMethod = callable.first
        @Suppress("UNCHECKED_CAST")
        return when (extractMethod.returnType.classifier) {
            Flow::class ->
                when (extractMethod.parameters.size) {
                    2 -> extractMethod.call(
                        callable.second,
                        if (extractMethod.parameters[1].type.classifier == Boolean::class) params[1] else params[0]
                    )

                    3 -> extractMethod.call(callable.second, params[0], params[1])
                    else -> extractMethod.call(callable.second)
                }
            else -> flowOf(emptyList<Exportable>())
        } as Flow<T>
    }

    @WorkerThread
    private fun <T : Exportable> writeToCSV(csvConfig: CsvConfig, content: List<T>) =
        flow {
            Timber.tag(TAG).d("writeToCSV(...) called")
            with(csvConfig) {
                // https://yatmanwong.medium.com/know-how-to-use-your-android-file-system-cfcc5e463e02
                parent.path.ifEmpty { throw IllegalStateException("Wrong Path") }
                val hostDirectory = File(parent, childPath)
                if (!hostDirectory.exists()) {
                    Timber.tag(TAG).d("writeToCSV: hostDirectory not exist!")
                    hostDirectory.mkdirs() // create directory
                }

                // create csv file
                val csvFile = File(hostDirectory, fileName)
                val csvWriter = CSVWriter(FileWriter(csvFile))
                Timber.tag(TAG).d("writeToCSV: csvFile.path = %s", csvFile.path)

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