package com.oborodulin.jwsuite.data_geo.remote.osm.model.country

import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CountryService {
    @FormUrlEncoded
    @POST
    suspend fun getCountries(@Body data: String): CountryApiModel
}