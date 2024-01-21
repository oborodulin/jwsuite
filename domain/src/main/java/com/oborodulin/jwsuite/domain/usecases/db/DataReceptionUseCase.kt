package com.oborodulin.jwsuite.domain.usecases.db

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.model.appsetting.AppSettingsWithSession
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.internal.NopCollector.emit

class DataReceptionUseCase(
    private val ctx: Context,
    configuration: Configuration,
    private val sessionManagerRepository: SessionManagerRepository,
    private val membersRepository: MembersRepository
) : UseCase<DataReceptionUseCase.Request, DataReceptionUseCase.Response>(configuration) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request) = sessionManagerRepository.username().flatMapLatest { username ->
        val transferObjects = membersRepository.getMemberTransferObjects(username.orEmpty()).first()
        emit(Response(
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
data class Response(
    val csvFilePrefix: String = "",
    val loadListSize: Int = 0,
    val isSuccess: Boolean = true
) : UseCase.Response

}