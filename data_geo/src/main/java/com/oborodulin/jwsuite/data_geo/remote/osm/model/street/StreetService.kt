package com.oborodulin.jwsuite.data_geo.remote.osm.model.street

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StreetService {
    @FormUrlEncoded
    @POST
    suspend fun getStreets(@Body data: String): StreetApiModel?
}