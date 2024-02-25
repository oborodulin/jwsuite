package com.oborodulin.jwsuite.data_geo.local.db.mappers.geomicrodistrict

data class GeoMicrodistrictMappers(
    val microdistrictViewListToGeoMicrodistrictsListMapper: MicrodistrictViewListToGeoMicrodistrictsListMapper,
    val geoMicrodistrictViewToGeoMicrodistrictMapper: GeoMicrodistrictViewToGeoMicrodistrictMapper,
    val geoMicrodistrictsListToGeoMicrodistrictEntityListMapper: GeoMicrodistrictsListToGeoMicrodistrictEntityListMapper,
    val geoMicrodistrictToGeoMicrodistrictEntityMapper: GeoMicrodistrictToGeoMicrodistrictEntityMapper,
    val geoMicrodistrictToGeoMicrodistrictTlEntityMapper: GeoMicrodistrictToGeoMicrodistrictTlEntityMapper
)
