package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoryUiMapper

class TerritoryToTerritoryUiMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
    private val localityMapper: LocalityToLocalityUiMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
    private val microdistrictMapper: MicrodistrictToMicrodistrictUiMapper
) : Mapper<Territory, TerritoryUi>, NullableMapper<Territory, TerritoryUi> {
    override fun map(input: Territory): TerritoryUi {
        val territoryUi = TerritoryUi(
            congregation = congregationMapper.map(input.congregation),
            territoryCategory = territoryCategoryMapper.map(input.territoryCategory),
            locality = localityMapper.map(input.locality),
            localityDistrict = localityDistrictMapper.nullableMap(input.localityDistrict),
            microdistrict = microdistrictMapper.nullableMap(input.microdistrict),
            territoryNum = input.territoryNum,
            isBusiness = input.isBusiness,
            isGroupMinistry = input.isGroupMinistry,
            isInPerimeter = input.isInPerimeter,
            isProcessed = input.isProcessed,
            isActive = input.isActive,
            territoryDesc = input.territoryDesc,
            cardNum = input.cardNum,
            cardLocation = input.cardLocation,
            fullCardNum = input.fullCardNum
        )
        territoryUi.id = input.id
        return territoryUi
    }

    override fun nullableMap(input: Territory?) = input?.let { map(it) }
}