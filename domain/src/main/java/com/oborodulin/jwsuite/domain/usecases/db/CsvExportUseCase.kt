package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
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
    private val csvRepositories: List<CsvTransferableRepo> = emptyList()
) : UseCase<CsvExportUseCase.Request, CsvExportUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        csvRepositories.forEach { repo ->
            repo.javaClass.kotlin.members.filter { it.annotations.any { anno -> anno is CsvExtract } }
                .forEach { extractMethod ->
                    val csvFilePrefix =
                        (extractMethod.annotations.first { anno -> anno is CsvExtract } as CsvExtract).fileNamePrefix
                    Timber.tag(TAG).d(
                        "CSV Exporting -> %s: csvFilePrefix = %s",
                        extractMethod.name, csvFilePrefix
                    )
                    if (extractMethod.returnType is Flow<*>) {
                        // get and transformation data to exportable type
                        val extractData = (extractMethod.call() as Flow<*>).first()
                        if (extractData is List<*> && extractData.isNotEmpty()) {
                            val exportableList = (extractData as List<Exportable>)
                            Timber.tag(TAG).d(
                                "CSV Exporting -> %s: list.size = %d",
                                extractMethod.name,  exportableList.size
                            )
                            // call export function from Export serivce with apply config + type of export
                            exportService.export(
                                type = Exports.CSV(CsvConfig(ctx = ctx, prefix = csvFilePrefix)),
                                content = exportableList    // send transformed data of exportable type
                            ).catch {
                                // handle error here
                                throw UseCaseException.ExportException(it)
                            }.map {
                                emit(Response(csvFilePrefix = csvFilePrefix, isSuccess = it))
                            }/*.collect { _ ->
            // do anything on success
            //_exportCsvState.value = ViewState.Success(emptyList())
        }*/

                        }
                    }
                }
        }
    }

    data object Request : UseCase.Request
    data class Response(val csvFilePrefix: String, val isSuccess: Boolean) : UseCase.Response
}