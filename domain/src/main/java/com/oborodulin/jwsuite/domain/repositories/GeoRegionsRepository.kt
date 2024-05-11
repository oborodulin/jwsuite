package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.home.common.domain.Result
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoRegionTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoRegionsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<GeoRegion>>
    fun getAllByCountry(
        countryId: UUID,
        countryGeocodeArea: String,
        countryCode: String,
        isRemoteFetch: Boolean = false
    ): Flow<Result<List<GeoRegion>>>

    fun get(regionId: UUID): Flow<GeoRegion>
    fun save(region: GeoRegion): Flow<GeoRegion>
    fun delete(region: GeoRegion): Flow<GeoRegion>
    fun delete(regionId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractRegions(): Flow<List<GeoRegionCsv>>
    fun extractRegionTls(): Flow<List<GeoRegionTlCsv>>
    fun loadRegions(regions: List<GeoRegionCsv>): Flow<Int>
    fun loadRegionTls(regionTls: List<GeoRegionTlCsv>): Flow<Int>
}