package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.oborodulin.jwsuite.domain.services.Importable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class EntranceCsv(
    @CsvBindByName val entranceId: UUID,
    @CsvBindByName val entranceNum: Int,
    @CsvBindByName val isSecurityEntrance: Boolean = false,
    @CsvBindByName val isIntercomEntrance: Boolean? = null,
    @CsvBindByName val isResidentialEntrance: Boolean = true,
    @CsvBindByName val entranceFloorsQty: Int? = null,
    @CsvBindByName val roomsByEntranceFloor: Int? = null,
    @CsvBindByName val estEntranceRooms: Int? = null,
    @CsvBindByName val entranceDesc: String? = null,
    @CsvBindByName val eTerritoriesId: UUID? = null,
    @CsvBindByName val eHousesId: UUID
) : Exportable, Importable