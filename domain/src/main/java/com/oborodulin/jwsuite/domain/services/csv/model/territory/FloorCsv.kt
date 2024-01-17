package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class FloorCsv(
    @CsvBindByName val floorId: UUID,
    @CsvBindByName val floorNum: Int,
    @CsvBindByName val isSecurityFloor: Boolean = false,
    @CsvBindByName val isIntercomFloor: Boolean? = null,
    @CsvBindByName val isResidentialFloor: Boolean = true,
    @CsvBindByName val roomsByFloor: Int? = null,
    @CsvBindByName val estFloorRooms: Int? = null,
    @CsvBindByName val floorDesc: String? = null,
    @CsvBindByName val fTerritoriesId: UUID? = null,
    @CsvBindByName val fEntrancesId: UUID? = null,
    @CsvBindByName val fHousesId: UUID
) : Exportable
