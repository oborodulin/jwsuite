package com.oborodulin.jwsuite.data.local.datastore.repositories


import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionManagerRepositoryImpl @Inject constructor(
    private val localSessionManagerDataSource: LocalSessionManagerDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionManagerRepository {
    override fun isSigned() = localSessionManagerDataSource.isSigned()
    override fun isLogged() = localSessionManagerDataSource.isLogged()
    override suspend fun signup(username: String, password: String) = withContext(dispatcher) {
        localSessionManagerDataSource.signup(username, password)
    }

    override suspend fun login(password: String) = flow {
        val isSuccess = false
        withContext(dispatcher) {
            localSessionManagerDataSource.checkPassword(password).collect{
                if (it) {
                    localSessionManagerDataSource.login()
                }
            }
        }
        emit(isSuccess)
    }

    override suspend fun logout() = withContext(dispatcher) {
        localSessionManagerDataSource.logout()
    }

}