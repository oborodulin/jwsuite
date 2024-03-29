package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.state.ObjectsTransferState
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.services.Imports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.util.Constants.RECEIVE_PATH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File

private const val TAG = "Domain.ReceiveDataUseCase"

class ReceiveDataUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val importService: ImportService,
    private val databaseRepository: DatabaseRepository
) : UseCase<ReceiveDataUseCase.Request, ReceiveDataUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        var importResult = false
        val dir = File(ctx.filesDir.path.plus(RECEIVE_PATH))
        val csvFiles = dir.listFiles()
        csvFiles?.let {
            Timber.tag(TAG).d("CSV Data Receive: Files size = %s", csvFiles.size)
            databaseRepository.orderedDataTableNames().first().forEach { tableName ->
                csvFiles.find { it.name.substringBefore('.') == tableName.key }?.let { file ->
                    Timber.tag(TAG).d(
                        "CSV Data Receive: tableName = %s -> FileName = %s",
                        tableName, file.name
                    )
                    val repositoryLoads = importService.csvRepositoryLoads(tableName.key)
                    Timber.tag(TAG).d("CSV Data Receive: repositoryLoads = %s", repositoryLoads)
                    repositoryLoads.onEachIndexed { callableIdx, callable ->
                        val csvFilePrefix = callable.key.fileNamePrefix
                        val contentType = callable.key.contentType
                        val loadMethod = callable.value.first
                        Timber.tag(TAG).d(
                            "CSV Data Receive -> %s: csvFilePrefix = %s; contentType = %s",
                            loadMethod.name, csvFilePrefix, contentType
                        )
                        // call import function from Import service with apply config + type of import data
                        val importList = importService.import(
                            type = Imports.CSV(
                                CsvConfig(
                                    ctx = ctx,
                                    subDir = RECEIVE_PATH,
                                    prefix = csvFilePrefix
                                )
                            ),
                            contentType = contentType.java    // define importable type of data
                        ).catch {
                            // handle error here
                            throw UseCaseException.DataReceiveException(it)
                        }.first()
                        if (importList.isNotEmpty() && loadMethod.returnType.classifier == Flow::class) {
                            // transformation data to importable type and load
                            val loadListSize = (loadMethod.call(
                                callable.value.second, importList
                            ) as Flow<*>).first()
                            if (loadListSize is Int) {
                                importResult = loadListSize > 0
                                Timber.tag(TAG).d(
                                    "CSV Data Receive -> %s: loadListSize = %s",
                                    loadMethod.name, loadListSize
                                )
                                emit(
                                    Response(
                                        ObjectsTransferState(
                                            objectName = csvFilePrefix,
                                            totalObjectItems = loadListSize,
                                            objectDesc = tableName.value,
                                            totalObjects = repositoryLoads.size,
                                            currentObjectNum = callableIdx + 1,
                                            isSuccess = importResult
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
                //for (i in csvFiles.indices) {}
            }
        }
        emit(Response(ObjectsTransferState(isSuccess = importResult)))
    }

    data object Request : UseCase.Request
    data class Response(val transferState: ObjectsTransferState) : UseCase.Response
}