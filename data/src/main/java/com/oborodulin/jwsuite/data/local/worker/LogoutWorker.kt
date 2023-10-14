package com.oborodulin.jwsuite.data.local.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@HiltWorker
class LogoutWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val sessionUseCases: SessionUseCases,
) : CoroutineWorker(context, workerParameters) {
    // https://developer.android.com/guide/background/persistent/getting-started/define-work#coroutineworker
    // https://stackoverflow.com/questions/71311516/notification-is-not-showing-with-workmanager-foregroundinfo
    // https://stackoverflow.com/questions/63537972/android-workmanager-setforegroundinfo
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val isSuccess = sessionUseCases.logoutUseCase.execute(LogoutUseCase.Request).first()
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}