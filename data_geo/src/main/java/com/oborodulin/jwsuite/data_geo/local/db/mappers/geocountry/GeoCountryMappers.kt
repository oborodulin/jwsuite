package com.oborodulin.jwsuite.data_geo.local.db.mappers.geocountry

data class GeoCountryMappers(
    val geoCountryViewListToGeoCountriesListMapper: GeoCountryViewListToGeoCountriesListMapper,
    val geoCountryViewToGeoCountryMapper: GeoCountryViewToGeoCountryMapper,
    val geoCountriesListToGeoCountryEntityListMapper: GeoCountriesListToGeoCountryEntityListMapper,
    val geoCountryToGeoCountryEntityMapper: GeoCountryToGeoCountryEntityMapper,
    val geoCountryToGeoCountryTlEntityMapper: GeoCountryToGeoCountryTlEntityMapper
)
