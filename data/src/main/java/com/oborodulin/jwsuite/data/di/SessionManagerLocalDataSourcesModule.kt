package com.oborodulin.jwsuite.data.di

import androidx.datastore.core.DataStore
import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.sources.local.LocalSessionManagerDataSourceImpl
import com.oborodulin.jwsuite.data.services.csv.model.session.AuthData
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
        authDataStore: DataStore<AuthData>, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalSessionManagerDataSource = LocalSessionManagerDataSourceImpl(authDataStore, dispatcher)
}