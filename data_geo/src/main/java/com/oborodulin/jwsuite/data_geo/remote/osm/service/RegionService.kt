package com.oborodulin.jwsuite.data_geo.remote.osm.service

import com.oborodulin.jwsuite.data_geo.remote.osm.model.RegionApiModel
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegionService {
    // https://stackoverflow.com/questions/26637340/prevent-retrofit-from-encoding-my-http-request-body
    @FormUrlEncoded
    // https://github.com/square/retrofit/issues/1701
    @POST(".")
    suspend fun getRegions(@Field("data") data: String): RegionApiModel?
}