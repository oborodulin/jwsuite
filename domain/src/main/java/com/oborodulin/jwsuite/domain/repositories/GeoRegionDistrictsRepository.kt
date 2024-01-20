package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoRegionDistrict
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionDistrictTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoRegionDistrictsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<GeoRegionDistrict>>
    fun getAllByRegion(regionId: UUID): Flow<List<GeoRegionDistrict>>
    fun get(regionDistrictId: UUID): Flow<GeoRegionDistrict>
    fun save(regionDistrict: GeoRegionDistrict): Flow<GeoRegionDistrict>
    fun delete(regionDistrict: GeoRegionDistrict): Flow<GeoRegionDistrict>
    fun deleteById(regionDistrictId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractRegionDistricts(): Flow<List<GeoRegionDistrictCsv>>
    fun extractRegionDistrictTls(): Flow<List<GeoRegionDistrictTlCsv>>
    fun loadRegionDistricts(regionDistricts: List<GeoRegionDistrictCsv>): Flow<Int>
    fun loadRegionDistrictTls(regionDistrictTls: List<GeoRegionDistrictTlCsv>): Flow<Int>
}