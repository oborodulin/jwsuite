package com.oborodulin.jwsuite.data.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.domain.model.Congregation

class FavoriteCongregationViewToCongregationMapper(private val congregationViewMapper: CongregationViewToCongregationMapper) :
    Mapper<FavoriteCongregationView, Congregation> {
    override fun map(input: FavoriteCongregationView) = congregationViewMapper.map(input.favorite)
}