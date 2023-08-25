package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalitiesListItem
import java.util.UUID

class LocalityToLocalitiesListItemMapper : Mapper<GeoLocality, LocalitiesListItem> {
    override fun map(input: GeoLocality) = LocalitiesListItem(
        id = input.id ?: UUID.randomUUID(),
        localityCode = input.localityCode,
        localityShortName = input.localityShortName,
        localityFullName = input.localityFullName
    )
}