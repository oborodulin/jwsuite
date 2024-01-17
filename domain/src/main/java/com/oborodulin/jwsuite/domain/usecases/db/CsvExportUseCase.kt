package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.appsetting.AppSettingsWithSession
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import kotlinx.coroutines.flow.map

class CsvExportUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val databaseRepository: DatabaseRepository,
    private val exportService: ExportService,
    private val appSettingsRepository: AppSettingsRepository
) : UseCase<CsvExportUseCase.Request, CsvExportUseCase.Response>(configuration) {
    override fun process(request: Request) =

        databaseRepository.orderedDataTableNames().map { tableNames ->
        val
        tableNames.forEach { name -> }
        exportService.export()
    }
            ) { transferObjects, dataTables ->

            }
            val transferObjects =
            .first()
            .map { it.transferObject.transferObjectType }
            ctx.filesDir.path
            emit(
                Response(
                    AppSettingsWithSession(
                        settings = settings,
                        username = username.orEmpty(),
                        roles = roles,
                        roleTransferObjects = roleTransferObjects,
                        appVersionName = version?.versionName.orEmpty(),
                        frameworkVersion = "${android.os.Build.VERSION.SDK_INT}",
                        sqliteVersion = sqliteVersion,
                        dbVersion = dbVersion
                    )
                )
        }
}

data object Request : UseCase.Request
data class Response(val appSettingsWithSession: AppSettingsWithSession) : UseCase.Response
}