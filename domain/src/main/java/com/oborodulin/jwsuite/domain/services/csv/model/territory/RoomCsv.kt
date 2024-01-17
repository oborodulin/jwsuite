package com.oborodulin.jwsuite.domain.services.csv.model.territory

import com.oborodulin.jwsuite.domain.services.Exportable
import com.opencsv.bean.CsvBindByName
import java.util.UUID

data class RoomCsv(
    @CsvBindByName val roomId: UUID,
    @CsvBindByName val roomNum: Int,
    @CsvBindByName val isIntercomRoom: Boolean? = null,
    @CsvBindByName val isResidentialRoom: Boolean = true,
    @CsvBindByName val isForeignLangRoom: Boolean = false,
    @CsvBindByName val roomDesc: String? = null,
    @CsvBindByName val rTerritoriesId: UUID? = null,
    @CsvBindByName val rFloorsId: UUID? = null,
    @CsvBindByName val rEntrancesId: UUID? = null,
    @CsvBindByName val rHousesId: UUID
) : Exportable