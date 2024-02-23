package com.oborodulin.jwsuite.data_geo.remote.osm.model.region

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegionService {
    @FormUrlEncoded
    @POST
    suspend fun getRegions(@Body data: String): List<RegionApiModel>
}