package com.oborodulin.jwsuite.domain.repositories

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface WorkerProviderRepository {
    fun createWork()
    fun onWorkerSuccess(): Flow<List<WorkInfo>>
}
