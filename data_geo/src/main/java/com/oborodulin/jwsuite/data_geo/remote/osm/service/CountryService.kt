package com.oborodulin.jwsuite.data_geo.remote.osm.service

import com.oborodulin.jwsuite.data_geo.remote.osm.model.CountryApiModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CountryService {
    @FormUrlEncoded
    @POST(".")
    suspend fun getCountries(@Field("data") data: String): CountryApiModel?
}