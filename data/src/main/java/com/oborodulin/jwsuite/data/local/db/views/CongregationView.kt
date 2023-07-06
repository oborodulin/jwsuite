package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data.util.Constants.PX_CONGREGATION_REGION
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY_REGION

@DatabaseView(
    viewName = CongregationView.VIEW_NAME,
    value = """
SELECT c.*, 
        l.${PX_LOCALITY_REGION}_regionId AS ${PX_CONGREGATION_REGION}_regionId, l.${PX_LOCALITY_REGION}_regionCode AS ${PX_CONGREGATION_REGION}_regionCode, 
            l.${PX_LOCALITY_REGION}_regionTlId AS ${PX_CONGREGATION_REGION}_regionTlId, l.${PX_LOCALITY_REGION}_regionLocCode AS ${PX_CONGREGATION_REGION}_regionLocCode, 
            l.${PX_LOCALITY_REGION}_regionName AS ${PX_CONGREGATION_REGION}_regionName, l.${PX_LOCALITY_REGION}_regionsId AS ${PX_CONGREGATION_REGION}_regionsId, 
        rdv.*, l.*, ltl.*
FROM ${CongregationEntity.TABLE_NAME} c JOIN ${GeoLocalityEntity.TABLE_NAME} l ON l.localityId = c. 
    JOIN ${GeoLocalityTlEntity.TABLE_NAME} ltl ON ltl.localitiesId = l.localityId 
${GeoLocalityView.VIEW_NAME} l ON l.localityId = c.cLocalitiesId
ORDER BY c.congregationName
"""
)
class CongregationView(
    @Embedded val congregation: CongregationEntity,
    @Embedded val locality: GeoLocalityView
) {
    companion object {
        const val VIEW_NAME = "congregations_view"
    }
}