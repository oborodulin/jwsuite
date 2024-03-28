package com.oborodulin.jwsuite.data_geo.remote.osm.model.microdistrict

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MicrodistrictService {
    @FormUrlEncoded
    @POST
    suspend fun getMicrodistricts(@Body data: String): MicrodistrictApiModel?
}