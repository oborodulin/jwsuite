package com.oborodulin.jwsuite.data.local.datastore.repositories

import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SessionManagerRepositoryImpl @Inject constructor(
    private val localSessionManagerDataSource: LocalSessionManagerDataSource
    //private val localMemberDataSource: LocalMemberDataSource,
    //private val mappers: MemberMappers,
    //@IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionManagerRepository {
    override fun isSigned() = localSessionManagerDataSource.isSigned()
    override fun username() = localSessionManagerDataSource.username()
    override fun lastDestination() = localSessionManagerDataSource.lastDestination()
    //override fun roles() = localSessionManagerDataSource.roles()

    override fun signup(username: String, password: String) = flow {
        localSessionManagerDataSource.signup(username, password)
        //val roles = localMemberDataSource.getMemberRoles(username)
        //    .map(mappers.roleEntityListToRolesListMapper::map).first()
        //localSessionManagerDataSource.updateRoles(roles)
        //localSessionManagerDataSource.login()
        emit(true)
    }

    override fun signout() = flow {
        localSessionManagerDataSource.signout()
        emit(true)
    }

    override fun isPasswordValid(password: String) =
        localSessionManagerDataSource.isPasswordValid(password)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun login(password: String) = localSessionManagerDataSource.login(password)
    /*.flatMapLatest { username ->
    when (username) {
        null -> flow { emit(false) }
        else -> {
            val roles = localMemberDataSource.getMemberRoles(username)
                .map(mappers.memberRoleViewListToRolesListMapper::map).first()
            //localSessionManagerDataSource.updateRoles(roles)
            flow { emit(true) }
        }
    }
}*/

    override fun logout(lastDestination: String?) = flow {
        localSessionManagerDataSource.logout(lastDestination)
        emit(true)
    }
}