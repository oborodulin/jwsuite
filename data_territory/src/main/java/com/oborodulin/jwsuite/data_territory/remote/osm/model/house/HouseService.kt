package com.oborodulin.jwsuite.data_territory.remote.osm.model.house

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HouseService {
    @FormUrlEncoded
    @POST
    suspend fun getHouses(@Body data: String): HouseApiModel?
}