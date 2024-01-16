package com.oborodulin.jwsuite.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.ExperimentalMultiProcessDataStore
import androidx.datastore.dataStoreFile
import com.oborodulin.home.common.secure.Crypto
import com.oborodulin.jwsuite.data.local.datastore.SecureAuthDataSerializer
import com.oborodulin.jwsuite.data.util.Constants.DATA_STORE_NAME
import com.oborodulin.jwsuite.data.services.csv.model.session.AuthData
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// https://medium.com/androiddevelopers/datastore-and-dependency-injection-ea32b95704e3
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @OptIn(ExperimentalMultiProcessDataStore::class)
    @Singleton
    @Provides
    fun provideAuthDataStore(@ApplicationContext ctx: Context, crypto: Crypto):
            DataStore<AuthData> = DataStoreFactory.create(
        serializer = SecureAuthDataSerializer(crypto),
        produceFile = { ctx.dataStoreFile(DATA_STORE_NAME) }
    )
    /*MultiProcessDataStoreFactory.create(
        serializer = SecureAuthDataSerializer(crypto),
        produceFile = { File("${ctx.cacheDir.path}/$DATA_STORE_NAME") }
    )*/
}