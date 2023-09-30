package com.oborodulin.jwsuite.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.sources.local.LocalSessionManagerDataSourceImpl
import com.oborodulin.jwsuite.data.util.Constants.PREF_DATA_STORE_NAME
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_DATA_STORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object SessionManagerLocalDataSourcesModule {
    @Singleton
    @Provides
    fun provideLocalSessionManagerDataSource(@ApplicationContext context: Context):
            LocalSessionManagerDataSource = LocalSessionManagerDataSourceImpl(context.dataStore)
}