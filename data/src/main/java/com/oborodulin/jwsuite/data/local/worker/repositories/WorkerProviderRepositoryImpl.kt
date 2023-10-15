package com.oborodulin.jwsuite.data.local.worker.repositories

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.oborodulin.jwsuite.data.local.worker.LogoutWorker
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "Data.Worker.WorkerProviderRepositoryImpl"

// https://medium.com/@sumon.v0.0/android-jetpack-workmanager-onetime-and-periodic-work-request-94ace224ff7d
class WorkerProviderRepositoryImpl @Inject constructor(private val workManager: WorkManager) :
    WorkerProviderRepository {
    override fun createLogoutWork() {
        createLogoutWorker()
    }

    // https://developer.android.com/guide/background/persistent/getting-started/define-work#expedited
    // https://stackoverflow.com/questions/53297982/how-to-run-workmanager-immediately
    private fun createLogoutWorker() {
        val workRequest = OneTimeWorkRequestBuilder<LogoutWorker>().addTag(TAG_LOGOUT)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueueUniqueWork(
            WORK_NAME_LOGOUT, ExistingWorkPolicy.REPLACE, workRequest
        )
        Timber.tag(TAG)
            .i("Work Manager: '%s' created", WorkerProviderRepositoryImpl.WORK_NAME_LOGOUT)
    }

    // https://chao2zhang.medium.com/converting-livedata-to-flow-lessons-learned-9362a00611c8
    override fun onLogoutWorkerSuccess() =
        flow { emit(workManager.getWorkInfosByTagLiveData(TAG_LOGOUT).value?.toList().orEmpty()) }

    companion object {
        const val WORK_NAME_LOGOUT = "logoutWorker"
        const val TAG_LOGOUT = "DebugJwSuiteLogout"
        const val WORK_NAME_BACKUP = "backupWorker"
        const val TAG_BACKUP = "DebugJwSuiteBackup"
    }
}