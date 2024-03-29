package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem
import java.util.UUID

class MicrodistrictToMicrodistrictsListItemMapper :
    Mapper<GeoMicrodistrict, MicrodistrictsListItem> {
    override fun map(input: GeoMicrodistrict) = MicrodistrictsListItem(
        id = input.id ?: UUID.randomUUID(),
        localityDistrictId = input.localityDistrict?.id,
        microdistrictShortName = input.microdistrictShortName,
        microdistrictFullName = input.microdistrictFullName
    )
}