package com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegionDistrictService {
    @FormUrlEncoded
    @POST
    suspend fun getRegionDistricts(@Body data: String): RegionDistrictApiModel?
}