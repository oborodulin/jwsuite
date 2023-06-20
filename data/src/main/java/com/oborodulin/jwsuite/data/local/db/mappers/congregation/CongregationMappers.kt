package com.oborodulin.jwsuite.data.local.db.mappers.congregation

data class CongregationMappers(
    val congregationViewListToCongregationListMapper: CongregationViewListToCongregationListMapper,
    val congregationViewToCongregationMapper: CongregationViewToCongregationMapper,
    val congregationListToCongregationEntityListMapper: CongregationListToCongregationEntityListMapper,
    val congregationToCongregationEntityMapper: CongregationToCongregationEntityMapper,
    val favoriteCongregationViewToCongregationMapper: FavoriteCongregationViewToCongregationMapper
)
