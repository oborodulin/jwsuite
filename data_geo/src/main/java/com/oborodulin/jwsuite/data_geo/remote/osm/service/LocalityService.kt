package com.oborodulin.jwsuite.data_geo.remote.osm.service

import com.oborodulin.jwsuite.data_geo.remote.osm.model.LocalityApiModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LocalityService {
    @FormUrlEncoded
    @POST(".")
    suspend fun getLocalities(@Field("data") data: String): LocalityApiModel?
}