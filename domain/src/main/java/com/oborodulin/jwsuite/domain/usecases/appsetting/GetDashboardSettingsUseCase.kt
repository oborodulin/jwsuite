package com.oborodulin.jwsuite.domain.usecases.appsetting

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.home.common.util.getAppVersion
import com.oborodulin.jwsuite.domain.model.appsetting.DashboardSettingsWithSession
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class GetDashboardSettingsUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val databaseRepository: DatabaseRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository
) : UseCase<GetDashboardSettingsUseCase.Request, GetDashboardSettingsUseCase.Response>(configuration) {
    override fun process(request: Request) = combine(
        appSettingsRepository.getAll(),
        sessionManagerRepository.username(),
        databaseRepository.sqliteVersion(),
        databaseRepository.dbVersion()
    ) { settings, username, sqliteVersion, dbVersion ->
        val roles = membersRepository.getMemberRoles(username.orEmpty()).first()
        val version = ctx.getAppVersion()
        Response(
            DashboardSettingsWithSession(
                settings = settings,
                username = username.orEmpty(),
                roles = roles,
                appVersionName = version?.versionName.orEmpty(),
                frameworkVersion = "${android.os.Build.VERSION.SDK_INT}",
                sqliteVersion = sqliteVersion,
                dbVersion = dbVersion
            )
        )
    }

    data object Request : UseCase.Request
    data class Response(val dashboardSettingsWithSession: DashboardSettingsWithSession) :
        UseCase.Response
}