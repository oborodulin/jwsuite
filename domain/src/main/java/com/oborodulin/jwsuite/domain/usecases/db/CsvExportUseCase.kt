package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.domain.usecases.UseCaseException
import com.oborodulin.jwsuite.domain.model.Event
import com.oborodulin.jwsuite.domain.model.state.ObjectsTransferState
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.EventsRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Exports
import com.oborodulin.jwsuite.domain.services.csv.CsvConfig
import com.oborodulin.jwsuite.domain.types.EventType
import com.oborodulin.jwsuite.domain.util.Constants.BACKUP_PATH
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
    private val databaseRepository: DatabaseRepository,
    private val eventsRepository: EventsRepository
) : UseCase<CsvExportUseCase.Request, CsvExportUseCase.Response>(configuration) {
    override fun process(request: Request) = flow {
        var importResult = false
        val dataTables = databaseRepository.orderedDataTableNames().first()
        Timber.tag(TAG).d("CSV Exporting: dataTables = %s", dataTables)
        val repositoryExtracts = exportService.csvRepositoryExtracts()
        Timber.tag(TAG).d("CSV Exporting: repositoryExtracts = %s", repositoryExtracts)
        repositoryExtracts.onEachIndexed { callableIdx, callable ->
            val csvFilePrefix = callable.key.fileNamePrefix
            val extractMethod = callable.value.first
            val entityDesc = dataTables[csvFilePrefix]
            Timber.tag(TAG).d(
                "CSV Exporting -> %s: csvFilePrefix = %s, entityDesc = %s",
                extractMethod.name, csvFilePrefix, entityDesc
            )
            // get and transformation data to exportable type
            val exportableList =
                exportService.extract<List<Exportable>>(callable.value, *arrayOf(null, false))
                    .first()
            if (exportableList.isNotEmpty()) {
                //@Suppress("UNCHECKED_CAST")
                //val exportableList = extractData as List<Exportable>
                Timber.tag(TAG).d(
                    "CSV Exporting -> %s: list.size = %d",
                    extractMethod.name, exportableList.size
                )
                // call export function from Export service with apply config + type of export
                exportService.export(
                    type = Exports.CSV(
                        CsvConfig(ctx = ctx, subDir = BACKUP_PATH, prefix = csvFilePrefix)
                    ),
                    content = exportableList    // send transformed data of exportable type
                ).catch {
                    // handle error here
                    throw UseCaseException.ExportException(it)
                }.collect {
                    // do anything on success
                    //_exportCsvState.value = ViewState.Success(emptyList())
                    importResult = it
                    emit(
                        Response(
                            ObjectsTransferState(
                                objectName = csvFilePrefix,
                                objectDesc = entityDesc.orEmpty(),
                                totalObjects = repositoryExtracts.size,
                                currentObjectNum = callableIdx + 1,
                                isSuccess = it
                            )
                        )
                    )
                }
            }
        }
        val backupEvent = eventsRepository.save(
            Event(eventType = EventType.BACKUP, isManual = true, isSuccess = importResult)
        ).first()
        Timber.tag(TAG).d("CSV Exporting: backupEvent = %s", backupEvent)
        emit(Response(ObjectsTransferState()))
    }

    data object Request : UseCase.Request
    data class Response(val transferState: ObjectsTransferState) : UseCase.Response
}