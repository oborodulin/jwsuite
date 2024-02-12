package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.services.Imports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.util.Constants.RECEPTION_PATH
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File

private const val TAG = "Domain.DataReceptionUseCase"

class DataReceptionUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val importService: ImportService,
    private val databaseRepository: DatabaseRepository
) : UseCase<DataReceptionUseCase.Request, DataReceptionUseCase.Response>(configuration) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) = flow {
        var importResult = false
        val dir = File(ctx.filesDir.path.plus(RECEPTION_PATH))
        val csvFiles = dir.listFiles()
        csvFiles?.let {
            Timber.tag(TAG).d("CSV Data Reception: Files size = %s", csvFiles.size)
            databaseRepository.orderedDataTableNames().first().forEach { tableName ->
                csvFiles.find { it.name.substringBefore('.') == tableName.key }?.let { file ->
                    Timber.tag(TAG).d(
                        "CSV Data Reception: tableName = %s -> FileName = %s",
                        tableName, file.name
                    )
                    importService.csvRepositoryLoads(tableName.key).forEach { callables ->
                        val csvFilePrefix = callables.key.fileNamePrefix
                        val contentType = callables.key.contentType
                        val loadMethod = callables.value
                        Timber.tag(TAG).d(
                            "CSV Data Reception -> %s: csvFilePrefix = %s; contentType = %s",
                            loadMethod.name, csvFilePrefix, contentType
                        )
                        // call import function from Import service with apply config + type of import data
                        val importList = importService.import(
                            type = Imports.CSV(
                                CsvConfig(
                                    ctx = ctx,
                                    subDir = RECEPTION_PATH,
                                    prefix = csvFilePrefix
                                )
                            ),
                            contentType = contentType.java    // define importable type of data
                        ).catch {
                            // handle error here
                            throw UseCaseException.DataReceptionException(it)
                        }.first()
                        if (importList.isNotEmpty() && loadMethod.returnType is Flow<*>) {
                            // transformation data to importable type and load
                            val loadListSize = (loadMethod.call(importList) as Flow<*>).first()
                            if (loadListSize is Int) {
                                importResult = loadListSize > 0
                                Timber.tag(TAG).d(
                                    "CSV Data Reception -> %s: loadListSize = %s",
                                    loadMethod.name, loadListSize
                                )
                                emit(
                                    Response(
                                        csvFilePrefix = csvFilePrefix,
                                        loadListSize = loadListSize,
                                        entityDesc = tableName.value,
                                        isSuccess = importResult
                                    )
                                )
                            }
                        }
                    }
                }
                //for (i in csvFiles.indices) {}
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