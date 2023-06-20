package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryDistrictView
import com.oborodulin.jwsuite.domain.model.TerritoryDistrict
import java.util.UUID

class TerritoryDistrictViewToTerritoryDistrictMapper :
    Mapper<TerritoryDistrictView, TerritoryDistrict> {
    override fun map(input: TerritoryDistrictView): TerritoryDistrict {
        val territoryDistrict = TerritoryDistrict(
            territoryDistrictType = input.territoryDistrictType,
            congregationId = input.congregationId,
            isPrivateSector = input.isPrivateSector,
            districtId = input.districtId,
            districtName = input.districtName
        )
        territoryDistrict.id = UUID.randomUUID()
        return territoryDistrict
    }
}