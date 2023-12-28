package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryHouseReport
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReport
import java.util.UUID

class TerritoryReportToTerritoryMemberReportEntityMapper(private val ctx: Context) :
    Mapper<TerritoryHouseReport, TerritoryMemberReportEntity>,
    NullableMapper<TerritoryHouseReport, TerritoryMemberReportEntity> {
    override fun map(input: TerritoryHouseReport) = TerritoryMemberReportEntity(
        territoryMemberReportId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
            territoryMemberMark = input.territoryReport?.territoryMemberMark,
            languageCode = input.territoryReport?.languageCode,
            gender = input.territoryReport?.gender,
            age = input.territoryReport?.age,
            isProcessed = input.territoryReport?.isProcessed ?: false,
            territoryReportDesc = input.territoryReport?.territoryReportDesc
        )
        territoryReport.id = input.territoryReport?.territoryMemberReportId
        return territoryReport
    }

    override fun nullableMap(input: TerritoryHouseReport?) =
        input?.let { map(it) }
}