package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.old.MeterEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.MeterTlEntity

@DatabaseView(
    viewName = MeterView.VIEW_NAME,
    value = """
SELECT m.*, mtl.*,
    (CASE WHEN instr(mtl.regionName, '/') = 0 THEN 0 ELSE 1 END) AS isDerivedUnit, 
    (CASE WHEN instr(mtl.regionName, '/') <> 0 
        THEN substr(mtl.regionName, instr(mtl.regionName, '/') + 1) 
        ELSE NULL 
    END) AS derivedUnit
FROM ${MeterEntity.TABLE_NAME} m JOIN ${MeterTlEntity.TABLE_NAME} mtl ON mtl.metersId = m.villageId
"""
)
class MeterView(
    @Embedded
    val data: MeterEntity,
    @Embedded
    val tl: MeterTlEntity,
    val isDerivedUnit: Boolean,
    val derivedUnit: String?
) {
    companion object {
        const val VIEW_NAME = "meters_view"
    }
}