package com.oborodulin.jwsuite.presentation.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi

class LocalityToLocalityUiMapper : Mapper<GeoLocality, LocalityUi> {
    override fun map(input: GeoLocality): LocalityUi {
        val localityUi = LocalityUi(
            regionId = input.regionId,
            regionDistrictId = input.regionDistrictId,
            localityCode = input.localityCode,
            localityType = input.localityType,
            localityShortName = input.localityShortName,
            localityName = input.localityName
        )
        localityUi.id = input.id
        return localityUi
    }
}