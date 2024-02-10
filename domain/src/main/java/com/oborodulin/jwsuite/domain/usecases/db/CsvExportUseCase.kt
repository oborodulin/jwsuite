package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.util.Constants.BACKUP_PATH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val TAG = "Domain.CsvExportUseCase"

// https://chetangupta.net/db-to-csv/
// https://stackoverflow.com/questions/37586532/how-to-find-methods-with-an-annotation-in-a-class
// https://stackoverflow.com/questions/73786464/hilt-inject-a-list-of-interface-implementations

// @JvmSuppressWildcards
class CsvExportUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val exportService: ExportService,
    private val databaseRepository: DatabaseRepository
) : UseCase<CsvExportUseCase.Request, CsvExportUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        val dataTables = databaseRepository.orderedDataTableNames().first()
        exportService.csvRepositoryExtracts().forEach { callables ->
            val csvFilePrefix = callables.key.fileNamePrefix
            val extractMethod = callables.value
            val entityDesc = dataTables[csvFilePrefix]
            Timber.tag(TAG).d(
                "CSV Exporting -> %s: csvFilePrefix = %s, entityDesc = %s",
                extractMethod.name, csvFilePrefix, entityDesc
            )
            if (extractMethod.returnType is Flow<*>) {
                // get and transformation data to exportable type
                val extractData = (extractMethod.call() as Flow<*>).first()
                if (extractData is List<*> && extractData.isNotEmpty()) {
                    val exportableList = extractData as List<Exportable>
                    Timber.tag(TAG).d(
                        "CSV Exporting -> %s: list.size = %d",
                        extractMethod.name, exportableList.size
                    )
                    // call export function from Export service with apply config + type of export
                    exportService.export(
                        type = Exports.CSV(
                            CsvConfig(
                                ctx = ctx,
                                subDir = BACKUP_PATH,
                                prefix = csvFilePrefix
                            )
                        ),
                        content = exportableList    // send transformed data of exportable type
                    ).catch {
                        // handle error here
                        throw UseCaseException.ExportException(it)
                    }.map {
                        emit(
                            Response(
                                csvFilePrefix = csvFilePrefix,
                                entityDesc = entityDesc.orEmpty(),
                                isSuccess = it
                            )
                        )
                    }/*.collect { _ ->
            // do anything on success
            //_exportCsvState.value = ViewState.Success(emptyList())
        }*/

                }
            }
        }
        emit(Response(isDone = true))
    }

    data object Request : UseCase.Request
    data class Response(
        val csvFilePrefix: String = "",
        val entityDesc: String = "",
        val isSuccess: Boolean = false,
        val isDone: Boolean = false
    ) : UseCase.Response
}