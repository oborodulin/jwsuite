package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.util.Constants.TRANSFER_PATH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID

private const val TAG = "Domain.DataTransmissionUseCase"

class DataTransmissionUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val exportService: ExportService,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository,
    private val databaseRepository: DatabaseRepository
) : UseCase<DataTransmissionUseCase.Request, DataTransmissionUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        val username = request.memberId?.let {
            membersRepository.get(it).first().pseudonym
        } ?: sessionManagerRepository.username().first()
        val transferObjects = membersRepository.getMemberTransferObjects(username.orEmpty()).first()
            .map { Pair(it.transferObject.transferObjectType, it.isPersonalData) }
        databaseRepository.transferObjectTableNames(transferObjects).first()
            .forEach { tableName ->
                exportService.csvRepositoryExtracts(tableName.key).forEach { callable ->
                    val csvFilePrefix = callable.key.fileNamePrefix
                    val extractMethod = callable.value
                    Timber.tag(TAG).d(
                        "CSV Data Transmission -> %s: csvFilePrefix = %s",
                        extractMethod.name, csvFilePrefix
                    )
                    if (extractMethod.returnType is Flow<*>) {
                        // get and transformation data to exportable type
                        val paramUsername = when (tableName.value) {
                            true -> username
                            false -> null
                        }
                        val extractData = extractMethod.parameters.getOrNull(0)?.let {
                            (extractMethod.call(paramUsername) as Flow<*>).first()
                        } ?: (extractMethod.call() as Flow<*>).first()
                        if (extractData is List<*> && extractData.isNotEmpty()) {
                            val exportableList = extractData as List<Exportable>
                            Timber.tag(TAG).d(
                                "CSV Data Transmission -> %s(%s): list.size = %d",
                                extractMethod.name, paramUsername, exportableList.size
                            )
                            // call export function from Export serivce with apply config + type of export
                            exportService.export(
                                type = Exports.CSV(
                                    CsvConfig(
                                        ctx = ctx,
                                        subDir = "$TRANSFER_PATH${
                                            request.memberId?.let { "/$it" }.orEmpty()
                                        }",
                                        prefix = csvFilePrefix
                                    )
                                ),
                                content = exportableList    // send transformed data of exportable type
                            ).catch {
                                // handle error here
                                throw UseCaseException.DataTransmissionException(it)
                            }.map {
                                emit(Response(csvFilePrefix = csvFilePrefix, isSuccess = it))
                            }
                        }
                    }
                }
            }
    }

    data class Request(val memberId: UUID? = null) : UseCase.Request
    data class Response(val csvFilePrefix: String, val isSuccess: Boolean) : UseCase.Response
}