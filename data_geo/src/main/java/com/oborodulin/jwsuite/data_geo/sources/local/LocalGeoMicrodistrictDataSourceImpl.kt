package com.oborodulin.jwsuite.data_geo.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoMicrodistrictDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoMicrodistrictDataSourceImpl @Inject constructor(
    private val microdistrictDao: GeoMicrodistrictDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoMicrodistrictDataSource {
    override fun getAllMicrodistricts() = microdistrictDao.findAll()
    override fun getLocalityMicrodistricts(localityId: UUID) =
        microdistrictDao.findByLocalityId(localityId)

    override fun getLocalityDistrictMicrodistricts(localityDistrictId: UUID) =
        microdistrictDao.findByLocalityDistrictId(localityDistrictId)

    override fun getStreetMicrodistricts(streetId: UUID) = microdistrictDao.findByStreetId(streetId)
    override fun getMicrodistrictsForStreet(streetId: UUID) =
        microdistrictDao.findForStreetByStreetId(streetId)

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

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getMicrodistrictEntities() = microdistrictDao.selectEntities()
    override fun getMicrodistrictTlEntities() = microdistrictDao.selectTlEntities()
    override suspend fun loadMicrodistrictEntities(microdistricts: List<GeoMicrodistrictEntity>) =
        withContext(dispatcher) {
            microdistrictDao.insert(microdistricts)
        }

    override suspend fun loadMicrodistrictTlEntities(microdistrictTls: List<GeoMicrodistrictTlEntity>) =
        withContext(dispatcher) {
            microdistrictDao.insertTls(microdistrictTls)
        }
}