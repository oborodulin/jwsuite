package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.services.Imports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.internal.NopCollector.emit
import kotlinx.coroutines.flow.map
import timber.log.Timber

class CsvImportUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val importService: ImportService,
    private val databaseRepository: DatabaseRepository,
    private val csvRepositories: List<CsvTransferableRepo> = emptyList()
) : UseCase<ExportUseCase.Request, ExportUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) =
        databaseRepository.orderedDataTableNames().flatMapLatest { tableNames ->
            tableNames.forEach { name ->
                csvRepositories.forEach { repo ->
                    repo.javaClass.kotlin.members.filter { it.annotations.any { anno -> anno is CsvLoad<*> } }
                        .forEach { loadMethod ->
                            val csvLoad = (loadMethod.annotations.first { anno -> anno is CsvLoad } as CsvLoad<*>)
                            val csvFilePrefix = csvLoad.fileNamePrefix
                            val contentType = csvLoad.contentType
                            Timber.tag(TAG).d(
                                "CSV Exporting -> %s: csvFilePrefix = %s",
                                loadMethod.name, csvFilePrefix
                            )
                            if (loadMethod.returnType is Flow<*>) {
                                // get and transformation data to exportable type
                                val extractData = (loadMethod.call() as Flow<*>).first()
                                if (extractData is List<*> && extractData.isNotEmpty()) {
                                    val exportableList = (extractData as List<Exportable>)
                                    Timber.tag(TAG).d(
                                        "CSV Exporting -> %s: list.size = %d",
                                        loadMethod.name, exportableList.size
                                    )
                                    // call export function from Export serivce with apply config + type of export
                                    importService.import(
                                        type = Imports.CSV(
                                            CsvConfig(ctx = ctx, prefix = csvFilePrefix)
                                        ),
                                        contentType = contentType    // send transformed data of exportable type
                                    ).catch {
                                        // handle error here
                                        throw UseCaseException.ExportException(it)
                                    }.map {
                                        emit(
                                            CsvExportUseCase.Response(
                                                csvFilePrefix = csvFilePrefix,
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
                }
            }
        }

    data object Request : UseCase.Request
    data class Response(val csvFilePrefix: String, val isSuccess: Boolean) : UseCase.Response
}