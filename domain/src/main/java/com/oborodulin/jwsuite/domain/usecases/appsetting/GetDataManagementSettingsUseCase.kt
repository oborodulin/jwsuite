package com.oborodulin.jwsuite.domain.usecases.appsetting

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.appsetting.DataManagementSettings
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class GetDataManagementSettingsUseCase(
    configuration: Configuration,
    private val appSettingsRepository: AppSettingsRepository,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository
) : UseCase<GetDataManagementSettingsUseCase.Request, GetDataManagementSettingsUseCase.Response>(
    configuration
) {
    override fun process(request: Request) = combine(
        appSettingsRepository.getAllByNames(listOf(AppSettingParam.DATABASE_BACKUP_PERIOD)),
        sessionManagerRepository.username()
    ) { settings, username ->
        val roleTransferObjects =
            membersRepository.getMemberTransferObjects(username.orEmpty()).first()
        Response(
            DataManagementSettings(
                settings = settings,
                roleTransferObjects = roleTransferObjects
            )
        )
    }

    data object Request : UseCase.Request
    data class Response(val dataManagementSettings: DataManagementSettings) : UseCase.Response
}