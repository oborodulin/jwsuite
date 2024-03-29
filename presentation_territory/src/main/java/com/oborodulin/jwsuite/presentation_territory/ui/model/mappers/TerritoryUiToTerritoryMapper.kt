package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryUiToTerritoryCategoryMapper

class TerritoryUiToTerritoryMapper(
    private val ctx: Context,
    private val congregationUiMapper: CongregationUiToCongregationMapper,
    private val territoryCategoryUiMapper: TerritoryCategoryUiToTerritoryCategoryMapper,
    private val localityUiMapper: LocalityUiToLocalityMapper,
    private val localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
    private val microdistrictUiMapper: MicrodistrictUiToMicrodistrictMapper
) : Mapper<TerritoryUi, Territory>, NullableMapper<TerritoryUi, Territory> {
    override fun map(input: TerritoryUi) = Territory(
        ctx = ctx,
        congregation = congregationUiMapper.map(input.congregation),
        territoryCategory = territoryCategoryUiMapper.map(input.territoryCategory),
        locality = localityUiMapper.map(input.locality),
        localityDistrict = localityDistrictUiMapper.nullableMap(input.localityDistrict),
        microdistrict = microdistrictUiMapper.nullableMap(input.microdistrict),
        territoryNum = input.territoryNum,
        isBusiness = input.isBusiness,
        isGroupMinistry = input.isGroupMinistry,
        isInPerimeter = input.isInPerimeter,
        isProcessed = input.isProcessed,
        isActive = input.isActive,
        territoryDesc = input.territoryDesc
    ).also { it.id = input.id }

    override fun nullableMap(input: TerritoryUi?) = input?.let { map(it) }
}