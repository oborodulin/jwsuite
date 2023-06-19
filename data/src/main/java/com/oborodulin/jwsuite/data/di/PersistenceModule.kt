package com.oborodulin.jwsuite.data.di

import android.content.Context
import com.google.gson.Gson
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    // Database:
    @Singleton
    @Provides
    fun provideHomeDatabase(
        @ApplicationContext appContext: Context, jsonLogger: Gson
    ): JwSuiteDatabase = JwSuiteDatabase.getInstance(appContext, jsonLogger)
}