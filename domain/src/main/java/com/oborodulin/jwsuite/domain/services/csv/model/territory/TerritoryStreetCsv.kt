package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class TerritoryStreetCsv(
    @CsvBindByName val territoryStreetId: UUID,
    @CsvBindByName val isEvenSide: Boolean? = null,
    @CsvBindByName val isTerStreetPrivateSector: Boolean? = null,
    @CsvBindByName val estTerStreetHouses: Int? = null,
    @CsvBindByName val tsStreetsId: UUID,
    @CsvBindByName val tsTerritoriesId: UUID
) : Exportable