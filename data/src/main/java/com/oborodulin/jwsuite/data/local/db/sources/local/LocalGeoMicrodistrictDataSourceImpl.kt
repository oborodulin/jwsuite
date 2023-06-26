package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoMicrodistrictDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoMicrodistrictDataSourceImpl @Inject constructor(
    private val microdistrictDao: GeoMicrodistrictDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoMicrodistrictDataSource {
    override fun getMicrodistricts(localityDistrictId: UUID) =
        microdistrictDao.findByLocalityDistrictId(localityDistrictId)

    override fun getMicrodistrict(microdistrictId: UUID) =
        microdistrictDao.findDistinctById(microdistrictId)

    override suspend fun insertMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    ) = withContext(dispatcher) {
        microdistrictDao.insert(microdistrict, textContent)
    }

    override suspend fun updateMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    ) = withContext(dispatcher) {
        microdistrictDao.update(microdistrict, textContent)
    }

    override suspend fun deleteMicrodistrict(microdistrict: GeoMicrodistrictEntity) =
        withContext(dispatcher) {
            microdistrictDao.delete(microdistrict)
        }

    override suspend fun deleteMicrodistrictById(microdistrictId: UUID) = withContext(dispatcher) {
        microdistrictDao.deleteById(microdistrictId)
    }

    override suspend fun deleteMicrodistricts(microdistricts: List<GeoMicrodistrictEntity>) =
        withContext(dispatcher) {
            microdistrictDao.delete(microdistricts)
        }

    override suspend fun deleteAllMicrodistricts() = withContext(dispatcher) {
        microdistrictDao.deleteAll()
    }

    // MD Streets:
    override suspend fun insertMicrodistrictStreet(
        microdistrict: GeoMicrodistrictEntity, street: GeoStreetEntity
    ) = withContext(dispatcher) {
        microdistrictDao.insert(microdistrict, street)
    }

    override suspend fun updateMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity) =
        withContext(dispatcher) {
            microdistrictDao.update(districtStreet)
        }

    override suspend fun deleteMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity) =
        withContext(dispatcher) {
            microdistrictDao.deleteStreet(districtStreet)
        }

    override suspend fun deleteMicrodistrictStreet(districtStreetId: UUID) =
        withContext(dispatcher) {
            microdistrictDao.deleteStreetById(districtStreetId)
        }

    override suspend fun deleteMicrodistrictStreets(microdistrictId: UUID) =
        withContext(dispatcher) {
            microdistrictDao.deleteStreetsByMicrodistrictId(microdistrictId)
        }
}
