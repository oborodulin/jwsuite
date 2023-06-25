package com.oborodulin.jwsuite.presentation.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi

class LocalityUiToLocalityMapper : Mapper<LocalityUi, GeoLocality> {
    override fun map(input: LocalityUi): GeoLocality {
        val locality = GeoLocality(
            regionId = input.regionId,
            regionDistrictId = input.regionDistrictId,
            localityCode = input.localityCode,
            localityType = input.localityType,
            localityShortName = input.localityShortName,
            localityName = input.localityName
        )
        locality.id = input.id
        return locality
    }
}