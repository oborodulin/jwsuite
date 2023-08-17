package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

data class CongregationMappers(
    val congregationViewListToCongregationsListMapper: CongregationViewListToCongregationsListMapper,
    val congregationViewToCongregationMapper: CongregationViewToCongregationMapper,
    val congregationsListToCongregationEntityListMapper: CongregationsListToCongregationEntityListMapper,
    val congregationToCongregationEntityMapper: CongregationToCongregationEntityMapper,
    val favoriteCongregationViewToCongregationMapper: FavoriteCongregationViewToCongregationMapper
)
