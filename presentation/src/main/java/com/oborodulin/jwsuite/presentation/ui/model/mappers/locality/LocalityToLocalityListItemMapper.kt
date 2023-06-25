package com.oborodulin.jwsuite.presentation.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.model.LocalityListItem
import java.util.UUID

class LocalityToLocalityListItemMapper : Mapper<GeoLocality, LocalityListItem> {
    override fun map(input: GeoLocality) = LocalityListItem(
        id = input.id ?: UUID.randomUUID(),
        localityCode = input.localityCode,
        localityType = input.localityType,
        localityShortName = input.localityShortName,
        localityName = input.localityName
    )
}