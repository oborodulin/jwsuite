package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.state.ObjectsTransferState
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.domain.util.Constants.TRANSFER_PATH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.UUID

private const val TAG = "Domain.SendDataUseCase"

class SendDataUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val exportService: ExportService,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository,
    private val databaseRepository: DatabaseRepository
) : UseCase<SendDataUseCase.Request, SendDataUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        Timber.tag(TAG).d(
            "CSV Data Transmission -> process(...) called: request.memberId = %s; request.memberRoleType = %s",
            request.memberId, request.memberRoleType
        )
        // data assigned to member
        val username = when (request.memberId) {
            null -> when (request.memberRoleType) {
                null -> null
                else -> membersRepository.getAllByRoles(listOf(request.memberRoleType)).first()
                    .firstOrNull()?.pseudonym
            }

            else -> membersRepository.get(request.memberId).first().pseudonym
        } ?: sessionManagerRepository.username().first() // data from owner to roled member
        Timber.tag(TAG).d("CSV Data Transmission: username = %s", username)
        val transferObjects = membersRepository.getMemberTransferObjects(username.orEmpty()).first()
            .map { Pair(it.transferObject.transferObjectType, it.isPersonalData) }
        Timber.tag(TAG).d("CSV Data Transmission: transferObjects = %s", transferObjects)
        databaseRepository.transferObjectTableNames(transferObjects).first()
            .forEach { tableName ->
                Timber.tag(TAG).d("CSV Data Transmission: tableName = %s", tableName)
                val repositoryExtracts = exportService.csvRepositoryExtracts(tableName.key)
                repositoryExtracts.onEachIndexed { callableIdx, callable ->
                    val csvFilePrefix = callable.key.fileNamePrefix
                    val extractMethod = callable.value.first
                    Timber.tag(TAG).d(
                        "CSV Data Transmission -> %s: csvFilePrefix = %s",
                        extractMethod.name, csvFilePrefix
                    )
                    if (extractMethod.returnType.classifier == Flow::class) {
                        // get and transformation data to exportable type
                        val paramUsername = when (tableName.value.second) {
                            true -> username
                            false -> null
                        }
                        val paramByFavorite = tableName.value.second.not()
                        val extractData = extractMethod.parameters.getOrNull(0)?.let { param1 ->
                            Timber.tag(TAG).d(
                                "CSV Data Transmission -> %s: param1.type = %s",
                                extractMethod.name, param1.type.toString()
                            )
                            extractMethod.parameters.getOrNull(1)?.let { param2 ->
                                Timber.tag(TAG).d(
                                    "CSV Data Transmission -> %s: param2.type = %s",
                                    extractMethod.name, param2.type.toString()
                                )
                                if (param1.type.toString() == "kotlin.String?" && param2.type.toString() == "kotlin.Boolean?") {
                                    Timber.tag(TAG).d(
                                        "CSV Data Transmission -> %s(%s, %s)",
                                        extractMethod.name, paramUsername, paramByFavorite
                                    )
                                    (extractMethod.call(
                                        callable.value.second, paramUsername, paramByFavorite
                                    ) as Flow<*>).first()
                                }
                            } ?: when (param1.type.toString()) {
                                "kotlin.Boolean?" -> {
                                    Timber.tag(TAG).d(
                                        "CSV Data Transmission -> %s(%s)",
                                        extractMethod.name, paramByFavorite
                                    )
                                    (extractMethod.call(
                                        callable.value.second, paramByFavorite
                                    ) as Flow<*>).first()
                                }

                                else -> {
                                    Timber.tag(TAG).d(
                                        "CSV Data Transmission -> %s(%s)",
                                        extractMethod.name, paramUsername
                                    )
                                    (extractMethod.call(
                                        callable.value.second, paramUsername
                                    ) as Flow<*>).first()
                                }
                            }
                        } ?: (extractMethod.call(callable.value.second) as Flow<*>).first()
                        if (extractData is List<*> && extractData.isNotEmpty()) {
                            val exportableList = extractData as List<Exportable>
                            Timber.tag(TAG).d(
                                "CSV Data Transmission -> %s: return exportableList.size = %d",
                                extractMethod.name, exportableList.size
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
                                throw UseCaseException.DataSendException(it)
                            }.collect {
                                emit(
                                    Response(
                                        ObjectsTransferState(
                                            objectName = csvFilePrefix,
                                            objectDesc = tableName.value.first,
                                            totalObjects = repositoryExtracts.size,
                                            currentObjectNum = callableIdx + 1,
                                            isSuccess = it
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
        emit(Response(ObjectsTransferState()))
    }

    data class Request(val memberId: UUID? = null, val memberRoleType: MemberRoleType? = null) :
        UseCase.Request

    data class Response(val transferState: ObjectsTransferState) : UseCase.Response
}