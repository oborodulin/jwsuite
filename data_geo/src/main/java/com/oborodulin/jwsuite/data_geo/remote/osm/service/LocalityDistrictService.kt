package com.oborodulin.jwsuite.data_geo.remote.osm.service

import com.oborodulin.jwsuite.data_geo.remote.osm.model.LocalityDistrictApiModel
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LocalityDistrictService {
    @FormUrlEncoded
    @POST
    suspend fun getLocalityDistricts(@Body data: String): LocalityDistrictApiModel?
}