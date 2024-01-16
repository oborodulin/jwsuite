package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.appsetting.AppSettingsWithSession
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.internal.NopCollector.emit

class CsvImportUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository,
    private val databaseRepository: DatabaseRepository,
) : UseCase<ExportUseCase.Request, ExportUseCase.Response>(configuration) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) =
        sessionManagerRepository.username().flatMapLatest { username ->
            combine(
                membersRepository.getMemberTransferObjects(username.orEmpty()),
                databaseRepository.orderedDataTableNames()
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