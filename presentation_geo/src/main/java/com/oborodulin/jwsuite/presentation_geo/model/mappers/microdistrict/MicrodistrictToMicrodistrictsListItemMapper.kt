package com.oborodulin.jwsuite.presentation_geo.model.mappers.microdistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.presentation_geo.model.MicrodistrictsListItem
import java.util.UUID

class MicrodistrictToMicrodistrictsListItemMapper :
    Mapper<GeoMicrodistrict, MicrodistrictsListItem> {
    override fun map(input: GeoMicrodistrict) = MicrodistrictsListItem(
        id = input.id ?: UUID.randomUUID(),
        microdistrictShortName = input.microdistrictShortName,
        microdistrictFullName = input.microdistrictFullName
    )
}