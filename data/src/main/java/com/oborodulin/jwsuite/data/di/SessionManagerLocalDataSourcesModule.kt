package com.oborodulin.jwsuite.data.di

import androidx.datastore.core.DataStore
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.sources.local.LocalSessionManagerDataSourceImpl
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.domain.model.AuthData
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionManagerLocalDataSourcesModule {
    /*//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_DATA_STORE_NAME)
    private val Context.authDataStore(crypto: Crypto): DataStore<AuthData> by dataStore(
        fileName = Constants.PREF_DATA_STORE_NAME,
        serializer = SecureAuthDataSerializer(crypto)
    )*/

    @Singleton
    @Provides
    fun provideLocalSessionManagerDataSource(
        authDataStore: DataStore<AuthData>, memberDao: MemberDao
    ): LocalSessionManagerDataSource = LocalSessionManagerDataSourceImpl(authDataStore, memberDao)
}