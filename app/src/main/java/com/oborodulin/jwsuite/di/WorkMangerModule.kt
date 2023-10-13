package com.oborodulin.jwsuite.di

import android.app.Application
import androidx.work.WorkManager
import com.oborodulin.jwsuite.data.local.worker.repository.WorkerProviderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkMangerModule {
    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun providesCoinsNotificationChannel(context: Application): CoinsNotificationChannel {
        return CoinsNotificationChannel(context)
    }

    @Singleton
    @Provides
    fun providesSyncNotificationChannel(context: Application): SyncNotificationChannel {
        return SyncNotificationChannel(context)
    }

    @Provides
    @Singleton
    fun providesCoinsNotification(
        context: Application,
        channel: CoinsNotificationChannel
    ) = CoinsNotification(context, channel)

    @Provides
    @Singleton
    fun providesSyncNotification(
        context: Application,
        channel: SyncNotificationChannel
    ) = SyncNotification(context, channel)

    @Provides
    @Singleton
    fun providesWorkManger(application: Application) =
        WorkManager.getInstance(application)
}