package com.oborodulin.jwsuite.domain.repositories

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface WorkerProviderRepository {
    fun createLogoutWork()
    fun onLogoutWorkerSuccess(): Flow<List<WorkInfo>>
}
