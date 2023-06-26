package com.oborodulin.jwsuite.data.local.db.mappers.congregation

data class CongregationMappers(
    val congregationViewListToCongregationsListMapper: CongregationViewListToCongregationsListMapper,
    val congregationViewToCongregationMapper: CongregationViewToCongregationMapper,
    val congregationsListToCongregationEntityListMapper: CongregationsListToCongregationEntityListMapper,
    val congregationToCongregationEntityMapper: CongregationToCongregationEntityMapper,
    val favoriteCongregationViewToCongregationMapper: FavoriteCongregationViewToCongregationMapper
)
