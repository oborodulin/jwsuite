package com.oborodulin.jwsuite.data.local.datastore.repositories


import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionManagerRepositoryImpl @Inject constructor(
    private val localSessionManagerDataSource: LocalSessionManagerDataSource,
    private val localMemberDataSource: LocalMemberDataSource,
    private val mappers: MemberMappers,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionManagerRepository {
    override fun isSigned() = localSessionManagerDataSource.isSigned()
    override fun isLogged() = localSessionManagerDataSource.isLogged()
    override suspend fun signup(username: String, password: String) = withContext(dispatcher) {
        localSessionManagerDataSource.signup(username, password)
        val roles = localMemberDataSource.getMemberRoles(username)
            .map(mappers.roleEntityListToRolesListMapper::map).first()
        localSessionManagerDataSource.updateRoles(roles)
        localSessionManagerDataSource.login()
    }

    override fun roles() = localSessionManagerDataSource.roles()

    override suspend fun login(password: String) = flow {
        val isSuccess = localSessionManagerDataSource.checkPassword(password).first()
        if (isSuccess) {
            val username = localSessionManagerDataSource.username().first()
            username?.let {
                val roles = localMemberDataSource.getMemberRoles(it)
                    .map(mappers.roleEntityListToRolesListMapper::map).first()
                localSessionManagerDataSource.updateRoles(roles)
                localSessionManagerDataSource.login()
            }
        }
        emit(isSuccess)
    }

    override suspend fun logout() = withContext(dispatcher) {
        localSessionManagerDataSource.logout()
    }
}