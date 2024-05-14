package com.oborodulin.jwsuite.data_geo.remote.osm.service

import com.oborodulin.jwsuite.data_geo.remote.osm.model.MicrodistrictApiModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MicrodistrictService {
    @FormUrlEncoded
    @POST(".")
    suspend fun getMicrodistricts(@Field("data") data: String): MicrodistrictApiModel?
}