package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.sources.local.LocalSessionManagerDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object SessionManagerLocalDataSourcesModule {
abstract class SessionManagerLocalDataSourcesModule {
    @Binds
    abstract fun bindLocalSessionManagerDataSource(dataSourceImpl: LocalSessionManagerDataSourceImpl): LocalSessionManagerDataSource

    /*//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_DATA_STORE_NAME)
private val Context.authDataStore(crypto: Crypto): DataStore<AuthData> by dataStore(
    fileName = Constants.PREF_DATA_STORE_NAME,
    serializer = SecureAuthDataSerializer(crypto)
)*/
    /*
        @Singleton
        @Provides
        fun provideLocalSessionManagerDataSource(
            authDataStore: DataStore<AuthData>, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalSessionManagerDataSource = LocalSessionManagerDataSourceImpl(authDataStore, dispatcher)
     */
}