package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.services.Imports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.util.Constants.BACKUP_PATH
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

private const val TAG = "Domain.CsvImportUseCase"

class CsvImportUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val importService: ImportService,
    private val databaseRepository: DatabaseRepository
) : UseCase<CsvImportUseCase.Request, CsvImportUseCase.Response>(configuration) {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) = flow {
        var importResult = false
        databaseRepository.orderedDataTableNames().first().forEach { tableName ->
            Timber.tag(TAG).d("CSV Importing: tableName = %s", tableName)
            importService.csvRepositoryLoads(tableName.key).forEach { callables ->
                val csvFilePrefix = callables.key.fileNamePrefix
                val contentType = callables.key.contentType
                val loadMethod = callables.value
                Timber.tag(TAG).d(
                    "CSV Importing -> %s: csvFilePrefix = %s; contentType = %s",
                    loadMethod.name, csvFilePrefix, contentType
                )
                // call import function from Import service with apply config + type of import data
                val importList = importService.import(
                    type = Imports.CSV(
                        CsvConfig(ctx = ctx, subDir = BACKUP_PATH, prefix = csvFilePrefix)
                    ),
                    contentType = contentType.java    // define importable type of data
                ).catch {
                    // handle error here
                    throw UseCaseException.ImportException(it)
                }.first()
                if (importList.isNotEmpty() && loadMethod.returnType is Flow<*>) {
                    loadMethod.parameters.getOrNull(0)?.let { param1 ->
                        Timber.tag(TAG).d(
                            "CSV Importing -> %s: param1.type = %s",
                            loadMethod.name, param1.type.toString()
                        )
                        // transformation data to importable type and load
                        val loadListSize = (loadMethod.call(importList) as Flow<*>).first()
                        if (loadListSize is Int) {
                            importResult = loadListSize > 0
                            Timber.tag(TAG).d(
                                "CSV Importing -> %s: loadListSize = %s",
                                loadMethod.name, loadListSize
                            )
                            emit(
                                Response(
                                    csvFilePrefix = csvFilePrefix,
                                    entityDesc = tableName.value,
                                    loadListSize = loadListSize,
                                    isSuccess = importResult
                                )
                            )
                        }
                    }
                }
            }
        }
        emit(Response(isSuccess = importResult, isDone = true))
    }

    data object Request : UseCase.Request
    data class Response(
        val csvFilePrefix: String = "",
        val loadListSize: Int = 0,
        val entityDesc: String = "",
        val isSuccess: Boolean = false,
        val isDone: Boolean = false
    ) : UseCase.Response
}