package com.oborodulin.jwsuite.data_geo.remote.osm.model.locality

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LocalityService {
    @FormUrlEncoded
    @POST
    suspend fun getLocalities(@Body data: String): LocalityApiModel?
}