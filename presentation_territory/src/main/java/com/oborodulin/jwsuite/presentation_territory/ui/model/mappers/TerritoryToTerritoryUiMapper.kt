package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Territory
import com.oborodulin.jwsuite.presentation_territory.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoryUiMapper

class TerritoryToTerritoryUiMapper(
    private val congregationMapper: CongregationToCongregationUiMapper,
    private val territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
    private val localityMapper: LocalityToLocalityUiMapper,
    private val localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
    private val microdistrictMapper: MicrodistrictToMicrodistrictUiMapper
) : Mapper<Territory, TerritoryUi> {
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
        )
        territoryUi.id = input.id
        return territoryUi
    }
}