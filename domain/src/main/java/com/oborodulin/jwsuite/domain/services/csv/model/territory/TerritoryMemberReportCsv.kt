package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class TerritoryMemberReportCsv(
    @CsvBindByName val territoryMemberReportId: UUID,
    @CsvBindByName val territoryReportMark: TerritoryReportMark,
    @CsvBindByName val languageCode: String? = null,
    @CsvBindByName val gender: Boolean? = null,
    @CsvBindByName val age: Int? = null,
    @CsvBindByName val isReportProcessed: Boolean = false,
    @CsvBindByName val territoryReportDesc: String? = null,
    @CsvBindByName val tmrRoomsId: UUID? = null,
    @CsvBindByName val tmrHousesId: UUID? = null,
    @CsvBindByName val tmrTerritoryStreetsId: UUID? = null,
    @CsvBindByName val tmrTerritoryMembersId: UUID
) : Exportable