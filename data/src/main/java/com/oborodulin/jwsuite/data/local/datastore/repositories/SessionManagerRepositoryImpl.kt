package com.oborodulin.jwsuite.data.local.datastore.repositories


import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.MemberMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalMemberDataSource
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManagerRepositoryImpl @Inject constructor(
    private val localSessionManagerDataSource: LocalSessionManagerDataSource,
    private val localMemberDataSource: LocalMemberDataSource,
    private val mappers: MemberMappers,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionManagerRepository {
    override fun isSigned() = localSessionManagerDataSource.isSigned()
    override fun isLogged() = localSessionManagerDataSource.isLogged()
    override fun roles() = localSessionManagerDataSource.roles()

    override fun signup(username: String, password: String) = flow {
        localSessionManagerDataSource.signup(username, password)
        val roles = localMemberDataSource.getMemberRoles(username)
            .map(mappers.roleEntityListToRolesListMapper::map).first()
        localSessionManagerDataSource.updateRoles(roles)
        localSessionManagerDataSource.login()
        emit(true)
    }

    override fun signout() = flow {
        localSessionManagerDataSource.signout()
        emit(true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun login(password: String) =
        localSessionManagerDataSource.isPasswordValid(password).flatMapLatest { username ->
            when (username) {
                null -> flow { emit(false) }
                else -> {
                    val roles = localMemberDataSource.getMemberRoles(username)
                        .map(mappers.roleEntityListToRolesListMapper::map).first()
                    localSessionManagerDataSource.updateRoles(roles)
                    localSessionManagerDataSource.login()
                    flow { emit(true) }
                }
            }
        }

    override fun logout() = flow {
        localSessionManagerDataSource.logout()
        emit(true)
    }
}